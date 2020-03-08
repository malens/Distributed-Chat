package edu.malens.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class User implements Runnable {

    private int id;
    private String name;
    private Socket userSocket;
    private TcpServerThread server;

    public User(int id, String name, Socket userSocket, TcpServerThread server) {
        this.id = id;
        this.name = name;
        this.userSocket = userSocket;
        this.server = server;
    }

    public void sendMessage(Message message, Boolean ignoreIdCheck) {
        if (message.fromId != id || ignoreIdCheck) {
            try {
                Gson gson = new GsonBuilder().serializeNulls().setLenient().create();
                DataOutputStream outputStream = new DataOutputStream(userSocket.getOutputStream());
                outputStream.writeUTF(gson.toJson(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(Message message) {
        sendMessage(message, false);
    }


    @Override
    public void run() {
        Gson gson = new GsonBuilder().serializeNulls().setLenient().create();
        try {
            Message msg = new Message(id, "adaas", name);
            sendMessage(msg, true);
            System.out.println("message sent");
            DataInputStream inputStream = new DataInputStream(userSocket.getInputStream());
            while (true) {
                String msgString = inputStream.readUTF().trim();
                System.out.println(msgString);
                msg = gson.fromJson(msgString, Message.class);
                System.out.println(msg.content);
                server.broadcast(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
