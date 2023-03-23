package org.kyrylo.appmockito.examples.repositories;

import org.kyrylo.appmockito.examples.models.Exam;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ExamRepositoryDiff implements ExamRepository {

    @Override
    public Exam save(Exam exam) {
        return null;
    }

    @Override
    public List<Exam> findAll() {
        try {
            System.out.println("**************");
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
