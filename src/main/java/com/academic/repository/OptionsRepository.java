package com.academic.repository;

import com.academic.model.Options;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OptionsRepository extends JpaRepository<Options,Long> {

    @Query(value = "SELECT op FROM Options AS op WHERE op.questionId= :studentExamQuestionId")
    List<Options> getAllDataByQuestionId(Long studentExamQuestionId);

    @Query(value = "SELECT op FROM Options AS op LEFT JOIN AnswerMaster AS am ON op.questionId=am.questionId \n" +
            "WHERE am.answer= :answer")
    List<Options> getAllDataByAnswer(Long answer);
}
