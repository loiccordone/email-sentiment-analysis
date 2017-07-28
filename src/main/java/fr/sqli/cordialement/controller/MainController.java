package fr.sqli.cordialement.controller;

import fr.sqli.cordialement.model.entity.*;
import fr.sqli.cordialement.services.EmailService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.validation.Valid;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MainController extends WebMvcConfigurerAdapter {

    private EmailService emailService;

    @Autowired
    public MainController(EmailService emailService) {
        this.emailService = emailService;
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/addemail")
    public String addEmail(Model model) {
        model.addAttribute("email", new Email());
        return "addemail";
    }

    @PostMapping("/addemail")
    public String addEmailSubmit(@Valid @ModelAttribute Email email, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "addemail";
        }

        this.emailService.addEmail(email.getContent());
        model.addAttribute("idAddedEmail", email.getId());

        return "redirect:results";
    }

    @RequestMapping("/addmaildb")
    public String addMailDb() throws IOException {

        ClassPathResource classPathResource = new ClassPathResource("static/emails.csv");

        InputStream inputStream = classPathResource.getInputStream();
        File emailsFile = File.createTempFile("emailsTemp", ".txt");
        try {
            FileUtils.copyInputStreamToFile(inputStream, emailsFile);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(emailsFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                this.emailService.addEmail(line);
            }
        }

        return "redirect:results";
    }

    @RequestMapping("/addsurveydb")
    public String addSurveyDb() throws IOException {

        ClassPathResource classPathResource = new ClassPathResource("static/survey.csv");

        InputStream inputStream = classPathResource.getInputStream();
        File emailsFile = File.createTempFile("surveyTemp", ".text");
        try {
            FileUtils.copyInputStreamToFile(inputStream, emailsFile);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(emailsFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] surveyScore = line.split(";");
                Long id = Long.valueOf(surveyScore[0]);
                Double score = Double.valueOf(surveyScore[1]);
                Email email = this.emailService.findEmailById(id);
                email.addEmailScore(new EmailScore(email, API.SURVEY, score, new Date()));
                this.emailService.save(email);
            }
        }

        return "redirect:results";
    }

    @RequestMapping("/results")
    public String viewResults(Model model) {
        List<Email> emails = new ArrayList<>();
        for (Email e : this.emailService.findAllEmail()) {
            emails.add(e);
        }
        model.addAttribute("emails", emails);

        return "results";
    }

    @RequestMapping("/analyseAll")
    public String analyseAllEmail() {
        this.emailService.analyseAllEmail();

        return "redirect:results";
    }

    @RequestMapping("/analyse")
    public String analyseEmail(@RequestParam(value="id", defaultValue="1") Long id, Model model) {
        model.addAttribute("idAnalysedEmail", id);
        this.emailService.analyseEmailById(id);

        return "redirect:results";
    }

    @RequestMapping("/scoreAll")
    public String scoreAllEmail(@RequestParam(value="api", defaultValue="all") String api) {
        if (!api.equals("all")) {
            this.emailService.scoreAllEmailsWithApi(API.valueOf(api.toUpperCase()));
        } else {
            this.emailService.scoreAllEmails();
        }

        return "redirect:results";
    }

    @RequestMapping("/score")
    public String scoreEmail(@RequestParam(value="id", defaultValue="1") Long id,
                             @RequestParam(value="api", defaultValue="all") String api,
                             @RequestParam(value = "idFin", defaultValue = "0") Long idFin) {

        if (!api.equals("all")) {
            if(idFin != 0) {
                this.emailService.scoreEmailsByIdsWithApi(id, idFin, API.valueOf(api.toUpperCase()));
            } else {
                this.emailService.scoreEmailByIdWithApi(id, API.valueOf(api.toUpperCase()));
            }
        } else {
            if (idFin != 0) {
                this.emailService.scoreEmailsByIds(id, idFin);
            } else {
                this.emailService.scoreEmailById(id);
            }
        }

        return "redirect:results";
    }

    @RequestMapping(value="/exportCSV", produces = "application/json")
    @ResponseBody
    public String exportCSV() {
        String separator = ";";
        StringBuilder sb = new StringBuilder();
        sb.append("ID;Email;Indico;Microsoft;Aylien;Meaning_Cloud;Repustate;Smileys;Score;Survey_Score").append(System.lineSeparator());

        for (Email email : this.emailService.findAllEmail()) {
            sb.append(email.getId()).append(separator);
            sb.append("\"").append(email.getContent().replaceAll("\"", "\"\"")).append("\"");
            for (API api : API.values()) {
                sb.append(separator);
                if (email.getEmailScoreByApi(api) != null) {
                    sb.append(email.getEmailScoreByApi(api).getScore());
                }
            }
            sb.append(System.lineSeparator());
        }

        return sb.toString();
    }

    @RequestMapping(value="/exportFragmentsCSV", produces = "application/json")
    @ResponseBody
    public String exportFragmentsCSV() {
        String separator = ";";
        StringBuilder sb = new StringBuilder();
        sb.append("ID;ID_Email;Contenu;Size;Type;Couleur;Indico;Microsoft;Aylien;Meaning_Cloud;Repustate;Smileys;Score;Survey_Score").append(System.lineSeparator());

        for (Email email : this.emailService.findAllEmail()) {
            for (Fragment f : email.getFragments()) {
                sb.append(f.getId()).append(separator);
                sb.append(f.getEmail().getId()).append(separator);
                sb.append("\"").append(f.getText().replaceAll("\"", "\"\"")).append("\"").append(separator);
                sb.append(f.getText().length()).append(separator);
                sb.append(f.getType().toString()).append(separator);
                sb.append(f.getColorName());
                for (API api : API.values()) {
                    sb.append(separator);
                    for (Score score : f.getScores()) {
                        if (score.getApi() == api) {
                            sb.append(score.getValeur());
                        }
                    }
                }
                sb.append(System.lineSeparator());
            }
        }

        return sb.toString();
    }

}
