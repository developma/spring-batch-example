package com.example.batch.domain;

public class Grade {

    private Integer gradeCode;
    private Integer bonusMagnification;
    private Integer fixedBonus;

    public Integer getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(Integer gradeCode) {
        this.gradeCode = gradeCode;
    }

    public Integer getBonusMagnification() {
        return bonusMagnification;
    }

    public void setBonusMagnification(Integer bonusMagnification) {
        this.bonusMagnification = bonusMagnification;
    }

    public Integer getFixedBonus() {
        return fixedBonus;
    }

    public void setFixedBonus(Integer fixedBonus) {
        this.fixedBonus = fixedBonus;
    }
}
