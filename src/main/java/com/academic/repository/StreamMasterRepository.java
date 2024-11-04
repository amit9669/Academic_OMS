package com.academic.repository;

import com.academic.model.StreamMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StreamMasterRepository extends JpaRepository<StreamMaster,Long> {

    boolean existsByStreamName(String streamName);

    boolean existsByStreamIdAndIsDeleted(Long studentStreamId, boolean b);

    boolean existsByStreamNameIgnoreCase(String streamName);
}
