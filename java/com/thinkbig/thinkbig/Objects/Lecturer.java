package com.thinkbig.thinkbig.Objects;

public class Lecturer {
    private String name;
    private String email;
    private String phoneNum;
    private String description;
    private String lectImage;

    public Lecturer(String name, String email, String phoneNum, String description, String lectImage) {
        this.name = name;
        this.email = email;
        this.phoneNum = phoneNum;
        this.description = description;
        this.lectImage = lectImage;
    }

    public Lecturer(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLectImage() {
        return lectImage;
    }

    public void setLectImage(String lectImage) {
        this.lectImage = lectImage;
    }
}
