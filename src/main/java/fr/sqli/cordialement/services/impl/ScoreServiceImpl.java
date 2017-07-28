package fr.sqli.cordialement.services.impl;

import com.aylien.textapi.TextAPIClient;
import com.aylien.textapi.parameters.SentimentParams;
import com.aylien.textapi.responses.Sentiment;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.vdurmont.emoji.EmojiParser;
import emoji4j.Emoji;
import emoji4j.EmojiUtils;
import fr.sqli.cordialement.model.bean.*;
import fr.sqli.cordialement.model.entity.*;
import fr.sqli.cordialement.model.repository.EmailRepository;
import fr.sqli.cordialement.model.repository.ScoreRepository;
import fr.sqli.cordialement.services.ScoreService;
import fr.sqli.cordialement.utils.EmailUtils;
import io.indico.Indico;
import io.indico.api.results.BatchIndicoResult;
import io.indico.api.utils.IndicoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static emoji4j.EmojiUtils.emojify;
import static fr.sqli.cordialement.utils.RepustateClient.getSentimentBulk;
import static fr.sqli.cordialement.utils.ScoreUtils.*;

@Service
public class ScoreServiceImpl implements ScoreService {

    private ScoreRepository scoreRepository;
    private EmailRepository emailRepository;

    private final static String apiKeyIndico = "your-key";
    private final static String apiKeyMicrosoft = "your-key";
    private final static String idAppAylien = "your-key";
    private final static String apiKeyAylien = "your-key";
    private final static String apiKeyMeaningCloud = "your-key";

    private static final Pattern htmlEntityPattern = Pattern.compile("&#x\\w+;");

    @Autowired
    public ScoreServiceImpl(ScoreRepository scoreRepository, EmailRepository emailRepository) {
        this.scoreRepository = scoreRepository;
        this.emailRepository = emailRepository;
    }

    @Override
    public void generateEmailScores(Email email) {
        for (API api : API.values()) {
            if (api != API.SURVEY) {
                generateEmailScoresWithApi(email, api);
            }
        }
    }

    @Override
    public void generateEmailScoresWithApi(Email email, API api) {
        switch (api) {
            case INDICO: generateEmailScoreWithIndico(email); break;
            case MICROSOFT: generateEmailScoresWithMicrosoft(email); break;
            case AYLIEN: generateEmailScoresWithAylien(email); break;
            case MEANINGCLOUD: generateEmailScoresWithMeaningCloud(email); break;
            case REPUSTATE: generateEmailScoresWithRepustate(email); break;
            case SMILEYS: generateEmailScoresWithSmileys(email); break;
            case GLOBAL: computeEmailScore(email); break;
        }
    }

    /* APIs */
    private void generateEmailScoreWithIndico(Email email) {
        List<Fragment> fragments = email.getFragments();
        List<Double> scores = new ArrayList<>();
        API indicoApi = API.INDICO;
        int i = 0;
        Double somme = 0.0;

        String[] stringFragments = fragmentListToStringArray(email.getFragments());

        try {
            Indico indico = new Indico(apiKeyIndico);
            BatchIndicoResult multiple = indico.sentimentHQ.predict(stringFragments);
            scores = multiple.getSentimentHQ();
        } catch (IndicoException | IOException e) {
            e.printStackTrace();
        }

        for (Double scoreReturned : scores) {
            Score score = new Score(fragments.get(i), indicoApi, scoreReturned, new Date());
            this.scoreRepository.save(score);

            i++;
            somme += scoreReturned;
        }

        email.addEmailScore(new EmailScore(email, indicoApi, somme/i, new Date()));
        this.emailRepository.save(email);
    }

    private void generateEmailScoresWithMicrosoft(Email email) {
        List<Fragment> fragments = email.getFragments();
        API microsoftApi = API.MICROSOFT;
        int i = 0;
        Double somme = 0.0;

        try {
            String json = createMicrosoftJsonBody(email);
            String msAnswer = getMicrosoftJsonAnswer(json, apiKeyMicrosoft);

            ObjectMapper objectMapper = new ObjectMapper();

            MicrosoftJsonAnswer msJsonAnswer = objectMapper.readValue(msAnswer, MicrosoftJsonAnswer.class);

            for (MicrosoftScores msScore : msJsonAnswer.getDocuments()) {
                Score score = new Score(fragments.get(i), microsoftApi, msScore.getScore(), new Date());
                this.scoreRepository.save(score);

                i++;
                somme += msScore.getScore();
            }

            email.addEmailScore(new EmailScore(email, microsoftApi, somme/i, new Date()));
            this.emailRepository.save(email);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void generateEmailScoresWithAylien(Email email) {
        List<Fragment> fragments = email.getFragments();
        API aylienApi = API.AYLIEN;
        int i = 0;
        Double doubleScore;
        Double somme = 0.0;
        int nbCalls = 0;

        TextAPIClient client = new TextAPIClient(idAppAylien, apiKeyAylien);
        SentimentParams.Builder builder = SentimentParams.newBuilder();

        try {
            for (Fragment f : fragments) {
                builder.setText(f.getText().replaceAll("\"", "\\\\\""));
                Sentiment sentiment = client.sentiment(builder.build());

                Score score = new Score(fragments.get(i), aylienApi, 0.0, new Date());

                switch (sentiment.getPolarity()) {
                    case "negative": doubleScore = 0.5 - sentiment.getPolarityConfidence() * 0.5; break;
                    case "positive": doubleScore = 0.5 + sentiment.getPolarityConfidence() * 0.5; break;
                    case "neutral": doubleScore = 0.5; break;
                    default: doubleScore = 0.5; break;
                }

                score.setValeur(doubleScore);
                this.scoreRepository.save(score);

                nbCalls++;
                i++;
                somme += doubleScore;
            }

            email.addEmailScore(new EmailScore(email, aylienApi, somme/i, new Date()));
            this.emailRepository.save(email);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void generateEmailScoresWithMeaningCloud(Email email) {
        List<Fragment> fragments = email.getFragments();
        API meaningCloudApi = API.MEANINGCLOUD;
        int i = 0;
        Double doubleScore;
        Double somme = 0.0;
        int nbCalls = 0;

        try {
            for (Fragment f : fragments) {

                /* wait 1 sec every two calls (api limit) */
                if (nbCalls % 2 == 0) {
                    TimeUnit.SECONDS.sleep(1);
                }

                HttpResponse<String> meaningCloudAnswer = Unirest.post("http://api.meaningcloud.com/sentiment-2.1")
                        .header("content-type", "application/x-www-form-urlencoded")
                        .body("key=" + apiKeyMeaningCloud + "&lang=en&txt=" +
                                f.getPlainText() + "&txtf=plain")
                        .asString();

                ObjectMapper objectMapper = new ObjectMapper();

                MeaningCloudJsonAnswer meaningCloudJsonAnswer = objectMapper.readValue(meaningCloudAnswer.getBody(), MeaningCloudJsonAnswer.class);
                Score score = new Score(fragments.get(i), meaningCloudApi, 0.0, new Date());

                switch (meaningCloudJsonAnswer.getScore_tag()) {
                    case "P+": doubleScore = 0.8 + meaningCloudJsonAnswer.getConfidence() * 0.002; break;
                    case "P": doubleScore = 0.5 + meaningCloudJsonAnswer.getConfidence() * 0.003; break;
                    case "N": doubleScore = 0.5 - meaningCloudJsonAnswer.getConfidence() * 0.003; break;
                    case "N+": doubleScore = 0.2 - meaningCloudJsonAnswer.getConfidence() * 0.002; break;
                    case "NEU": doubleScore = 0.5; break;
                    case "NONE": doubleScore = 0.5; break;
                    default: doubleScore = 0.5; break;
                }

                score.setValeur(doubleScore);
                this.scoreRepository.save(score);

                nbCalls++;
                i++;
                somme += doubleScore;
            }

            email.addEmailScore(new EmailScore(email, meaningCloudApi, somme/i, new Date()));
            this.emailRepository.save(email);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateEmailScoresWithRepustate(Email email) {
        List<Fragment> fragments = email.getFragments();
        API repustateApi = API.REPUSTATE;
        int i = 0;
        Double somme = 0.0;
        Double doubleScore = 0.0;

        try {
            String repustateAnswer = getSentimentBulk(fragmentListToMap(email.getFragments()));

            ObjectMapper objectMapper = new ObjectMapper();

            RepustateJsonAnswer repustateJsonAnswer = objectMapper.readValue(repustateAnswer, RepustateJsonAnswer.class);

            for (RepustateScore repustateScore : repustateJsonAnswer.getResults()) {
                doubleScore = (repustateScore.getScore() + 1) / 2;
                Score score = new Score(fragments.get(i), repustateApi, doubleScore, new Date());
                this.scoreRepository.save(score);

                i++;
                somme += doubleScore;
            }

            email.addEmailScore(new EmailScore(email, repustateApi, somme/i, new Date()));
            this.emailRepository.save(email);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void generateEmailScoresWithSmileys(Email email) {
        if (email.getSmileysPresent()) {
            List<Fragment> fragments = email.getFragments();
            Map<String, Double> emojisScores = getEmojisScores("static/emojis_score.csv");
            API smileysApi = API.SMILEYS;
            int i = 0, nbFragmentsEmojis = 0;
            int nbEmojis;
            Double somme = 0.0;
            Double doubleScore = 0.0;

            for (Fragment f : fragments) {
                nbEmojis = 0;
                String text = f.getText().replaceAll("([^a-zA-Z\\d\\w]) ([^a-zA-Z\\d\\w])", "$1$2");
                String hexHtmlifiedText = EmojiUtils.hexHtmlify(text);
                Matcher matcher = htmlEntityPattern.matcher(hexHtmlifiedText);

                while (matcher.find()) {
                    String emojiCode = matcher.group();
                    if (EmojiUtils.isEmoji(emojiCode)) {
                        doubleScore += emojisScores.get(emojiCode);
                        nbEmojis++;
                    }
                }

                if (nbEmojis != 0) {
                    doubleScore /= nbEmojis;
                    Score score = new Score(fragments.get(i), smileysApi, doubleScore, new Date());
                    this.scoreRepository.save(score);

                    somme += doubleScore;
                    nbFragmentsEmojis++;
                }

                i++;
            }

            email.addEmailScore(new EmailScore(email, smileysApi, somme/nbFragmentsEmojis, new Date()));
            this.emailRepository.save(email);
        }
    }

    public void computeEmailScore(Email email) {
        Double[] param = new Double[9];
        param[0] = 0.0004343709; /* SIZE */
        param[1] = 0.5951741220; /* INDICO */
        param[2] = 0.1131594085; /* MICROSOFT */
        param[3] = 0.3438021197; /* AYLIEN */
        param[4] = -0.0244543326; /* MEANINGCLOUD */
        param[5] = -0.0849043711; /* REPUSTATE */
        param[6] = 0.1251683320; /* SMILEYS */
        param[7] = -0.8755026602; /* BOLD */
        param[8] = 0.4735644180; /* CAPS */

        email.addEmailScore(new EmailScore(email, API.GLOBAL, EmailUtils.modelEmailScore(email, param), new Date()));
        this.emailRepository.save(email);
    }
}
