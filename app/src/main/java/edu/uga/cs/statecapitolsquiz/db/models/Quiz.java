package edu.uga.cs.statecapitolsquiz.db.models;

import java.util.Date;

public class Quiz {
    private long id;
    private Date date;
    private String time;
    private int result;

    public Quiz(long id, Date date, String time, int result) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.result = result;
    }
    // Getters and setters for each field


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
