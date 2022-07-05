package com.example.projetodum.classes;

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

    public Date getSchedule() {
        return schedule;
    }

    public void setSchedule(Date schedule) {
        this.schedule = schedule;
    }

    private String Eid;
    private int status;
    private Date schedule;

    public Schedule(){
        //Default constructor
    }

    public Schedule(String Eid, int status, Date schedule) {
        this.Eid = Eid;
        this.status = status;
        this.schedule = schedule;
    }
}
