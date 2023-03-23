package org.kyrylo.appmockito.examples.services;

import org.kyrylo.appmockito.examples.models.Exam;
import org.kyrylo.appmockito.examples.repositories.ExamRepository;
import org.kyrylo.appmockito.examples.repositories.QuestionsRepository;

import java.util.List;
import java.util.Optional;

public class ExamServiceImpl implements ExamService {

    private ExamRepository examRepository;
    private QuestionsRepository questionsRepository;

    public ExamServiceImpl(ExamRepository examRepository, QuestionsRepository questionsRepository) {
        this.examRepository = examRepository;
        this.questionsRepository = questionsRepository;
    }

    @Override
    public Exam save(Exam exam) {
        if (!exam.getQuestions().isEmpty()) {
            questionsRepository.saveSeveral(exam.getQuestions());
        }
        return examRepository.save(exam);
    }

    @Override
    public Optional<Exam> findExamByName(String name) {
        return examRepository.findAll().stream().filter(e -> e.getName().equals(name)).findFirst();
    }

    @Override
    public Exam findExamByNameWithQuestions(String name) {
        Optional<Exam> examOptional = findExamByName(name);
        Exam exam = null;
        if (examOptional.isPresent()) {
            exam = examOptional.get();
            List<String> questions = questionsRepository.findQuestionsByExamId(exam.getId());
            exam.setQuestions(questions);
        }
        return exam;
    }
}
