package com;

import java.net.Socket;

/**
 * Created by Johnson on 2015/11/7.
 */
public class clientBean {
    private String name;
    private Socket socket;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
