package com.example.news.model;

public class HocKy {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HocKy(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public HocKy() {
    }
}
