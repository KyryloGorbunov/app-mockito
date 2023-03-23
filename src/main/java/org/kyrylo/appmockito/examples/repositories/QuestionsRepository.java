package org.kyrylo.appmockito.examples.repositories;

import java.util.List;

public interface QuestionsRepository {

    void saveSeveral(List<String> questions);

    List<String> findQuestionsByExamId(Long id);
}
