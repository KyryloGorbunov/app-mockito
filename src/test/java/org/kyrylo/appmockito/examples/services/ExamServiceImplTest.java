package org.kyrylo.appmockito.examples.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kyrylo.appmockito.examples.Source;
import org.kyrylo.appmockito.examples.models.Exam;
import org.kyrylo.appmockito.examples.repositories.ExamRepository;
import org.kyrylo.appmockito.examples.repositories.QuestionsRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExamServiceImplTest {

    @Mock
    ExamRepository repository;
    @Mock
    QuestionsRepository questionsRepository;

    @InjectMocks
    ExamServiceImpl examService;

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
////        repository = mock(ExamRepositoryDiff.class);
////        questionsRepository = mock(QuestionsRepository.class);
////        examService = new ExamServiceImpl(repository, questionsRepository);
//    }


    @Test
    void findExamByName() {
        when(repository.findAll()).thenReturn(Source.EXAMS);
        Optional<Exam> exam = examService.findExamByName("Math");

        assertTrue(exam.isPresent());
        assertEquals(5L, exam.orElseThrow().getId());
        assertEquals("Math", exam.get().getName());
    }

    @Test
    void findExamByNameEmpty() {
        List<Exam> exams = Collections.emptyList();

        when(repository.findAll()).thenReturn(exams);
        Optional<Exam> exam = examService.findExamByName("Math");

        assertFalse(exam.isPresent());
    }

    @Test
    void testQuestionsExam() {
        when(repository.findAll()).thenReturn(Source.EXAMS);
        when(questionsRepository.findQuestionsByExamId(anyLong())).thenReturn(Source.QUESTIONS);
        Exam exam = examService.findExamByNameWithQuestions("Math");

        assertEquals(5, exam.getQuestions().size());
        assertTrue(exam.getQuestions().contains("arithmetic"));
    }

    @Test
    void testQuestionsExamVerify() {
        when(repository.findAll()).thenReturn(Source.EXAMS);
        when(questionsRepository.findQuestionsByExamId(anyLong())).thenReturn(Source.QUESTIONS);
        Exam exam = examService.findExamByNameWithQuestions("Math");

        assertEquals(5, exam.getQuestions().size());
        assertTrue(exam.getQuestions().contains("arithmetic"));

        verify(repository).findAll();
        verify(questionsRepository).findQuestionsByExamId(anyLong());
    }

    @Test
    void testNotExistExamVerify() {
        //given
        when(repository.findAll()).thenReturn(Collections.emptyList());
        when(questionsRepository.findQuestionsByExamId(anyLong())).thenReturn(Source.QUESTIONS);

        //when
        Exam exam = examService.findExamByNameWithQuestions("Math2");
        assertNull(exam);

        //then
        verify(repository).findAll();
        verify(questionsRepository).findQuestionsByExamId(anyLong());
    }

    @Test
    void testSaveExam() {
        //given
        Exam newExam = Source.EXAM;
        newExam.setQuestions(Source.QUESTIONS);

        when(repository.save(any(Exam.class))).then(new Answer<Exam>() {

            Long sequence = 8L;

            @Override
            public Exam answer(InvocationOnMock invocation) throws Throwable {
                Exam exam = invocation.getArgument(0);
                exam.setId(sequence++);
                return exam;
            }
        });

        //when
        Exam exam = examService.save(newExam);

        assertNotNull(exam.getId());
        assertEquals(8L, exam.getId());
        assertEquals("Physical", exam.getName());

        //then
        verify(repository).save(any(Exam.class));
        verify(questionsRepository).saveSeveral(anyList());
    }

    @Test
    void testManageException() {
        when(repository.findAll()).thenReturn(Source.EXAMS);
        when(questionsRepository.findQuestionsByExamId(anyLong())).thenThrow(IllegalArgumentException.class);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            examService.findExamByNameWithQuestions("Math");
        });

        assertEquals(IllegalArgumentException.class, exception.getClass());

        verify(repository).findAll();
        verify(questionsRepository).findQuestionsByExamId(anyLong());
    }

    @Test
    void testManageExceptionWithIdNull() {
        when(repository.findAll()).thenReturn(Source.EXAMS_ID_NULL);
        when(questionsRepository.findQuestionsByExamId(isNull())).thenThrow(IllegalArgumentException.class);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            examService.findExamByNameWithQuestions("Math");
        });

        assertEquals(IllegalArgumentException.class, exception.getClass());

        verify(repository).findAll();
        verify(questionsRepository).findQuestionsByExamId(isNull());
    }

    @Test
    void testArgumentMatchers() {
        when(repository.findAll()).thenReturn(Source.EXAMS);
        when(questionsRepository.findQuestionsByExamId(anyLong())).thenReturn(Source.QUESTIONS);
        examService.findExamByNameWithQuestions("Math");

        verify(repository).findAll();
        verify(questionsRepository).findQuestionsByExamId(argThat(arg -> arg.equals(5L)));
        verify(questionsRepository).findQuestionsByExamId(eq(5L));
    }
}