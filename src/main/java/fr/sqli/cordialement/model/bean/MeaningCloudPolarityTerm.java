package fr.sqli.cordialement.model.bean;

import java.util.List;

public class MeaningCloudPolarityTerm {
    private String text;
    private int inip;
    private int endp;
    private String tag_stack;
    private String confidence;
    private String score_tag;
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

    public String getTag_stack() {
        return tag_stack;
    }

    public void setTag_stack(String tag_stack) {
        this.tag_stack = tag_stack;
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
