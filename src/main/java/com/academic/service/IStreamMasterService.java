package com.academic.service;

import com.academic.model.StreamMaster;

public interface IStreamMasterService {

    public Object saveOrUpdateStream(StreamMaster streamMaster) ;

    public Object getStreamById(Long streamId) ;

    public Object getAllStream() ;

    public Object softDeleteStreamById(Long streamId) ;

    public Object changeStatus(Long streamId) ;
    }
