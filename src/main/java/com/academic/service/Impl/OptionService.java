package com.academic.service.Impl;

import com.academic.exception.IdNotFound;
import com.academic.model.Options;
import com.academic.model.request.OptionsRequest;
import com.academic.repository.OptionsRepository;
import com.academic.repository.QuestionRepository;
import com.academic.service.IOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionService implements IOptionService {

    @Autowired
    private OptionsRepository optionsRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public Object saveOrUpdate(List<OptionsRequest> optionsRequest) {
        if (optionsRequest != null && !optionsRequest.isEmpty()) {
            for (OptionsRequest request : optionsRequest) {
                if (optionsRepository.existsById(request.getOptionId())) {
                    Options options1 = optionsRepository.findById(request.getOptionId()).get();
                    if (questionRepository.existsById(request.getQuestionId())) {
                        options1.setQuestionId(request.getQuestionId());
                    } else {
                        throw new IdNotFound("Question not exist!!");
                    }
                    options1.setOptions(request.getOptions());
                    optionsRepository.save(options1);
                } else {
                    Options options = new Options();
                    if (!questionRepository.existsById(request.getQuestionId())) {
                        throw new IdNotFound("Question not exist!!");
                    }else {
                        options.setQuestionId(request.getQuestionId());
                    }
                    options.setOptions(request.getOptions());
                    optionsRepository.save(options);
                }
            }
        }else {
            throw new IdNotFound("Data is Empty!!");
        }
        return "Thank-You!!!";
    }
}
