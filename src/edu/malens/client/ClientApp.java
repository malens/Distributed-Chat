package edu.malens.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;


public class ClientApp {

    private static int defaultTCPPort = 9009;

    private static InetAddress defaultAddress;


    public static void main(String[] args) {

        try {
            System.out.println("Please provide username.");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String input = reader.readLine();
            defaultAddress = InetAddress.getLocalHost();
            Thread t = new Thread(new ClientThread(defaultTCPPort, defaultAddress, input));
            t.start();
        } catch (Exception e){
            e.printStackTrace();
        }



    }

}
