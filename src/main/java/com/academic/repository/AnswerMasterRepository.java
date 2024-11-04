package com.academic.repository;

import com.academic.model.AnswerMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AnswerMasterRepository extends JpaRepository<AnswerMaster,Long> {

    @Query(value = "SELECT am FROM AnswerMaster AS am WHERE am.questionId = :questionId",nativeQuery = false)
    AnswerMaster getQuestionByQuestionId(Long questionId);
}
