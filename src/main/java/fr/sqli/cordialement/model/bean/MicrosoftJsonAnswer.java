package fr.sqli.cordialement.model.bean;

import java.util.List;

public class MicrosoftJsonAnswer {

    private List<MicrosoftScores> documents;
    private List<MicrosoftErrors> errors;

    public List<MicrosoftScores> getDocuments() {
        return documents;
    }

    public void setDocuments(List<MicrosoftScores> documents) {
        this.documents = documents;
    }

    public List<MicrosoftErrors> getErrors() {
        return errors;
    }

    public void setErrors(List<MicrosoftErrors> errors) {
        this.errors = errors;
    }
}
