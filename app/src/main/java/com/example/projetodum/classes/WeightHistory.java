package com.example.projetodum.classes;

public class WeightHistory {

    public WeightHistory(String weight, String timestamp) {
        this.weight = weight;
        this.timestamp = timestamp;
    }

    public WeightHistory() {
        //Default constructor

    }
    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    String weight;
    String timestamp;


}
