package com.example.provence.model;

public class Vacancy {
    private String name;
    private String phone;
    private String resume;

    public Vacancy(String name, String phone, String resume) {
        this.name = name;
        this.phone = phone;
        this.resume = resume;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }
}
