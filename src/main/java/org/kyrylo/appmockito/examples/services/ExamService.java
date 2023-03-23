package org.kyrylo.appmockito.examples.services;

import org.kyrylo.appmockito.examples.models.Exam;

import java.util.Optional;

public interface ExamService {
    Exam save(Exam exam);

    Optional<Exam> findExamByName(String name);

    Exam findExamByNameWithQuestions(String name);
}
