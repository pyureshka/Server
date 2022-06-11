package ru.bgpu.server.transfer;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServerConenct extends Thread{
    public int port = 3002;
    public String name;
    public InetAddress ipTcpSocket = InetAddress.getLocalHost();
    private ServerSocket serverSocket;
    private Socket socket;

    public TcpServerConenct() throws IOException {
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
}
