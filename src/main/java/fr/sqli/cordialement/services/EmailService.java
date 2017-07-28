package fr.sqli.cordialement.services;

import fr.sqli.cordialement.model.entity.API;
import fr.sqli.cordialement.model.entity.Email;
import io.indico.api.Api;

public interface EmailService {

    /* Add email to DB */
    void addEmail(String text);

    /* Fill up the DB */
    void analyseEmail(Email email);

    void analyseEmailById(Long id);

    void analyseAllEmail();

    /* Scoring */
    void scoreEmail(Email email);

    void scoreEmailWithApi(Email email, API api);

    void scoreEmailById(Long id);

    void scoreEmailByIdWithApi(Long id, API api);

    void scoreEmailsByIds(Long idDebut, Long idFin);

    void scoreEmailsByIdsWithApi(Long idDebut, Long idFin, API api);

    void scoreAllEmails();

    void scoreAllEmailsWithApi(API api);

    /* Services */
    void save(Email email);

    Email findEmailById(Long id);

    Iterable<Email> findAllEmail();
}
