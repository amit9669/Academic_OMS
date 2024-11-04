package com.academic.service;
import com.academic.model.request.OptionsRequest;

import java.util.List;

public interface IOptionService {

    public Object saveOrUpdate(List<OptionsRequest> OptionsRequest);
}
