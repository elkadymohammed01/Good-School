package com.example.easyschool.data;

public class Question {
    private String email,name,ques,type,subject,level,file;
    private String ch_1,ch_2,ch_3,ch_4,correct_ans;
    public Question() {
    }

    public Question(String email, String name, String ques, String type, String subject, String level, String file, String ch_1, String ch_2, String ch_3, String ch_4, String correct_ans) {
        this.email = email;
        this.name = name;
        this.ques = ques;
        this.type = type;
        this.subject = subject;
        this.level = level;
        this.file = file;
        this.ch_1 = ch_1;
        this.ch_2 = ch_2;
        this.ch_3 = ch_3;
        this.ch_4 = ch_4;
        this.correct_ans = correct_ans;
    }

    public String getCh_1() {
        return ch_1;
    }

    public void setCh_1(String ch_1) {
        this.ch_1 = ch_1;
    }

    public String getCh_2() {
        return ch_2;
    }

    public void setCh_2(String ch_2) {
        this.ch_2 = ch_2;
    }

    public String getCh_3() {
        return ch_3;
    }

    public void setCh_3(String ch_3) {
        this.ch_3 = ch_3;
    }

    public String getCh_4() {
        return ch_4;
    }

    public void setCh_4(String ch_4) {
        this.ch_4 = ch_4;
    }

    public String getCorrect_ans() {
        return correct_ans;
    }

    public void setCorrect_ans(String correct_ans) {
        this.correct_ans = correct_ans;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQues() {
        return ques;
    }

    public void setQues(String ques) {
        this.ques = ques;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
