package org.kyrylo.appmockito.examples;

import org.kyrylo.appmockito.examples.models.Exam;

import java.util.Arrays;
import java.util.List;

public class Source {
    public final static List<Exam> EXAMS = Arrays.asList(new Exam(5L, "Math"),
            new Exam(6L, "Language"), new Exam(7L, "History"));

    public final static List<String> QUESTIONS = Arrays.asList("arithmetic", "integral", "derivatives",
            "trigonometry", "geometry");

    public final static List<Exam> EXAMS_ID_NULL = Arrays.asList(new Exam(null, "Math"),
            new Exam(null, "Language"), new Exam(null, "History"));

    public final static List<Exam> EXAMS_ID_NEGATIVES = Arrays.asList(new Exam(-5L, "Math"),
            new Exam(-6L, "Language"), new Exam(null, "History"));

    public final static Exam EXAM = new Exam(null, "Physical");
}
