package com.academic.repository;

import com.academic.model.SubjectMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<SubjectMaster,Long> {

    boolean existsBySubjectName(String subjectName);

    boolean existsBySubjectIdAndIsDeleted(Long subjectId, boolean b);
}
