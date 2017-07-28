package fr.sqli.cordialement.model.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "valeur")
    private Double valeur;

    @ManyToOne
    @JoinColumn(name = "id_fragment")
    private Fragment fragment;

    @Enumerated(EnumType.STRING)
    @Column(name = "api")
    private API api;

    @Column(name = "date")
    private Date date;

    public Score() { }

    public Score(Fragment fragment, API api, Double valeur, Date date) {
        this.fragment = fragment;
        this.api = api;
        this.valeur = valeur;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValeur() {
        return valeur;
    }

    public void setValeur(Double valeur) {
        this.valeur = valeur;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public API getApi() {
        return api;
    }

    public void setApi(API api) {
        this.api = api;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
