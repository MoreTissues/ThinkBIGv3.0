package com.thinkbig.thinkbig.Objects;

public class Appointment {
    private String appointID;
    private String mImgURL;
    private String title;
    private String date;
    private String time;
    private String desc;
    private String lect_name;

    public Appointment(String appointID , String title, String date, String time, String desc, String lect_name) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.desc = desc;
        this.appointID = appointID;
        this.lect_name = lect_name;
    }
    public Appointment(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAppointID() {
        return appointID;
    }

    public void setAppointID(String appointID) {
        this.appointID = appointID;
    }

    public String getmImgURL() {
        return mImgURL;
    }

    public void setmImgURL(String mImgURL) {
        this.mImgURL = mImgURL;
    }

    public String getLect_name() {
        return lect_name;
    }

    public void setLect_name(String lect_name) {
        this.lect_name = lect_name;
    }
}
