package com.tcl.isport.Bean;

/**
 * Created by user on 17-8-25.
 */
public class StepData {
    //步数信息
    private Integer _id;
    private String today;
    private String step;

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }
}
