package com.academic.service.Impl;

import com.academic.exception.IdNotFound;
import com.academic.model.StreamMaster;
import com.academic.repository.StreamMasterRepository;
import com.academic.service.IStreamMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StreamMasterService implements IStreamMasterService {

    @Autowired
    private StreamMasterRepository streamMasterRepository ;

    @Override
    public Object saveOrUpdateStream(StreamMaster streamMaster) {
        if(streamMasterRepository.existsById(streamMaster.getStreamId())){
            StreamMaster streamMaster1 = streamMasterRepository.findById(streamMaster.getStreamId()).get();
            if(streamMasterRepository.existsByStreamName(streamMaster.getStreamName())){
                throw new IdNotFound("Stream Already Exist!!!") ;
            }else {
                streamMaster1.setStreamId(streamMaster.getStreamId());
                streamMaster1.setStreamName(streamMaster.getStreamName());
                streamMasterRepository.save(streamMaster1);
                return "Updated Successfully!!!";
            }
        }else{
            if(streamMasterRepository.existsByStreamName(streamMaster.getStreamName())){
                throw new IdNotFound("Stream Already Exist!!!") ;
            }
            streamMasterRepository.save(streamMaster) ;
            return "Inserted Successfully!!!" ;
        }
    }

    @Override
    public Object getStreamById(Long streamId) {
        if(streamMasterRepository.existsById(streamId)){
            return streamMasterRepository.findById(streamId) ;
        }else{
            throw new IdNotFound("Id Not Exist!!!") ;
        }
    }

    @Override
    public Object getAllStream() {
        return streamMasterRepository.findAll();
    }

    @Override
    public Object softDeleteStreamById(Long streamId) {
        if(streamMasterRepository.existsById(streamId)){
            StreamMaster streamMaster = streamMasterRepository.findById(streamId).get() ;
            streamMaster.setIsDeleted(true);
            streamMasterRepository.save(streamMaster) ;
            return "Deleted Successfully!!!" ;
        }else{
            throw new IdNotFound("Id not Exist!!!");
        }
    }

    public Object changeStatus(Long streamId){
        if(streamMasterRepository.existsById(streamId)){
            StreamMaster streamMaster = streamMasterRepository.findById(streamId).get() ;
            if(streamMaster.isActive()){
                streamMaster.setActive(false);
            }else{
                streamMaster.setActive(true);
            }
            streamMasterRepository.save(streamMaster) ;
            return "Change Stream Active Status!!!" ;
        }else{
            throw new IdNotFound("Stream Not Exist!!!") ;
        }
    }
}
