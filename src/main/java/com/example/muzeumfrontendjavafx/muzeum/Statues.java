package com.example.muzeumfrontendjavafx.muzeum;

public class Statues {

    private int id;
    private String person;
    private int height;
    private int price;

    public Statues(int id, String person, int height, int price) {
        this.id = id;
        this.person = person;
        this.height = height;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
