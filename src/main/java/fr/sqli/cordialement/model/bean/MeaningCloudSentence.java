package fr.sqli.cordialement.model.bean;

import java.util.List;

public class MeaningCloudSentence {
    private String text;
    private int inip;
    private int endp;
    private String bop;
    private int confidence;
    private String score_tag;
    private String agreement;
    private List<MeaningCloudSegment> segment_list;
    private List<MeaningCloudSentimentedObject> sentimented_entity_list;
    private List<MeaningCloudSentimentedObject> sentimented_concept_list;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getInip() {
        return inip;
    }

    public void setInip(int inip) {
        this.inip = inip;
    }

    public int getEndp() {
        return endp;
    }

    public void setEndp(int endp) {
        this.endp = endp;
    }

    public String getBop() {
        return bop;
    }

    public void setBop(String bop) {
        this.bop = bop;
    }

    public int getConfidence() {
        return confidence;
    }

    public void setConfidence(int confidence) {
        this.confidence = confidence;
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

    public List<MeaningCloudSegment> getSegment_list() {
        return segment_list;
    }

    public void setSegment_list(List<MeaningCloudSegment> segment_list) {
        this.segment_list = segment_list;
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
