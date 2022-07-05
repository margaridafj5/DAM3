package com.example.projetodum.classes;

public class Exercises {

    private String name;
    private String description;
    private int calories;
    private int nPeople;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getnPeople() {
        return nPeople;
    }

    public void setnPeople(int nPeople) { this.nPeople = nPeople;  }


    public Exercises(){
        //Default Constructor
    }

    public Exercises(String name, String description, int calories, int nPeople){
        this.name = name;
        this.description = description;
        this.calories = calories;
        this.nPeople = nPeople;


    }

}
