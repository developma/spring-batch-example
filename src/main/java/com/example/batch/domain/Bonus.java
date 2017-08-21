package com.example.batch.domain;

public class Bonus {

    private Integer empId;

    private Integer payments;

    public Bonus() {
    }

    public Bonus(Integer empId, Integer payments) {
        this.empId = empId;
        this.payments = payments;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public Integer getPayments() {
        return payments;
    }

    public void setPayments(Integer payments) {
        this.payments = payments;
    }
}
