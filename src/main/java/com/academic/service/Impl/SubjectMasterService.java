package com.academic.service.Impl;

import com.academic.exception.IdNotFound;
import com.academic.model.SubjectMaster;
import com.academic.repository.SubjectRepository;
import com.academic.service.ISubjectMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubjectMasterService implements ISubjectMasterService {

    @Autowired
    private SubjectRepository subjectRepository ;

    @Override
    public Object saveOrUpdateSubject(SubjectMaster subjectMaster) {
        if(subjectRepository.existsById(subjectMaster.getSubjectId())){
            SubjectMaster subjectMaster1 = subjectRepository.findById(subjectMaster.getSubjectId()).get();
            subjectMaster1.setSubjectLanguage(subjectMaster.getSubjectLanguage());
            if(!subjectRepository.existsBySubjectName(subjectMaster.getSubjectName())){
                subjectMaster1.setSubjectName(subjectMaster.getSubjectName());
                subjectMaster1.setSubjectLanguage(subjectMaster.getSubjectLanguage());
                subjectRepository.save(subjectMaster1) ;
                return "Updated Successfully!!!" ;
            }else{
                throw new IdNotFound("Subject Already Taken!!") ;
            }
        }else{
            if(subjectRepository.existsBySubjectName(subjectMaster.getSubjectName())){
                throw new IdNotFound("Subject Already Taken!!") ;
            }else{
                subjectRepository.save(subjectMaster) ;
                return "Inserted Successfully!!!";
            }
        }
    }

    @Override
    public Object getSubjectById(Long subjectId) {
        if(subjectRepository.existsById(subjectId)){
            return subjectRepository.findById(subjectId).get() ;
        }else{
            throw new IdNotFound("Subject Id Not Exist!!!") ;
        }
    }

    @Override
    public Object getAllSubject() {
        return subjectRepository.findAll();
    }

    @Override
    public Object softDeleteSubjectById(Long subjectId) {
        if(subjectRepository.existsById(subjectId)){
            SubjectMaster subjectMaster = subjectRepository.findById(subjectId).get();
            subjectMaster.setIsDeleted(true);
            subjectRepository.save(subjectMaster) ;
            return "Subject Deleted!!!" ;
        }else{
            throw new IdNotFound("Subject Id Not Exist!!!") ;
        }
    }

    @Override
    public Object changeStatus(Long subjectId) {
        if(subjectRepository.existsById(subjectId)){
            SubjectMaster subjectMaster = subjectRepository.findById(subjectId).get();
            if(subjectMaster.isActive()){
                subjectMaster.setActive(false);
            }else{
                subjectMaster.setActive(true);
            }
            subjectRepository.save(subjectMaster) ;
            return "Change Subject Active status" ;
        }else{
            throw new IdNotFound("Subject ID Not Exist!!!") ;
        }
    }
}
