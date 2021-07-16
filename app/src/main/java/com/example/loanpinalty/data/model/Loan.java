package com.example.loanpinalty.data.model;

import java.util.Date;

public class Loan {
    private double value;
    private Date createdAt;


    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
