package com.chinosk.umaplg.uma_data.data_models;


import java.util.List;

public class UmaSkills {
    private String skillIcon;
    private int rare;
    private int score;
    private String description;
    private String id;
    private List<UmaSkillEffect> effects;

    public void setDescription(String desc) {
        this.description = desc;
    }
    public String getDescription() {
        return description;
    }

    public void setSkillIcon(String skillIcon) {
        this.skillIcon = skillIcon;
    }
    public String getSkillIcon() {
        return skillIcon;
    }

    public void setRare(int rare) {
        this.rare = rare;
    }
    public int getRare() {
        return rare;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public int getScore() {
        return score;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setEffects(List<UmaSkillEffect> effects) {
        this.effects = effects;
    }
    public List<UmaSkillEffect> getEffects() {
        return effects;
    }

}
