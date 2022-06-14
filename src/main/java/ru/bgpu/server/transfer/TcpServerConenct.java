package ru.bgpu.server.transfer;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

public class TcpServerConenct extends Thread{
    private int port;
    public String name;
    public InetAddress ipTcpSocket;
    private ServerSocket serverSocket;
    private Socket socket;

    public TcpServerConenct() throws IOException {
        Properties prop = new Properties();
        File propFile = new File("config.properties");
        if(propFile.exists()) {
            prop.load(new FileReader("config.properties"));
        }
        port = Integer.parseInt(prop.getProperty("port","3002"));
        ipTcpSocket = InetAddress.getByName(prop.getProperty("host","localhost"));
        name = prop.getProperty("name", System.getProperty("user.name"));

        serverSocket = new ServerSocket(port, 2, ipTcpSocket);
    }

    public void run() {
        while (true) {
            try {
                new SocketWorker(serverSocket.accept()).start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int getPort() {
        return port;
    }
    public String getIpTcpSocket() {
        return ipTcpSocket.getHostAddress();
    }

    public String getServerName() {
        return name;
    }
}
