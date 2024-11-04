package com.academic.controller;

import com.academic.exception.IdNotFound;
import com.academic.model.StreamMaster;
import com.academic.model.response.EntityResponse;
import com.academic.service.IStreamMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/streamMaster")
public class StreamMasterController {

    @Autowired
    private IStreamMasterService iStreamMasterService ;

    @PostMapping("/saveOrUpdateStream")
    public ResponseEntity<?> saveOrUpdateStream(@RequestBody StreamMaster streamMaster){
       try {
           return new ResponseEntity<>(iStreamMasterService.saveOrUpdateStream(streamMaster), HttpStatus.OK);
       }catch (IdNotFound e){
           return new ResponseEntity<>(new EntityResponse(e.getMessage(),HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST) ;
       }
    }

    @GetMapping("/getStreamById/{streamId}")
    public ResponseEntity<?> getStreamById(@PathVariable Long streamId){
        try{
            return new ResponseEntity<>(iStreamMasterService.getStreamById(streamId),HttpStatus.FOUND) ;
        }catch (IdNotFound e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST) ;
        }
    }

    @GetMapping("/getAllStreams")
    public ResponseEntity<?> getAllStream(){
        return new ResponseEntity<>(iStreamMasterService.getAllStream(),HttpStatus.OK) ;
    }

    @DeleteMapping("/softDeleteStream/{streamId}")
    public ResponseEntity<?> softDeleteStreamById(@PathVariable Long streamId){
        try{
            return new ResponseEntity<>(iStreamMasterService.softDeleteStreamById(streamId),HttpStatus.OK) ;
        }catch (IdNotFound e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST) ;
        }
    }

    @PutMapping("/changeStatus/{streamId}")
    public ResponseEntity<?> changeStatus(@PathVariable Long streamId){
        try{
            return new ResponseEntity<>(iStreamMasterService.changeStatus(streamId),HttpStatus.OK) ;
        }catch (IdNotFound e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST) ;
        }
    }
}
