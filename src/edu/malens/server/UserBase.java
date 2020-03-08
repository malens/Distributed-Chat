package edu.malens.server;

import java.net.DatagramPacket;
import java.util.Map;
import java.util.TreeMap;

public class UserBase {

    private Map<Integer, User> usersByIds;
    private Map<Integer, UdpUser> usersUDPByIds;
    private int currentId = 1;
    private TcpServerThread tcpServer;
    private UdpServerThread udpServer;

    public UserBase() {
        this.usersByIds = new TreeMap<>();
        this.usersUDPByIds = new TreeMap<>();
    }

    public void broadcast(Message msg) {
        for (User u : usersByIds.values()) {
            u.sendMessage(msg);
        }
    }

    public void broadcastUDP(DatagramPacket msg) {
        for (UdpUser u : usersUDPByIds.values()) {
            u.sendMessage(msg);
        }
    }

    public void addUser(Integer id, User user, UdpUser udpUser) {
        this.usersByIds.put(id, user);
        this.usersUDPByIds.put(id, udpUser);
        Thread t = new Thread(user);
        t.start();
    }

    public int getNextId(Message msg) {
        int tmpId;
        if (msg.fromId == null) {
            currentId++;
            tmpId = currentId;
        } else {
            tmpId = msg.fromId;
        }
        while (usersByIds.containsKey(tmpId)) {
            tmpId++;
            currentId++;
        }
        return tmpId;
    }

    public UserBase registerTcpServer(TcpServerThread serverThread){
        this.tcpServer = serverThread;
        serverThread.userBase = this;
        return this;
    }

    public UserBase registerUdpServer(UdpServerThread serverThread){
        this.udpServer = serverThread;
        serverThread.userBase = this;
        return this;
    }

    public TcpServerThread getTcpServer(){
        return this.tcpServer;
    }
    public UdpServerThread getUdpServer(){
        return this.udpServer;
    }


}
