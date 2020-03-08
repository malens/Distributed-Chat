package edu.malens.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.malens.server.Message;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;

public class ClientUDPReceive implements Runnable {
    private DatagramSocket clientSocket;

    public ClientUDPReceive(DatagramSocket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {

        try {
            while (true) {
                byte[] buf = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                clientSocket.receive(packet);
                System.out.println(new String(packet.getData(), 0, packet.getLength()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
