package com.company.model;

import java.util.ArrayList;
import java.util.Date;

public class Product {

    private int id;

    private String name;

    private Date until;

    private String userName;

    private int highestBid;

    private ArrayList<Bid> history;

    public Product(int id, String name, Date until, String userName, int highestBid, ArrayList<Bid> history) {
        this.id = id;
        this.name = name;
        this.highestBid = highestBid;
        this.until = until;
        this.userName = userName;
        this.history = history;
    }

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

    public int getHighestBid() {
        return highestBid;
    }

    public void setBid(int bid) {
        this.highestBid = bid;
    }

    public Date getUntil() {
        return until;
    }

    public void setUntil(Date until) {
        this.until = until;
    }

    public String getUserName() {
        return userName;
    }

    public void setUser(String userName) {
        this.userName = userName;
    }

    public ArrayList<Bid> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<Bid> history) {
        this.history = history;
    }

    public void addHistory(Bid bid) {
        this.history.add(bid);
    }
}
