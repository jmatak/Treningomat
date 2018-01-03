package com.errorsonogsvijeta.treningomat.controllers;

import com.errorsonogsvijeta.treningomat.model.administration.Receipt;
import com.errorsonogsvijeta.treningomat.model.users.Attendant;
import com.errorsonogsvijeta.treningomat.model.users.Trainer;
import com.errorsonogsvijeta.treningomat.services.*;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private TrainerService trainerService;
    @Autowired
    private AttendantService attendantService;

    @RequestMapping(value = "/trainer/receipts", method = RequestMethod.GET)
    public ModelAndView showAllReceipts() {
        ModelAndView modelAndView = new ModelAndView("/trainer/receipts");
        Trainer trainer = getThisTrainer();
        List<Receipt> receipts = paymentService.getReceiptsOfTrainerByDate(trainer);
        modelAndView.addObject("receipts", receipts);
        return modelAndView;
    }

    // todo bilo bi bolje ovo obaviti u javascriptu umjesto redirecta
    @RequestMapping(value = "/trainer/receipt/pay", method = RequestMethod.POST)
    public String markAsPaid(@RequestParam("id") String id, @RequestParam("paid") String paid) {
        paymentService.markAsPaid(id, paid);
        return "redirect:/trainer/receipts";
    }

    @RequestMapping(value = {"/attendant/receipts"}, method = RequestMethod.GET)
    public ModelAndView getReceipts() {
        Attendant attendant = getThisAttendant();
        List<Receipt> receipts = paymentService.getAllReceiptsOfAttendant(attendant);
        ModelAndView modelAndView = new ModelAndView("/attendant/receipts");
        modelAndView.addObject("receipts", receipts);
        return modelAndView;
    }

    @RequestMapping(value = {"/attendant/receipt/"}, method = RequestMethod.POST)
    public ModelAndView getReceipt(@RequestParam("id") String id) {
        ModelAndView modelAndView = new ModelAndView("/attendant/receipt");
        modelAndView.addObject("id", id);
        modelAndView.addObject("info", getInfo(paymentService.getReceipt(Integer.parseInt(id))));
        return modelAndView;
    }

    // todo popuni info.. dodati u bazu ostale bitne informacije
    private List<Pair<String, String>> getInfo(Receipt receipt) {
        Attendant attendant = receipt.getAttendant();

        ArrayList<Pair<String, String>> list = new ArrayList<>();
        list.add(new Pair<>("iznos", receipt.getTrainingGroup().getAmount().toString() + " hrk"));
        list.add(new Pair<>("ime i prezime (pošiljatelj)", attendant.getName() + " " + attendant.getSurname()));
        list.add(new Pair<>("ulica i kucni broj (pošiljatelj)", attendant.getStreetAndNumber()));
        list.add(new Pair<>("post. broj i grad (pošiljatelj)", attendant.getCity().getZipCode() + " " + attendant.getCity().getName()));

        list.add(new Pair<>("ime i prezime (primatelj)", "Matej Pipalovic"));
        list.add(new Pair<>("ulica i kucni broj (primatelj)", "Lj. Modeca 5"));
        list.add(new Pair<>("post. broj i grad (primatelj)", "44250 Petrinja"));
        list.add(new Pair<>("iban (primatelj)", "HR5223400093231102467"));
        list.add(new Pair<>("model", "HR99"));
        list.add(new Pair<>("referenca", receipt.getCreatedDate().toString().split("\\s+")[0]));
        list.add(new Pair<>("svrha", "SUBS"));
        list.add(new Pair<>("opis placanja", "clanarina: " + receipt.getTrainingGroup().getName()));

        return list;
    }

    public static class Pair<F, S> {
        private F f;
        private S s;

        public Pair(F f, S s) {
            this.f = f;
            this.s = s;
        }

        public F getF() {
            return f;
        }

        public void setF(F f) {
            this.f = f;
        }

        public S getS() {
            return s;
        }

        public void setS(S r) {
            this.s = r;
        }
    }

    @ResponseBody
    @RequestMapping(value =
            "/attendant/receipt/2d/"
            , method = RequestMethod.POST, produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getReceipt2d(@RequestParam("id") String id) throws Exception {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://hub3.bigfish.software/api/v1/barcode");
        StringEntity params = new StringEntity(getPaymentJson(paymentService.getReceipt(Integer.parseInt(id))));
        httpPost.addHeader("content-type", "application/json");
        httpPost.setEntity(params);

        HttpResponse response = httpClient.execute(httpPost);
        return IOUtils.toByteArray(response.getEntity().getContent());
    }

    private String getPaymentJson(Receipt receipt) {
        // todo, ispraviti podatke, paziti na kolicinu teksta, postoje ogranicenja
        Attendant at = receipt.getAttendant();

        String amount = receipt.getTrainingGroup().getAmount().toString();
        String street = at.getStreetAndNumber();
        String place = at.getCity().getZipCode() + " " + at.getCity().getName();

        // todo, ne mijenjati podatke, nece raditi
        String nameRec = "Matej Pipalovic";
        String strRec = "Lj. Modeca 5";
        String placeRec = "44250 Petrinja";
        String iban = "HR5223400093231102467";
        String model = "99";
        String reference = receipt.getCreatedDate().toString().split("\\s+")[0];
        String purpose = "SUBS";    //  https://www.iso20022.org/standardsrepository/public/wqt/Description/mx/dico/codesets/_Z5yGktp-Ed-ak6NoX_4Aeg_1855256934
        String description = "clanarina: " + receipt.getTrainingGroup().getName();

        return "{" +
                "\"renderer\": \"image\"," +
                "\"options\": {" +
                "\"format\": \"png\"," +
                "\"color\": \"#000000\"" +
                "}," +
                "\"data\": {" +
                "\"amount\": " + amount + "," +
                "\"sender\": {" +
                "\"name\": \"" + at.getName() + " " + at.getSurname() + "\"," +
                "\"street\": \"" + street + "\"," +
                "\"place\": \"" + place + "\"" +
                "}," +
                "\"receiver\": {" +
                "\"name\": \"" + nameRec + "\"," +
                "\"street\": \"" + strRec + "\"," +
                "\"place\": \"" + placeRec + "\"," +
                "\"iban\": \"" + iban + "\"," +
                "\"model\": \"" + model + "\"," +
                "\"reference\": \"" + reference + "\"" +
                "}," +
                "\"purpose\": \"" + purpose + "\"," +
                "\"description\": \"" + description + "\"" +
                "}" +
                "}";
    }


    private Attendant getThisAttendant() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return attendantService.getAttendantWithUserName(user.getUsername());
    }

    private Trainer getThisTrainer() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return trainerService.findTrainerByUsername(user.getUsername());
    }
}
