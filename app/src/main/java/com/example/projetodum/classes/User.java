package com.example.projetodum.classes;

import java.util.Date;

public class User {

    private String email;
    private String username;
    private String gender;
    private String bDate;
    private double weight;
    private double height;
    private double IMC;
    private double BW;


    public User(){
        //Default constructor
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() { return username;}

    public void setUsername(String username){ this.username = username; }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getbDate() {
        return bDate;
    }

    public void setbDate(String bDate) {
        this.bDate = bDate;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getIMC() {
        return IMC;
    }

    public void setIMC(double IMC) {
        this.IMC = IMC;
    }

    public double getBW() {
        return BW;
    }

    public void setBW(double BW) {this.BW = BW;  }

    public User(String email, String username, String gender, String bDate, double height, double weight, double IMC, double BW){
        this.email = email;
        this.username = username;
        this.gender = gender;
        this.bDate = bDate;
        this.height = height;
        this.weight = weight;
        this.IMC = IMC;
        this.BW = BW;

    }


}
