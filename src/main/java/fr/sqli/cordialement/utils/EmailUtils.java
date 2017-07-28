package fr.sqli.cordialement.utils;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.SentenceUtils;
import edu.stanford.nlp.process.DocumentPreprocessor;
import fr.sqli.cordialement.model.entity.API;
import fr.sqli.cordialement.model.entity.Email;
import fr.sqli.cordialement.model.entity.Fragment;
import fr.sqli.cordialement.model.entity.Score;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailUtils {

    private final static Pattern closingHtmlTagsPattern = Pattern.compile("^(\\s*</[^>]*>)+");
    private final static Pattern capitalLettersPartsPattern = Pattern.compile("([A-Z]{2}[A-Z\\s]*\\b)");
    private final static Pattern cssBoldPattern = Pattern.compile(".*font-weight\\s*:\\s*bold.*");
    private final static Pattern cssColorPattern = Pattern.compile(".*color\\s*:.*");
    private final static Pattern cssColorNamePattern = Pattern.compile(".*color\\s*:\\s*([a-zA-Z]+).*");
    private final static Pattern cssRgbColorPattern = Pattern.compile(".*color\\s*:\\s*rgba?\\((?:(\\d+)%?[,\\s]+)(?:(\\d+)%?[,\\s]+)(?:(\\d+)%?[,\\s]*)(?:([\\d\\.]+%?))?\\).*");
    private final static Pattern cssHexColorPattern = Pattern.compile(".*color\\s*:\\s*(?:#([0-9a-f]{6})|#([0-9a-f]{3})).*");

    public static List<String> textToSentences(String text){
        Reader reader = new StringReader(text);
        DocumentPreprocessor dp = new DocumentPreprocessor(reader);
        List<String> sentenceList = new ArrayList<>();

        for (List<HasWord> sentence : dp) {
            String sentenceString = SentenceUtils.listToString(sentence);
            sentenceList.add(sentenceString);
        }

        ListIterator<String> it = sentenceList.listIterator();
        int index = 0;

        while(it.hasNext()) {
            String s = it.next();
            String sentence = cleanSentence(s);
            sentenceList.set(index, sentence);

            Matcher matcher = closingHtmlTagsPattern.matcher(sentence);
            if (matcher.find()) {
                String newSentence = sentence.substring(matcher.group(0).length());
                sentenceList.set(index, newSentence);

                if (it.hasPrevious()) {
                    sentenceList.set(index-1, sentenceList.get(index-1) + matcher.group(0));
                }
            }
            index++;
        }

        return sentenceList;
    }

    private static String cleanSentence(String s) {
        return s.replaceAll("-LRB-","(")
                .replaceAll("-RRB-", ")")
                .replaceAll("-LCB-", "{")
                .replaceAll("-RCB-", "}")
                .replaceAll("-LSB-", "[")
                .replaceAll("-RSB-", "]")
                .replaceAll("\\u00A0", " ")
                .replaceAll("``", "\"")
                .replaceAll("''", "\"");
    }

    public static void addFragments(List<?> fragments, Email email, Fragment.TYPE type) {
        for (Object fragment : fragments) {
            Fragment f = new Fragment();
            f.setEmail(email);
            f.setPlainText(fragment.toString());
            f.setText(parseHtml(fragment.toString()));
            f.setType(type);
            if (type == Fragment.TYPE.COLORED) {
                    f.setColor(Fragment.COLOR.valueOf(getColorFromColorElement((Element) fragment).toUpperCase()));
            } else {
                f.setColor(Fragment.COLOR.valueOf("BLACK"));
            }
            email.addFragment(f);
        }
    }

    private static String parseHtml(String sentence) {
        String parsedSentence;

        Source source = new Source(sentence);
        parsedSentence = source.getTextExtractor().toString().trim();

        return parsedSentence;
    }

    public static List<String> getAllCapitalLettersParts(String email) {
        List<String> capitalLettersParts = new ArrayList<>();
        Matcher m = capitalLettersPartsPattern.matcher(parseHtml(email));

        while (m.find()) {
            for (int i = 1; i <= m.groupCount() ; i++) {
                capitalLettersParts.add(m.group(i));
            }
        }

        return capitalLettersParts;
    }

    public static List<Element> getAllBoldElements(String email) {
        List<Element> boldElements = new ArrayList<>();
        Source source = new Source(email);

        boldElements.addAll(source.getAllElements(HTMLElementName.STRONG));
        boldElements.addAll(source.getAllElements(HTMLElementName.B));
        boldElements.addAll(source.getAllElements("style", cssBoldPattern));

        return boldElements;
    }

    public static List<Element> getAllColoredElements(String email) {
        List<Element> coloredElements = new ArrayList<>();
        Source source = new Source(email);

        coloredElements.addAll(source.getAllElements("style", cssColorPattern));

        return coloredElements;
    }

    public static String getColorFromColorElement(Element element) {
        Matcher matcher;
        ColorUtils.Color basicColor = ColorUtils.Color.getDefault();

        matcher = cssHexColorPattern.matcher(element.getFirstElement().getAttributeValue("style"));
        if (matcher.find()) {
            /* hex */
            String hexColor6 = matcher.group(1);
            String hexColor3 = matcher.group(2);

            if (hexColor3 != null) {
                String hexColor = "" + hexColor3.charAt(0) + hexColor3.charAt(0) + hexColor3.charAt(1)
                        + hexColor3.charAt(1) + hexColor3.charAt(2) + hexColor3.charAt(2);
                basicColor = ColorUtils.getBasicColorFromHex(Integer.parseInt(hexColor, 16));
            } else {
                basicColor = ColorUtils.getBasicColorFromHex(Integer.parseInt(hexColor6, 16));
            }
        } else {
            /* RGB */
            matcher = cssRgbColorPattern.matcher(element.getFirstElement().getAttributeValue("style"));
            if (matcher.find()) {
                int r = Integer.parseInt(matcher.group(1));
                int g = Integer.parseInt(matcher.group(2));
                int b = Integer.parseInt(matcher.group(3));
                basicColor = ColorUtils.getBasicColorFromRgb(r, g, b);
            } else {
                /* name */
                matcher = cssColorNamePattern.matcher(element.getFirstElement().getAttributeValue("style"));
                if (matcher.find()) {
                    basicColor = ColorUtils.getBasicColorFromColorName(matcher.group(1));
                }
            }
        }

        return basicColor.getName();
    }

    public static Double modelEmailScore(Email email, Double[] param) {
        List<Fragment> fragments = email.getFragments();
        int nbFragments = 0;
        Double scoreFragment;
        Double total = 0.0;
        int i;

        for (Fragment f : fragments) {
            i = 1;
            scoreFragment = f.getText().length() * param[0];

            for (API api : API.values()) {
                Score score = f.getScoreByApi(api);

                if (api == API.SMILEYS) {
                    if (email.getSmileysPresent()) {
                        if (score != null) {
                            scoreFragment *= score.getValeur() * param[i];
                        }
                    }
                } else if (api != API.GLOBAL && api != API.SURVEY) {
                    scoreFragment += score.getValeur() * param[i];
                    i++;
                }

                if (f.getType() == Fragment.TYPE.BOLD) {
                    scoreFragment = cubic(scoreFragment, param[7]);
                } else if (f.getType() == Fragment.TYPE.CAPS) {
                    scoreFragment = cubic(scoreFragment, param[8]);
                }
            }

            nbFragments++;
            total += scoreFragment;
        }

        return  total / nbFragments;
    }

    private static Double cubic(Double x, Double c) {
        Double a = 2*(c-1);
        Double b = 3*(1-c);

        return a*x*x*x + b*x*x + c*x;
    }
}
