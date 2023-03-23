package org.kyrylo.appmockito.examples.repositories;

import org.kyrylo.appmockito.examples.models.Exam;

import java.util.List;

public interface ExamRepository {

    Exam save(Exam exam);

    List<Exam> findAll();
}
