package edu.malens.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.malens.server.Message;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientReceive implements Runnable {
    private Socket clientSocket;

    public ClientReceive(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        Gson gson = new GsonBuilder().setLenient().serializeNulls().create();
        try {
            while (true){
                DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
                String received = inputStream.readUTF().trim();
                Message recMsg = gson.fromJson(received, Message.class);
                System.out.println(recMsg.fromName + "> " + recMsg.content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
