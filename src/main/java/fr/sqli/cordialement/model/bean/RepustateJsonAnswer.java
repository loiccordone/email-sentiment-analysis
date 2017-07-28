package fr.sqli.cordialement.model.bean;

import java.util.List;

public class RepustateJsonAnswer {

    private String status;
    private List<RepustateScore> results;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<RepustateScore> getResults() {
        return results;
    }

    public void setResults(List<RepustateScore> results) {
        this.results = results;
    }
}
