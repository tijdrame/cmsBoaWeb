package com.boa.web.response.prepareChangeCardOption;

import java.util.ArrayList;
import java.util.List;

/**
 * PrepareChangeCardOption
 */
public class PrepareChangeCardOption {

    private List<Information> infos = new ArrayList<>();

    private HiddenInput hiddenInput = new HiddenInput();

    public List<Information> getInfos() {
        return this.infos;
    }
    public void setInfos(List<Information> infos) {
        this.infos=infos;
    }

    public HiddenInput getHiddenInput() {
        return this.hiddenInput;
    }

    public void setHiddenInput(HiddenInput hiddenInput) {
        this.hiddenInput = hiddenInput;
    }

}