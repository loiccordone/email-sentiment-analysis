package fr.sqli.cordialement.services.impl;

import emoji4j.EmojiUtils;
import fr.sqli.cordialement.model.entity.API;
import fr.sqli.cordialement.model.entity.Email;
import fr.sqli.cordialement.model.entity.EmailScore;
import fr.sqli.cordialement.model.entity.Fragment;
import fr.sqli.cordialement.services.EmailService;
import fr.sqli.cordialement.model.repository.*;
import fr.sqli.cordialement.services.ScoreService;
import fr.sqli.cordialement.utils.EmailUtils;
import net.htmlparser.jericho.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static fr.sqli.cordialement.utils.EmailUtils.*;

@Service
public class EmailServiceImpl implements EmailService {

    private EmailRepository emailRepository;
    private ScoreService scoreService;

    @Autowired
    public EmailServiceImpl(EmailRepository emailRepository, ScoreService scoreService) {
        this.emailRepository = emailRepository;
        this.scoreService = scoreService;
    }

    /* Add email to DB */
    @Override
    public void addEmail(String texteEmail) {
        Email email = new Email();
        email.setContent(texteEmail);
        this.emailRepository.save(email);
    }

    /* Fill up the DB */
    @Override
    public void analyseEmail(Email email) {
        if (!email.isAnalysed()) {
            List<String> sentences = textToSentences(email.getContent());
            boolean smileysPresent;
            boolean boldPresent = false;
            boolean capitalLettersPresent = false;
            boolean colorsPresent = false;
            boolean typosPresent = false;

            /* Adding fragments to the email */
            addFragments(sentences, email, Fragment.TYPE.TEXT);

            /* Smileys */
            smileysPresent = EmojiUtils.countEmojis(email.getContent()) > 0;

            /* Bold words */
            List<Element> boldElements = getAllBoldElements(email.getContent());
            if (!boldElements.isEmpty()) {
                boldPresent = true;
                addFragments(boldElements, email, Fragment.TYPE.BOLD);
            }

            /* Capital Letters Words */
            List<String> capitalLettersParts = getAllCapitalLettersParts(email.getContent());
            if (!capitalLettersParts.isEmpty()) {
                capitalLettersPresent = true;
                addFragments(capitalLettersParts, email, Fragment.TYPE.CAPS);
            }

            /* Colors */
            List<Element> coloredFragments = getAllColoredElements(email.getContent());
            if (!coloredFragments.isEmpty()) {
                colorsPresent = true;
                addFragments(coloredFragments, email, Fragment.TYPE.COLORED);
            }

            /* Typos */
            /* TO DO */

            /* Setting the email characteristics */
            email.setNbSentences(sentences.size());
            email.setSmileysPresent(smileysPresent);
            email.setBoldPresent(boldPresent);
            email.setCapitalLettersPresent(capitalLettersPresent);
            email.setColorsPresent(colorsPresent);
            email.setTyposPresent(typosPresent);
            email.setIsAnalysed(true);

            /* Update the mail + all the fragments to the database */
            this.emailRepository.save(email);
        }
    }

    @Override
    public void analyseEmailById(Long id) {
        analyseEmail(this.emailRepository.findById(id));
    }

    @Override
    public void analyseAllEmail() {
        for (Email email : this.emailRepository.findAll()) {
            analyseEmail(email);
        }
    }

    /* Scoring */
    @Override
    public void scoreEmail(Email email) {
        this.scoreService.generateEmailScores(email);
    }

    @Override
    public void scoreEmailWithApi(Email email, API api) {
        this.scoreService.generateEmailScoresWithApi(email, api);
    }

    @Override
    public void scoreEmailById(Long id) {
        Email email = this.emailRepository.findById(id);
        scoreEmail(email);
    }

    @Override
    public void scoreEmailByIdWithApi(Long id, API api) {
        Email email = this.emailRepository.findById(id);
        scoreEmailWithApi(email, api);
    }

    @Override
    public void scoreAllEmails() {
        for (Email email : this.emailRepository.findAll()) {
            scoreEmail(email);
        }
    }

    @Override
    public void scoreAllEmailsWithApi(API api) {
        int aylienCalls = 0;

        for (Email email : this.emailRepository.findAll()) {
            if (api == API.AYLIEN) {
                /* wait 1min every 60 (or so) calls (60 calls max per minute) */
                if (aylienCalls + email.getNbSentences() > 60) {
                    long startTime = System.currentTimeMillis();
                    long elapsedTime = 0L;

                    while (elapsedTime < 60*1000) {
                        elapsedTime = (new Date()).getTime() - startTime;
                    }

                    aylienCalls = 0;
                }

                scoreEmailWithApi(email, api);
                aylienCalls += email.getNbSentences();
            } else {
                scoreEmailWithApi(email, api);
            }
        }
    }

    @Override
    public void scoreEmailsByIds(Long idDebut, Long idFin) {
        for (Long id = idDebut; id < idFin; id++) {
            scoreEmailById(id);
        }
    }

    @Override
    public void scoreEmailsByIdsWithApi(Long idDebut, Long idFin, API api) {
        for (Long id = idDebut; id < idFin; id++) {
            scoreEmailByIdWithApi(id, api);
        }
    }

    /* Services */
    @Override
    public Email findEmailById(Long id) {
        return this.emailRepository.findById(id);
    }

    @Override
    public void save(Email email) {
        this.emailRepository.save(email);
    }

    @Override
    public Iterable<Email> findAllEmail() {
        return this.emailRepository.findAll();
    }
}
