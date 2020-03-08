package edu.malens.server;

import java.io.IOException;
import java.net.*;

public class UdpServerThread extends ServerThread{

    DatagramSocket serverSocket;


    public UdpServerThread(int serverPort) {
        this.serverPort = serverPort;

    }

    public void broadcast(DatagramPacket msg) {
        System.out.println("broadcasting");
        this.userBase.broadcastUDP(msg);
    }

    @Override
    public void run() {
        try {
            this.serverSocket = new DatagramSocket(serverPort);
            while(true){
                byte[] buf = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                serverSocket.receive(packet);
                System.out.println("received packet:" + packet.getAddress() + ":" + packet.getPort());
                broadcast(packet);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
