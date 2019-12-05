package com.company.model;

public class Bid {

    private int id;

    private String userName;

    private String itemName;

    private int price;

    public Bid(int id, String userName, String itemName, int price) {
        this.id = id;
        this.userName = userName;
        this.itemName = itemName;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getUser() {
        return userName;
    }

    public int getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser(String userName) {
        this.userName = userName;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getItem() {
        return itemName;
    }

    public void setItem(String itemName) {
        this.itemName = itemName;
    }
}
