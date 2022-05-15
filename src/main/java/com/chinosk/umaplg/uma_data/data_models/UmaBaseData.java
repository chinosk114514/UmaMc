package com.chinosk.umaplg.uma_data.data_models;

import java.util.List;


public class UmaBaseData {
    private String uuid;
    private String masteruuid;
    private String mastername;
    private int speed;
    private int speed_now;
    private int max_speed;
    private int endurance;
    private int endurance_now;
    private int max_endurance;
    private int power;
    private int power_now;
    private int max_power;
    private int perse;
    private int perse_now;
    private int max_perse;
    private int intelli;
    private int intelli_now;
    private int max_intelli;
    private int mood;
    private int love;
    private int feed;
    private List<UmaSkills> skills;


    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getUuid() {
        return uuid;
    }

    public void setMasteruuid(String playeruuid) {
        this.masteruuid = playeruuid;
    }
    public String getMasteruuid() {
        return masteruuid;
    }

    public void setMastername(String playername) {
        this.mastername = playername;
    }
    public String getMastername() {
        return mastername;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public int getSpeed() {
        return speed;
    }

    public void setSpeed_now(int speed_now) {
        this.speed_now = speed_now;
    }
    public int getSpeed_now() {
        return speed_now;
    }

    public void setMax_speed(int max_speed) {
        this.max_speed = max_speed;
    }
    public int getMax_speed() {
        return max_speed;
    }

    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }
    public int getEndurance() {
        return endurance;
    }

    public void setEndurance_now(int endurance_now) {
        this.endurance_now = endurance_now;
    }
    public int getEndurance_now() {
        return endurance_now;
    }

    public void setMax_endurance(int max_endurance) {
        this.max_endurance = max_endurance;
    }
    public int getMax_endurance() {
        return max_endurance;
    }

    public void setPower(int power) {
        this.power = power;
    }
    public int getPower() {
        return power;
    }

    public void setPower_now(int power_now) {
        this.power_now = power_now;
    }
    public int getPower_now() {
        return power_now;
    }

    public void setMax_power(int max_power) {
        this.max_power = max_power;
    }
    public int getMax_power() {
        return max_power;
    }

    public void setPerse(int perse) {
        this.perse = perse;
    }
    public int getPerse() {
        return perse;
    }

    public void setPerse_now(int perse_now) {
        this.perse_now = perse_now;
    }
    public int getPerse_now() {
        return perse_now;
    }

    public void setMax_perse(int max_perse) {
        this.max_perse = max_perse;
    }
    public int getMax_perse() {
        return max_perse;
    }

    public void setIntelli(int intelli) {
        this.intelli = intelli;
    }
    public int getIntelli() {
        return intelli;
    }

    public void setIntelli_now(int intelli_now) {
        this.intelli_now = intelli_now;
    }
    public int getIntelli_now() {
        return intelli_now;
    }

    public void setMax_intelli(int max_intelli) {
        this.max_intelli = max_intelli;
    }
    public int getMax_intelli() {
        return max_intelli;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }
    public int getMood() {
        return mood;
    }

    public void setLove(int love) {
        this.love = love;
    }
    public int getLove() {
        return love;
    }

    public void setFeed(int feed) {
        this.feed = feed;
    }
    public int getFeed() {
        return feed;
    }

    public void setSkills(List<UmaSkills> skills) {
        this.skills = skills;
    }
    public List<UmaSkills> getSkills() {
        return skills;
    }

}