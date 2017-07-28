package fr.sqli.cordialement.utils;

import fr.sqli.cordialement.model.entity.API;
import fr.sqli.cordialement.model.entity.Email;
import fr.sqli.cordialement.model.entity.EmailScore;
import fr.sqli.cordialement.model.entity.Fragment;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreUtils {

    public static String[] fragmentListToStringArray(List<Fragment> fragments) {
        String[] stringArrayFragments = new String[fragments.size()];
        int i = 0;

        for (Fragment f : fragments) {
            stringArrayFragments[i] = f.getText();
            i++;
        }

        return stringArrayFragments;
    }

    public static Map<String, String> fragmentListToMap(List<Fragment> fragments) {
        Map<String, String> fragmentsMap = new HashMap<>();
        int i = 1;

        for (Fragment f : fragments) {
            fragmentsMap.put("text" + i, f.getText());
            i++;
        }

        return fragmentsMap;
    }

    public static Map<String, Double> getEmojisScores(String filePath) {
        Map<String, Double> emojisScores = new HashMap<>();

        ClassPathResource classPathResource = new ClassPathResource(filePath);
        try {
            InputStream inputStream = classPathResource.getInputStream();
            File emailsFile = File.createTempFile("emojisTemp", ".txt");
            try {
                FileUtils.copyInputStreamToFile(inputStream, emailsFile);
            } finally {
                IOUtils.closeQuietly(inputStream);
            }

            try (BufferedReader br = new BufferedReader(new FileReader(emailsFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] emojiScore = line.split(",");
                    emojisScores.put(emojiScore[0], Double.valueOf(emojiScore[1]));
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return emojisScores;
    }

    public static String createMicrosoftJsonBody(Email email) throws Exception {
        boolean estUnSuivant = false;
        StringBuilder sb = new StringBuilder();
        sb.append("{\"documents\": [");

        for (Fragment f : email.getFragments()) {
            if (estUnSuivant) {
                sb.append(",");
            }
            sb.append("{\"language\": \"en\", \"id\": \"");
            sb.append(f.getId());
            sb.append("\", \"text\": \"");
            sb.append(f.getText().replaceAll("\"", "\\\\\""));
            sb.append("\"}");
            estUnSuivant = true;
        }

        sb.append("]}");

        return sb.toString();
    }

    public static String getMicrosoftJsonAnswer(String json, String apiKeyMicrosoft) throws Exception {
        HttpClient httpclient = HttpClients.createDefault();
        URIBuilder builder = new URIBuilder("https://westus.api.cognitive.microsoft.com/text/analytics/v2.0/sentiment");
        URI uri = builder.build();

        HttpPost request = new HttpPost(uri);
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Ocp-Apim-Subscription-Key", apiKeyMicrosoft);

        // Request body
        StringEntity reqEntity = new StringEntity(json);
        request.setEntity(reqEntity);

        HttpResponse response = httpclient.execute(request);
        HttpEntity entity = response.getEntity();

        return entity != null ? EntityUtils.toString(entity) : "null";
    }
}
