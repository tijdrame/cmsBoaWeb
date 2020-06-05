package com.boa.web.response;

import com.boa.web.response.prepareChangeCardOption.PrepareChangeCardOption;

/**
 * PrepareChangeCardOptionResponse
 */
public class PrepareChangeCardOptionResponse extends GenericResponse {

    private PrepareChangeCardOption prepareChangeCardOption;

    public PrepareChangeCardOption getPrepareChangeCardOption() {
        return this.prepareChangeCardOption;
    }

    public void setPrepareChangeCardOption(PrepareChangeCardOption prepareChangeCardOption) {
        this.prepareChangeCardOption = prepareChangeCardOption;
    }

}