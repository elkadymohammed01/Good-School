package com.example.easyschool.data;

public class Task {
    private String Teacher_email,title,details,level,subject;

    public Task() {
    }

    public Task(String teacher_email, String title, String details, String level, String subject) {
        Teacher_email = teacher_email;
        this.title = title;
        this.details = details;
        this.level = level;
        this.subject = subject;
    }

    public String getTeacher_email() {
        return Teacher_email;
    }

    public void setTeacher_email(String teacher_email) {
        Teacher_email = teacher_email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
