package com.example.atik.billcontrolapp;


import java.util.Calendar;

public class Bill {
    private String mType;
    private double mPrice;
    private String mDeadline;
    private String mDescription;
    private long id;

    public Bill(){
        mType = null;
        mPrice = 0;
        mDeadline = null;
        mDescription = null;

    }
    public Bill(String type, double price, String deadline, String description){
        super();
        mType = type;
        mPrice = price;
        mDeadline = deadline;
        mDescription = description;
    }
    public String getType() {
        return mType;
    }

    public double getPrice() {
        return mPrice;
    }

    public String getDeadline() {
        return mDeadline;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setType(String type) {
        mType = type;
    }

    public void setPrice(double price) {
        mPrice = price;
    }

    public void setDeadline(String deadline) {
        mDeadline = deadline;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String toString(){
        return mType + " " + mDeadline;
    }
}