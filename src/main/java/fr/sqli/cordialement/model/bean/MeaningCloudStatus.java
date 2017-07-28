package fr.sqli.cordialement.model.bean;

public class MeaningCloudStatus {

    private String code;
    private String msg;
    private int credits;
    private int remaining_credits;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getRemaining_credits() {
        return remaining_credits;
    }

    public void setRemaining_credits(int remaining_credits) {
        this.remaining_credits = remaining_credits;
    }

}
