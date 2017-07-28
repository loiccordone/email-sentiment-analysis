package fr.sqli.cordialement.services;

import fr.sqli.cordialement.model.entity.API;
import fr.sqli.cordialement.model.entity.Email;

public interface ScoreService {

    /* Scoring Emails */
    void generateEmailScores(Email email);

    void generateEmailScoresWithApi(Email email, API api);
}
