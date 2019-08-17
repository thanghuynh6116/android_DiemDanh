package com.example.news.model;

public class MonHoc {
    private int id;
    private String tenmonhoc;
    private String cahoc;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public MonHoc() {
    }

    public MonHoc(int id, String tenmonhoc, String cahoc) {
        this.id = id;
        this.tenmonhoc = tenmonhoc;
        this.cahoc = cahoc;
    }
}
