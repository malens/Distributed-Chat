package edu.malens.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.malens.server.Message;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.*;

public class ClientMulticastReceive implements Runnable {
    private MulticastSocket clientSocket;

    public ClientMulticastReceive() {
    }

    @Override
    public void run() {

        try {
            this.clientSocket = new MulticastSocket(9010);
            this.clientSocket.joinGroup(InetAddress.getByName("234.0.0.0"));

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
