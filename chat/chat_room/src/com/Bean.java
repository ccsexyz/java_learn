package com;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Johnson on 2015/11/7.
 */

public class Bean implements Serializable{
    private beanType type; // 1私聊 0上下线更新 -1下线请求 2请求发送文件 3.确定接收文件
    private HashSet<String> clients; // 存放选中的客户
    private HashSet<String> to;
    public HashMap<String, clientBean> onlines;
    private String info;
    private String timer;
    private String name;
    private String fileName;
    private int size;
    private String ip;
    private int port;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public HashSet<String> getTo() {
        return to;
    }

    public void setTo(HashSet<String> to) {
        this.to = to;
    }

    public beanType getType() {
        return type;
    }

    public void setType(beanType type) {
        this.type = type;
    }

    public HashSet<String> getClients() {
        return clients;
    }

    public void setClients(HashSet<String> clients) {
        this.clients = clients;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public HashMap<String, clientBean> getOnlines() {
        return onlines;
    }

    public void setOnlines(HashMap<String, clientBean> onlines) {
        this.onlines = onlines;
    }
}
