package com.company.model;

import java.util.ArrayList;

public class User {

    private int id;

    private String username;

    private String password;

    private String email;

    private ArrayList<Bid> bids;

    public User(int id, String username, String password, String email, ArrayList<Bid> bids) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.bids = bids;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<Bid> getBids() {
        return bids;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBids(ArrayList<Bid> bids) {
        this.bids = bids;
    }

    public void addBid(Bid bid) {
        this.bids.add(bid);
    }

}
