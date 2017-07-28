package fr.sqli.cordialement.model.bean;

import java.util.List;

public class MeaningCloudSegment {
    private String text;
    private String segment_type;
    private int inip;
    private int endp;
    private String confidence;
    private String score_tag;
    private String agreement;
    private List<MeaningCloudPolarityTerm> polarity_term_list;
    private List<MeaningCloudSegment> segment_list;
    private List<MeaningCloudSentimentedObject> sentimented_entity_list;
    private List<MeaningCloudSentimentedObject> sentimented_concept_list;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSegment_type() {
        return segment_type;
    }

    public void setSegment_type(String segment_type) {
        this.segment_type = segment_type;
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

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
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

    public List<MeaningCloudPolarityTerm> getPolarity_term_list() {
        return polarity_term_list;
    }

    public void setPolarity_term_list(List<MeaningCloudPolarityTerm> polarity_term_list) {
        this.polarity_term_list = polarity_term_list;
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
