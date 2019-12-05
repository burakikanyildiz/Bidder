package com.company;

import com.company.helper.CountingSemaphore;

import java.io.*;
import java.net.*;

public class TCPServer {


    public static final CountingSemaphore cs = new CountingSemaphore(1);
    public static Socket serverSocket;
    public static int PORT;

    public static final Database db=new Database();

    public static void main(String [] args) throws IOException {

        PORT=80;

        ServerSocket serverSocket = new ServerSocket(PORT);

        while(true) {
            Socket connectionSocket = serverSocket.accept();
            System.out.println("connectionSocket kuruldu");
            new Handler(connectionSocket);

/*
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());


            while (true) {

                clientMessage = inFromClient.readLine();

                capitalizedMessage = clientMessage.toUpperCase() + "\n";

                outToClient.writeBytes(capitalizedMessage);

                if(clientMessage.equals("aq"))
                    break;
            }*/
        }

    }



}
