package com.chinosk.umaplg.uma_data.data_models;


public class UmaSkillEffect {
    private String condition;
    private String effect_type;
    private int effect_value;

    public void setCondition(String condition) {
        this.condition = condition;
    }
    public String getCondition() {
        return condition;
    }

    public void setEffect_type(String effect_type) {
        this.effect_type = effect_type;
    }
    public String getEffect_type() {
        return effect_type;
    }

    public void setEffect_value(int effect_value) {
        this.effect_value = effect_value;
    }
    public int getEffect_value() {
        return effect_value;
    }

}
