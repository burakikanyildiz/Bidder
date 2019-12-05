package com.company;

import com.company.model.Product;
import com.company.model.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import sun.rmi.transport.tcp.TCPEndpoint;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class Handler extends Thread{

    Socket connectionSocket;

    BufferedReader inFromClient;

    BufferedWriter outToClient;

    public Handler(Socket connectionSocket){
        this.connectionSocket = connectionSocket;

        try {
            inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            outToClient = new BufferedWriter(new OutputStreamWriter(connectionSocket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.start();
    }

    public void run(){
        String request = "";

        try {
            request = inFromClient.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JSONObject json = new JSONObject(request);
            requestHandler(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void requestHandler(JSONObject json) throws JSONException {

        if(json.getString("function").equals("signIn")){
            signIn(json);
        }else if(json.getString("function").equals("signUp")){
            signUp(json);
        }else if(json.getString("function").equals("getItems")){
            getItems(json);
        }else if(json.getString("function").equals("addAuction")){
            addAuction(json);
        }else if(json.getString("function").equals("addBid")){
            addBid(json);
        }else if(json.getString("function").equals("getBoughtItems")){
            getItems(json);
        }


    }

    private void addBid(JSONObject json) throws JSONException {
        JSONObject response = new JSONObject();
        System.out.println(json);
        String username = json.getString("username");
        int itemId = json.getInt("itemId");
        String bidString = json.getString("bid");
        int bid = -1;
        try {
            bid = Integer.parseInt(bidString);
        }catch (NumberFormatException e){
            response.put("success",0);
            sendResponse(response);
            return;
        }
        boolean isAdded = TCPServer.db.addBid(itemId, username, bid);
        if(isAdded) {
            response.put("success", 1);
        }else{
            response.put("success", 0);
        }
        sendResponse(response);

    }

    private void addAuction(JSONObject json) throws JSONException {
        JSONObject response = new JSONObject();
        System.out.println(json);
        String username = json.getString("username");
        String itemName = json.getString("name");
        String minimumBid = json.getString("minimumBid");
        String date = json.getString("date");
        int minBid = -1;
        try {
            minBid = Integer.parseInt(minimumBid);
        }catch (NumberFormatException e){
            response.put("success",0);
            sendResponse(response);
            return;
        }
        boolean isAdded = TCPServer.db.addAuction(username, itemName, minBid, date);
        if(isAdded) {
            response.put("success", 1);
        }else{
            response.put("success", 0);
        }
        sendResponse(response);
    }

    private void getItems(JSONObject json) throws JSONException {

        JSONObject response = new JSONObject();
        System.out.println(" getItems'a geldim ");
        JSONArray items = TCPServer.db.selectItems();
        response.put("success",1);
        response.put("items", items);
        System.out.println(response);
        sendResponse(response);
    }

    private void signUp(JSONObject json) throws JSONException {
        String username = json.getString("username");
        String password = json.getString("password");
        String email = json.getString("email");

        JSONObject response = new JSONObject();
        if(!TCPServer.db.registerUser(username, password, email)){
            response.put("success", "0");
            System.out.println(response);
        }else {
            response.put("success", "1");
        }
        response.put("username", username);
        sendResponse(response);

    }

    private void signIn(JSONObject json) throws JSONException {

        String username = json.getString("username");
        String password = json.getString("password");

        System.out.println(username);
        System.out.println(password);

        List<User> users= TCPServer.db.selectUsers();



        JSONObject response = new JSONObject();
        response.put("success", "0");
        response.put("username", username);

        for (User u:users) {
            if(u.getUsername().equals(username) && u.getPassword().equals(password)){
                System.out.println(u.getEmail());
                response.put("email",u.getEmail());
                response.put("success", "1");
                break;
            }

        }

        sendResponse(response);



    }

    private void sendResponse(JSONObject response) {

            try {
                outToClient.write(response.toString());
                outToClient.newLine();
                outToClient.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

    }


}
