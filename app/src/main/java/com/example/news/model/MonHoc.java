package com.example.news.model;

public class MonHoc {
    private String tenmonhoc;
    private String cahoc;

    public String getTenmonhoc() {
        return tenmonhoc;
    }

    public void setTenmonhoc(String tenmonhoc) {
        this.tenmonhoc = tenmonhoc;
    }

    public String getCahoc() {
        return cahoc;
    }

    public void setCahoc(String cahoc) {
        this.cahoc = cahoc;
    }

    public MonHoc(String tenmonhoc, String cahoc) {
        this.tenmonhoc = tenmonhoc;
        this.cahoc = cahoc;
    }

    public MonHoc() {
    }
}
