package edu.malens.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class ServerThread implements Runnable {
    protected UserBase userBase;
    protected int serverPort;

    Gson gson = new GsonBuilder().serializeNulls().setLenient().create();

    public ServerThread registerUserBase(UserBase userBase){
        this.userBase = userBase;
        return this;
    }

}
