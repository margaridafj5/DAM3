package com.example.projetodum.classes;

import android.content.Intent;

import com.example.projetodum.BackOffice;
import com.example.projetodum.FirstPage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User implements Serializable {

    private String email;
    private String fName;
    private String lName;
    private String gender;
    private String bDate;
    private double IMC;
    private double BW;
    private double weight;
    private double height;
    private List<String> followingList;

    public List<String> getFollowingList() {
        return followingList;
    }

    public void setFollowingList(List<String> followingList) {
        this.followingList = followingList;
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

    public void setBW(double BW) {
        this.BW = BW;
    }

    public User(){
        //Default constructor
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

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

    public User(String email, String fName, String lName, String gender, String bDate, double height, double weight, double IMC, double BW, List<String> followingList){
        this.email = email;
        this.fName = fName;
        this.lName = lName;
        this.gender = gender;
        this.bDate = bDate;
        this.height = height;
        this.weight = weight;
        this.IMC = IMC;
        this.BW = BW;
        this.followingList = followingList;


    }


}
