package com.example.news.model;

public class SinhVien {
    private String name;
    private String birthday;
    private String code;
    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public SinhVien() {
    }

    public SinhVien(String name, String birthday, String code) {
        this.name = name;
        this.birthday = birthday;
        this.code = code;
    }
}
