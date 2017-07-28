package fr.sqli.cordialement.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table(name = "email")
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="smileys")
    private Boolean smileysPresent;

    @Column(name="bold")
    private Boolean boldPresent;

    @Column(name="majuscules")
    private Boolean capitalLettersPresent;

    @Column(name="couleurs")
    private Boolean colorsPresent;

    @Column(name="fautes_ortho")
    private Boolean typosPresent;

    @Column(name="nb_phrases")
    private Integer nbSentences;

    @Column(name="est_analyse")
    private Boolean isAnalysed = false;

    @NotNull
    @Size(min=10)
    @Column(columnDefinition = "MEDIUMTEXT", name="contenu")
    private String content;

    @OneToMany(mappedBy = "email", cascade = CascadeType.ALL)
    private List<EmailScore> emailScores = new LinkedList<>();

    @OneToMany(mappedBy = "email", cascade = CascadeType.ALL)
    private List<Fragment> fragments = new LinkedList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getSmileysPresent() {
        return smileysPresent;
    }

    public void setSmileysPresent(Boolean smileysPresent) {
        this.smileysPresent = smileysPresent;
    }

    public Boolean getBoldPresent() {
        return boldPresent;
    }

    public void setBoldPresent(Boolean boldPresent) {
        this.boldPresent = boldPresent;
    }

    public Boolean getCapitalLettersPresent() {
        return capitalLettersPresent;
    }

    public void setCapitalLettersPresent(Boolean capitalLettersPresent) {
        this.capitalLettersPresent = capitalLettersPresent;
    }

    public Boolean getColorsPresent() {
        return colorsPresent;
    }

    public void setColorsPresent(Boolean colorsPresent) {
        this.colorsPresent = colorsPresent;
    }

    public Boolean getTyposPresent() {
        return typosPresent;
    }

    public void setTyposPresent(Boolean typosPresent) {
        this.typosPresent = typosPresent;
    }

    public Integer getNbSentences() {
        return nbSentences;
    }

    public void setNbSentences(Integer nbSentences) {
        this.nbSentences = nbSentences;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<EmailScore> getEmailScores() {
        return this.emailScores;
    }

    public void setEmailScores(List<EmailScore> emailScores) {
        this.emailScores = emailScores;
    }

    public void addEmailScore(EmailScore f) {
        this.emailScores.add(f);
    }

    public EmailScore getEmailScoreByApi(API api) {
        EmailScore searchedEmailScore = null;

        for (EmailScore es : this.emailScores) {
            if (es.getApi() == api) {
                searchedEmailScore = es;
                break;
            }
        }

        return searchedEmailScore;
    }

    public EmailScore getEmailScoreByApiName(String apiName) {
        return getEmailScoreByApi(API.valueOf(apiName.toUpperCase()));
    }

    public List<Fragment> getFragments() {
        return this.fragments;
    }

    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
    }

    public void addFragment(Fragment f) {
        this.fragments.add(f);
    }

    public Boolean isAnalysed() {
        return isAnalysed;
    }

    public Boolean getIsAnalysed() {
        return isAnalysed;
    }

    public void setIsAnalysed(Boolean isAnalysed) {
        this.isAnalysed = isAnalysed;
    }
}
