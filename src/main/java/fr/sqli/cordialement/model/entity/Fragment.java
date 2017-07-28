package fr.sqli.cordialement.model.entity;

import fr.sqli.cordialement.utils.ColorUtils;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Fragment {

    public enum TYPE {
        TEXT, BOLD, CAPS, COLORED
    }

    public enum COLOR {
        BLACK, WHITE, RED, ORANGE, GREEN, BLUE, YELLOW, GREY, PURPLE, PINK, BROWN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_email")
    private Email email;

    @Column(columnDefinition = "TEXT", name = "texte_brut")
    private String plainText;

    @Column(columnDefinition = "TEXT", name = "text")
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TYPE type;

    @Enumerated(EnumType.STRING)
    @Column(name = "color")
    private COLOR color;

    @OneToMany(mappedBy = "fragment", cascade = CascadeType.ALL)
    private Set<Score> scores;

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

    public String getPlainText() {
        return plainText;
    }

    public void setPlainText(String plainText) {
        this.plainText = plainText;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public Set<Score> getScores() {
        return scores;
    }

    public Score getScoreByApi(API api) {
        Score result = null;

        for (Score score : getScores()) {
            if (score.getApi() == api) {
                result = score;
            }
        }

        return result;
    }

    public void setScores(Set<Score> scores) {
        this.scores = scores;
    }

    public COLOR getColor() {
        return color;
    }

    public String getColorName() {
        return color.toString().toLowerCase();
    }

    public void setColor(COLOR color) {
        this.color = color;
    }
}
