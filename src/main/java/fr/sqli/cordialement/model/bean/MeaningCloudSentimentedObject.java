package fr.sqli.cordialement.model.bean;

public class MeaningCloudSentimentedObject {
    private String form;
    private String id;
    private String variant;
    private int inip;
    private int endp;
    private String type;
    private String score_tag;

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getScore_tag() {
        return score_tag;
    }

    public void setScore_tag(String score_tag) {
        this.score_tag = score_tag;
    }
}
