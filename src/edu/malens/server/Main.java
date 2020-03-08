package edu.malens.server;

public class Main {

    private static int serverPort = 9009;


    public static void main(String[] args) {

        try {
            UdpServerThread udpThread = new UdpServerThread(serverPort);
            UserBase userBase = new UserBase();
            TcpServerThread tcpThread = new TcpServerThread(serverPort);
            Thread t = new Thread(tcpThread);
            Thread t2 = new Thread(udpThread);
            userBase.registerTcpServer(tcpThread);
            userBase.registerUdpServer(udpThread);

            t.start();
            t2.start();

        } catch (Exception e){
            e.printStackTrace();
        }


    }
}
