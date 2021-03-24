package com.example.easyschool.data;

public class  Activities {
    private String title ,level,time,details,teacher_email,teacher;

    public Activities() {
    }

    public Activities(String title, String level, String time, String details, String teacher_email, String teacher) {
        this.title = title;
        this.level = level;
        this.time = time;
        this.details = details;
        this.teacher_email = teacher_email;
        this.teacher = teacher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getTeacher_email() {
        return teacher_email;
    }

    public void setTeacher_email(String teacher_email) {
        this.teacher_email = teacher_email;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
