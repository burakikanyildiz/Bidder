package com.company;

import com.company.model.Product;
import com.company.model.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Database {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:~/test";

    //  Database credentials
    static final String USER = "sa";
    static final String PASS = "";

    Connection conn = null;
    Statement stmt = null;

    public Database(){

        try{

            Class.forName(JDBC_DRIVER);
            System.out.println("Connection to database");

            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            stmt = conn.createStatement();
            String sql4 =  "DROP TABLE BID";
            stmt.executeUpdate(sql4);
            stmt.close();

            stmt = conn.createStatement();
            String sql3 =  "DROP TABLE PRODUCT";
            stmt.executeUpdate(sql3);
            stmt.close();

            stmt = conn.createStatement();
            String sql2 =  "DROP TABLE USER";
            stmt.executeUpdate(sql2);
            stmt.close();


            //STEP 3: Execute a query
            System.out.println("Creating USER table in given database...");
            stmt = conn.createStatement();
            String sql =  "CREATE TABLE IF NOT EXISTS USER " +
                    "(id INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                    " username VARCHAR(255) not NULL UNIQUE, " +
                    " password VARCHAR(255), " +
                    " email VARCHAR(255) not NULL UNIQUE)";
            stmt.executeUpdate(sql);
            stmt.close();
            System.out.println("Created USER table in given database...");

            System.out.println("Creating PRODUCT table in given database...");
            stmt = conn.createStatement();
            sql =  "CREATE TABLE IF NOT EXISTS PRODUCT " +
                    "(id INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                    " name VARCHAR(255), " +
                    " endDate VARCHAR(255)," +
                    " userName VARCHAR(255), " +
                    " highestBid INTEGER," +
                    " FOREIGN KEY (userName) REFERENCES USER(username))";
            stmt.executeUpdate(sql);
            System.out.println("Created PRODUCT table in given database...");
            stmt.close();

            System.out.println("Creating BID table in given database...");
            stmt = conn.createStatement();
            sql =  "CREATE TABLE IF NOT EXISTS BID " +
                    "(id INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                    " userName VARCHAR(255), " +
                    " productName VARCHAR(255)," +
                    " price INTEGER, " +
                    " FOREIGN KEY (userName) REFERENCES USER(username)," +
                    " FOREIGN KEY (productName) REFERENCES PRODUCT(name))";
            stmt.executeUpdate(sql);
            System.out.println("Created BID table in given database...");
            stmt.close();
/*
            stmt = conn.createStatement();
            sql =  "INSERT INTO PRODUCT" +
                    "(name, " +
                    " endDate, " +
                    " highestBid) "+" VALUES(" +
                    "'butterfly knife', '2019-09-09 12:11:09', 100)";
            stmt.executeUpdate(sql);
*/


            // STEP 4: Clean-up environment
            //stmt.close();
            //conn.close();


        } catch(SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try{
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {
            } // nothing we can do
            /*try {
                if(conn!=null)
                    conn.close();
            } catch(SQLException se){
                se.printStackTrace();
            } */
            //end finally try
        } //end try

    }

    public List<User> selectUsers() {
        ResultSet users = null;
        ResultSet bids = null;
        List<User> ll = null;
        try {

            stmt = conn.createStatement();
            String sql = "SELECT * FROM USER ";
            TCPServer.cs.P();
            users = stmt.executeQuery(sql);
            TCPServer.cs.V();
            //stmt.close();
            ll = new LinkedList<User>();

            while(users.next()){
                User user = new User(users.getInt("id"),users.getString("username"),users.getString("password"),users.getString("email"),null); // TODO: bids have to not be null!
                ll.add(user);
            }
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) stmt.close();
            }catch (SQLException se2) {

            }


        }

        for (User u:ll) {
            System.out.println(u.getUsername());

        }

        return ll;
    }

    public boolean registerUser(String username, String password, String email){
        ResultSet users = null;
        try{
            stmt = conn.createStatement();
            String sql = "SELECT * FROM USER WHERE username='"+username+"' ";
            TCPServer.cs.P();
            users = stmt.executeQuery(sql);

            TCPServer.cs.V();
            if(users.next()) {
                stmt.close();
                return false;
            }

            stmt = conn.createStatement();
            sql = "INSERT INTO USER(username, password, email) VALUES('"+username+"' , '"+password+"' , '"+email+"')";
            TCPServer.cs.P();
            stmt.executeUpdate(sql);
            TCPServer.cs.V();
            stmt.close();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try{
                if (stmt!=null) stmt.close();
            }catch (SQLException se2){

            }
        }
        return true;

    }

    public JSONArray selectItems() throws JSONException {
        ResultSet items = null;
        JSONArray ll = null;
        try {

            stmt = conn.createStatement();
            String sql = "SELECT * FROM PRODUCT ";
            TCPServer.cs.P();
            items = stmt.executeQuery(sql);
            TCPServer.cs.V();
            //stmt.close();
            ll = new JSONArray();

            while(items.next()){
                System.out.println(items);
                JSONObject product = new JSONObject();
                product.put("id", items.getInt("id"));
                product.put("name", items.getString("name"));
                product.put("endDate", items.getString("endDate"));
                product.put("userName", items.getString("userName"));
                product.put("highestBid", items.getInt("highestBid"));
                product.put("history", "null");
                ll.put(product);
            }
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) stmt.close();
            }catch (SQLException se2) {

            }


        }

        System.out.println(ll.length());

        return ll;
    }

    public boolean addAuction(String username, String itemName, int minBid, String date) {

        try{
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d = formatter.parse(date);

            stmt = conn.createStatement();
            String sql = "INSERT INTO Product(name, endDate, username, highestBid) VALUES('"+itemName+"' , '"+date+"' , '"+username+"'"+", "+minBid+")";
            TCPServer.cs.P();
            stmt.executeUpdate(sql);
            TCPServer.cs.V();
            stmt.close();
        }catch (SQLException e){
            e.printStackTrace();
        } catch (ParseException e) {
            return false;
        } finally {
            try{
                if (stmt!=null) stmt.close();
            }catch (SQLException se2){

            }
        }
        return true;

    }

    public boolean addBid(int itemId, String username, int bid) {
        ResultSet items = null;
        try{

            stmt = conn.createStatement();
            String sql = "SELECT * FROM Product WHERE id="+itemId;
            TCPServer.cs.P();
            items = stmt.executeQuery(sql);

            TCPServer.cs.V();
            while(items.next()) {
                if(bid <= items.getInt("highestBid"))
                    return false;
            }


            stmt = conn.createStatement();
            sql = " UPDATE Product SET userName= '"+username+"', highestBid="+bid+" WHERE id="+itemId;
            TCPServer.cs.P();
            stmt.executeUpdate(sql);
            TCPServer.cs.V();
            stmt.close();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try{
                if (stmt!=null) stmt.close();
            }catch (SQLException se2){

            }
        }
        return true;
    }
}
