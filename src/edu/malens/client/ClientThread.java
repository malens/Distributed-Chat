package edu.malens.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.malens.server.Message;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;

public class ClientThread implements Runnable {

    private Socket clientSocket;
    private DatagramSocket udpSocket;
    private DatagramSocket multicastSocket;
    private Integer myAssignedId = null;
    private String myName;
    private DataOutputStream outputStream;
    private DataInputStream inputStream;

    private Gson gson = new GsonBuilder().serializeNulls().setLenient().create();

    public ClientThread(int serverPort, InetAddress serverAddress, String myName) throws IOException {
        this.clientSocket = new Socket(serverAddress, serverPort);
        this.udpSocket = new DatagramSocket();
        this.udpSocket.connect(clientSocket.getInetAddress(), clientSocket.getPort());
        this.multicastSocket = new DatagramSocket();
        this.outputStream = new DataOutputStream(clientSocket.getOutputStream());
        this.inputStream = new DataInputStream(clientSocket.getInputStream());
        this.myName = myName;
    }

    @Override
    public void run() {

        try {


            Message toSend = new Message(myAssignedId, "test", myName);
            String json = gson.toJson(toSend);
            outputStream.writeUTF(json);
            String received = inputStream.readUTF();
            Message recMsg = gson.fromJson(received, Message.class);

            this.myAssignedId = recMsg.fromId;
            Thread t = new Thread(new ClientReceive(clientSocket));
            Thread t2 = new Thread(new ClientUDPReceive(new DatagramSocket(clientSocket.getLocalPort(), clientSocket.getLocalAddress())));
            Thread t3 = new Thread(new ClientMulticastReceive());
            t.start();
            t2.start();
            t3.start();

            while (true) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String input = reader.readLine();
                if (input.startsWith("/U")) {
                    sendUDPMessage();
                } else if (input.startsWith("/M")) {
                    sendMulticastMessage();
                } else {
                    sendTCPMessage(input);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void sendTCPMessage(String input) throws IOException {
        Message toSend = new Message(myAssignedId, input, myName);
        String json = gson.toJson(toSend);
        outputStream.writeUTF(json);
    }

    private void sendUDPMessage() {
        try (BufferedReader reader = new BufferedReader(new FileReader(getClass().getResource("ascii.txt").getPath()))) {
            byte[] bytes;
            String line;
            while ((line = reader.readLine()) != null) {
                bytes = line.getBytes();
                this.udpSocket.send(new DatagramPacket(bytes, bytes.length));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendMulticastMessage(){
        try (BufferedReader reader = new BufferedReader(new FileReader(getClass().getResource("ascii.txt").getPath()))) {
            byte[] bytes;
            String line;
            while ((line = reader.readLine()) != null) {
                bytes = line.getBytes();
                this.multicastSocket.send(new DatagramPacket(bytes, bytes.length, InetAddress.getByName("234.0.0.0"), 9010));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
