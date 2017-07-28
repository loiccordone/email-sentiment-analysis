package fr.sqli.cordialement.model.entity;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "scores_email")
public class EmailScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_email")
    private Email email;

    @Enumerated(EnumType.STRING)
    @Column(name = "api")
    private API api;

    @Column(name = "score")
    private Double score;

    @Column(name = "lastUpdated")
    private Date lastUpdated;

    public EmailScore() {
    }

    public EmailScore(Email email, API api, Double score, Date lastUpdated) {
        this.email = email;
        this.api = api;
        this.score = score;
        this.lastUpdated = lastUpdated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public API getApi() {
        return api;
    }

    public void setApi(API api) {
        this.api = api;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String displayLastUpdated() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy 'at' HH:mm");
        return dateFormat.format(this.lastUpdated);
    }
}
