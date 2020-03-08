package edu.malens.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.xml.crypto.Data;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;

public class UdpUser {

    private int id;
    private String name;
    private int port;
    private InetAddress addr;
    private DatagramSocket userSocket;
    private UdpServerThread server;

    public UdpUser(int id, String name, DatagramSocket userSocket, UdpServerThread server, InetAddress addr, int port) {
        this.id = id;
        this.name = name;
        this.userSocket = userSocket;
        this.server = server;
        this.userSocket.connect(addr, port);
        this.addr = addr;
        this.port = port;
    }

    public void sendMessage(DatagramPacket packet) {
        System.out.println("sending UDP");
        System.out.println(userSocket.getInetAddress() + ":" + userSocket.getPort());
        System.out.println(this.userSocket.getLocalAddress() + ":" + this.userSocket.getLocalPort());
        System.out.println(packet.getAddress() + ":" + packet.getPort());
        if (packet.getAddress() != this.userSocket.getLocalAddress() && packet.getPort() != this.userSocket.getLocalPort() - 1){
            try {
                DatagramPacket p = new DatagramPacket(packet.getData(), packet.getLength(), userSocket.getInetAddress(), userSocket.getPort());
                this.userSocket.send(p);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
