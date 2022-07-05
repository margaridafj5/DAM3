package com.example.projetodum.classes;

import java.time.LocalDateTime;
import java.util.Date;

public class Schedule {

    public String getEid() {
        return Eid;
    }

    public void setEid(String eid) {
        Eid = eid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    private String Eid;
    private int status;
    private String schedule;

    public Schedule(){
        //Default constructor
    }

    public Schedule(String Eid, int status, String schedule) {
        this.Eid = Eid;
        this.status = status;
        this.schedule = schedule;
    }
}
