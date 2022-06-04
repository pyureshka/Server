package ru.bgpu.server;

public class ServerInfo {
    public int port;
    public String host;
    public String name;

    public ServerInfo(int port, String host, String name){
        this.name = name;
        this.host = host;
        this.port = port;
    };
    public ServerInfo(int port, String host){
        this.name = null;
        this.host = host;
        this.port = port;
    };

    public ServerInfo(){};

    public void setPort(int port) {
        this.port = port;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setName(String name) {
        this.name = name;
    }
}
