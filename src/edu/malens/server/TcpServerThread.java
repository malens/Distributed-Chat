package edu.malens.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class TcpServerThread extends ServerThread {

    public TcpServerThread(int serverPort) {
        this.serverPort = serverPort;
    }

    public void broadcast(Message msg) {
        System.out.println("broadcasting");
        this.userBase.broadcast(msg);
    }

    @Override
    public void run() {



        try (ServerSocket serverSocket = new ServerSocket(serverPort)) {
            while (true) {

                Socket clientSocket = serverSocket.accept();

                DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());


                String msgString = inputStream.readUTF().trim();

                Message msg = gson.fromJson(msgString, Message.class);

                int tmpId = this.userBase.getNextId(msg);
                System.out.println("clsocket: " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
                User newUser = new User(tmpId, msg.fromName, clientSocket, this.userBase.getTcpServer());
                UdpUser newUDPUser = new UdpUser(tmpId, msg.fromName, new DatagramSocket(), this.userBase.getUdpServer(), clientSocket.getInetAddress(), clientSocket.getPort());

                this.userBase.addUser(tmpId, newUser, newUDPUser);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
