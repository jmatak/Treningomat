package com.errorsonogsvijeta.treningomat.model.calendar;

import com.errorsonogsvijeta.treningomat.model.training.Training;
import com.errorsonogsvijeta.treningomat.model.training.TrainingGroup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

public class Term {
    private final static String DEFAULT_URL = "/training/";
    private final static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
    private final static Function<String, String> formatter = s -> s.substring(0, 10) + "T" + s.substring(11, s.length());

    private String title;
    private String url;
    private String start;
    private String end;

    public Term() {
    }

    public Term(TrainingGroup trainingGroup, Integer id, Date startAt, Date endsAt) {
        title = trainingGroup.getSport().toString() +  " : " + trainingGroup.getName() ;
        url = DEFAULT_URL + id;
        start = formatter.apply(DATE_FORMAT.format(startAt));
        end = formatter.apply(DATE_FORMAT.format(endsAt));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public static List<Term> toTerms(List<Training> trainings) {
        List<Term> terms = new ArrayList<>();
        for (Training training : trainings) {
            terms.add(Term.toTerm(training));
        }
        return terms;
    }

    private static Term toTerm(Training training) {
        return new Term(training.getTrainingGroup(),
                training.getId(),
                training.getStartAt(),
                training.getEndsAt());
    }
}
