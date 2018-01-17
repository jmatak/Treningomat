package com.errorsonogsvijeta.treningomat.controllers;

import com.errorsonogsvijeta.treningomat.model.administration.Receipt;
import com.errorsonogsvijeta.treningomat.model.users.Attendant;
import com.errorsonogsvijeta.treningomat.model.users.Trainer;
import com.errorsonogsvijeta.treningomat.services.AttendantService;
import com.errorsonogsvijeta.treningomat.services.PaymentService;
import com.errorsonogsvijeta.treningomat.services.TrainerService;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
//https://stackoverflow.com/questions/38281512/how-to-read-data-from-java-properties-file-using-spring-boot
@PropertySource(ignoreResourceNotFound = true, value = "classpath:racun.properties")
public class PaymentController {
    @Autowired
    private Environment environment;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private TrainerService trainerService;
    @Autowired
    private AttendantService attendantService;
    private String lastPath;

    @RequestMapping(value = "/trainer/receipts/all", method = RequestMethod.GET)
    public ModelAndView showAllReceipts() {
        lastPath = "all";
        Trainer trainer = getLoggedTrainer();
        List<Receipt> receipts = paymentService.getReceiptsOfTrainerByDate(trainer);
        return getMAVforReceipts(receipts);
    }

    @RequestMapping(value = "/trainer/receipts/nonConfirmed", method = RequestMethod.GET)
    public ModelAndView showNonConfirmedReceipts() {
        lastPath = "nonConfirmed";
        Trainer trainer = getLoggedTrainer();
        List<Receipt> receipts = paymentService.getNonPaidReceiptsOfTrainer(trainer);
        return getMAVforReceipts(receipts);
    }

    ModelAndView getMAVforReceipts(List<Receipt> receipts) {
        ModelAndView modelAndView = new ModelAndView("trainer/receipts");
        modelAndView.addObject("receipts", receipts);
        return modelAndView;
    }


    @RequestMapping(value = "/trainer/receipt/pay", method = RequestMethod.POST)
    public String markAsPaid(@RequestParam("id") Integer id, @RequestParam("paid") Boolean paid) {
        paymentService.markAsPaid(id, paid);
        return "redirect:/trainer/receipts/" + lastPath;
    }

    @RequestMapping(value = "/trainer/receipt/delete", method = RequestMethod.POST)
    public String deleteReceipt(@RequestParam("id") Integer id) {
        paymentService.deleteReceipt(id);
        return "redirect:/trainer/receipts/" + lastPath;
    }

    @RequestMapping(value = {"/attendant/receipts"}, method = RequestMethod.GET)
    public ModelAndView getReceipts() {
        ModelAndView modelAndView = new ModelAndView("attendant/receipts");

        Attendant attendant = getLoggedAttendant();
        List<Receipt> receipts = paymentService.getAllReceiptsOfAttendant(attendant);

        modelAndView.addObject("receipts", receipts);
        return modelAndView;
    }

    @RequestMapping(value = {"/attendant/receipt/"}, method = RequestMethod.POST)
    public ModelAndView getReceipt(@RequestParam("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView("attendant/receipt");

        modelAndView.addObject("id", id);
        modelAndView.addObject("info", getInfo(paymentService.getReceipt(id)));
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value =
            "/attendant/receipt/2d/"
            , method = RequestMethod.POST, produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getReceipt2d(@RequestParam("id") Integer id) throws Exception {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://hub3.bigfish.software/api/v1/barcode");
        StringEntity params = new StringEntity(getPaymentJson(paymentService.getReceipt(id)));
        httpPost.addHeader("content-type", "application/json");
        httpPost.setEntity(params);

        HttpResponse response = httpClient.execute(httpPost);
        return IOUtils.toByteArray(response.getEntity().getContent());
    }

    private String getPaymentJson(Receipt receipt) {
        Attendant at = receipt.getAttendant();

        String amount = receipt.getTrainingGroup().getAmount().toString();
        String street = at.getStreetAndNumber();
        String place = at.getCity().getZipCode() + " " + at.getCity().getName();

        String nameRec = environment.getProperty("reciever");
        String strRec = environment.getProperty("address");
        String placeRec = environment.getProperty("city");
        String iban = environment.getProperty("iban");
        String model = environment.getProperty("modelNumb");
        String reference = receipt.getCreatedDate().toString().split("\\s+")[0];
        String purpose = environment.getProperty("purpose");    //  https://www.iso20022.org/standardsrepository/public/wqt/Description/mx/dico/codesets/_Z5yGktp-Ed-ak6NoX_4Aeg_1855256934
        String description = environment.getProperty("description") + receipt.getTrainingGroup().getName();

        return Util.barcode2DtoJson(amount, at, street, place, nameRec, strRec, placeRec, iban, model, reference, purpose, description);
    }

    private List<Pair<String, String>> getInfo(Receipt receipt) {
        Attendant attendant = receipt.getAttendant();

        ArrayList<Pair<String, String>> list = new ArrayList<>();
        list.add(new Pair<>("IZNOS", receipt.getTrainingGroup().getAmount().toString() + " kn"));
        list.add(new Pair<>("PLATITELJ", attendant.getName() + " " + attendant.getSurname()));
        list.add(new Pair<>("ULICA", attendant.getStreetAndNumber()));
        list.add(new Pair<>("GRAD", attendant.getCity().getZipCode() + " " + attendant.getCity().getName()));

        list.add(new Pair<>("PRIMATELJ", environment.getProperty("reciever")));
        list.add(new Pair<>("ULICA", environment.getProperty("address")));
        list.add(new Pair<>("GRAD", environment.getProperty("city")));
        list.add(new Pair<>("IBAN", environment.getProperty("iban")));
        list.add(new Pair<>("POZIV NA BROJ", environment.getProperty("model")));
        list.add(new Pair<>("DATUM", receipt.getCreatedDate().toString().split("\\s+")[0]));
        list.add(new Pair<>("OPIS PLAĆANJA", environment.getProperty("description") + receipt.getTrainingGroup().getName()));

        return list;
    }


    private Attendant getLoggedAttendant() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return attendantService.findAttendantByUsername(user.getUsername());
    }

    private Trainer getLoggedTrainer() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return trainerService.findTrainerByUsername(user.getUsername());
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

}
