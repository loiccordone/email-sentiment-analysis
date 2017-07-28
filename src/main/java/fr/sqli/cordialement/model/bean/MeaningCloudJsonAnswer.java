package fr.sqli.cordialement.model.bean;

import java.util.List;

public class MeaningCloudJsonAnswer {

    private MeaningCloudStatus status;
    private String model;
    private String score_tag;
    private String agreement;
    private String subjectivity;
    private int confidence;
    private String irony;
    private List<MeaningCloudSentence> sentence_list;
    private List<MeaningCloudSentimentedObject> sentimented_entity_list;
    private List<MeaningCloudSentimentedObject> sentimented_concept_list;

    public MeaningCloudStatus getStatus() {
        return status;
    }

    public void setStatus(MeaningCloudStatus status) {
        this.status = status;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getScore_tag() {
        return score_tag;
    }

    public void setScore_tag(String score_tag) {
        this.score_tag = score_tag;
    }

    public String getAgreement() {
        return agreement;
    }

    public void setAgreement(String agreement) {
        this.agreement = agreement;
    }

    public String getSubjectivity() {
        return subjectivity;
    }

    public void setSubjectivity(String subjectivity) {
        this.subjectivity = subjectivity;
    }

    public int getConfidence() {
        return confidence;
    }

    public void setConfidence(int confidence) {
        this.confidence = confidence;
    }

    public String getIrony() {
        return irony;
    }

    public void setIrony(String irony) {
        this.irony = irony;
    }

    public List<MeaningCloudSentence> getSentence_list() {
        return sentence_list;
    }

    public void setSentence_list(List<MeaningCloudSentence> sentence_list) {
        this.sentence_list = sentence_list;
    }

    public List<MeaningCloudSentimentedObject> getSentimented_entity_list() {
        return sentimented_entity_list;
    }

    public void setSentimented_entity_list(List<MeaningCloudSentimentedObject> sentimented_entity_list) {
        this.sentimented_entity_list = sentimented_entity_list;
    }

    public List<MeaningCloudSentimentedObject> getSentimented_concept_list() {
        return sentimented_concept_list;
    }

    public void setSentimented_concept_list(List<MeaningCloudSentimentedObject> sentimented_concept_list) {
        this.sentimented_concept_list = sentimented_concept_list;
    }

}
