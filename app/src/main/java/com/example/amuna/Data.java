package com.example.amuna;

public class Data {
    String member_id;
    String member_name;
    String member_Gender;
    String member_rating;
    String member_Nickname;
    String member_Foreigner;
    String member_Age;
    String member_PhoneNum;
    String member_Job;
    String member_Univ_Name;
    String member_Intro;
    String member_PhotoURL;
    String member_LimitBudget;

    public String getMember_Gender() {
        return member_Gender;
    }

    public void setMember_Gender(String member_Gender) {
        this.member_Gender = member_Gender;
    }

    public String getMember_Nickname() {
        return member_Nickname;
    }

    public String getMember_PhotoURL() {
        return member_PhotoURL;
    }

    public void setMember_PhotoURL(String member_PhotoURL) {
        this.member_PhotoURL = member_PhotoURL;
    }

    public void setMember_LimitBudget(String member_LimitBudget) {
        this.member_LimitBudget = member_LimitBudget;
    }

    public String getMember_LimitBudget() {
        return member_LimitBudget;
    }

    public void setMember_Nickname(String member_Nickname) {
        this.member_Nickname = member_Nickname;
    }

    public String getMember_Foreigner() {
        return member_Foreigner;
    }

    public void setMember_Foreigner(String member_Foreigner) {
        this.member_Foreigner = member_Foreigner;
    }

    public String getMember_Age() {
        return member_Age;
    }

    public void setMember_Age(String member_Age) {
        this.member_Age = member_Age;
    }

    public String getMember_PhoneNum() {
        return member_PhoneNum;
    }

    public void setMember_PhoneNum(String member_PhoneNum) {
        this.member_PhoneNum = member_PhoneNum;
    }

    public String getMember_Job() {
        return member_Job;
    }

    public void setMember_Job(String member_Job) {
        this.member_Job = member_Job;
    }

    public String getMember_Univ_Name() {
        return member_Univ_Name;
    }

    public void setMember_Univ_Name(String member_Univ_Name) {
        this.member_Univ_Name = member_Univ_Name;
    }

    public String getMember_Intro() {
        return member_Intro;
    }

    public void setMember_Intro(String member_Intro) {
        this.member_Intro = member_Intro;
    }

    public String getMember_rating() {
        return member_rating;
    }

    public void setMember_rating(String member_rating) {
        this.member_rating = member_rating;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public Data(String member_id, String member_name, String member_Gender,String member_rating,String member_Nickname,String member_Foreigner,
                String member_Age, String member_PhoneNum, String member_Job, String member_LimitBudget, String member_PhotoURL, String member_Univ_Name,
                String member_Intro){
        this.member_id = member_id;
        this.member_name = member_name;
        this.member_Gender=member_Gender;
        this.member_rating = member_rating;
        this.member_Nickname=member_Nickname;
        this.member_Foreigner=member_Foreigner;
        this.member_Age=member_Age;
        this.member_PhoneNum=member_PhoneNum;
        this.member_Job=member_Job;
        this.member_PhotoURL=member_PhotoURL;
        this.member_Univ_Name=member_Univ_Name;
        this.member_Intro=member_Intro;
        this.member_LimitBudget=member_LimitBudget;
    }
}