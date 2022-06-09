package ru.bgpu.server;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class TcpServerConenct extends Thread{
    public int port = 3001;
    public String name;

    private static String FOLDER = "./files";

    public InetAddress ipTcpSocket = InetAddress.getLocalHost();
    private ServerSocket serverSocket;
    private Socket socket;

    public TcpServerConenct() throws IOException {
        serverSocket = new ServerSocket(port, 2, ipTcpSocket);
    }

    public void run() {
        while (true) {
            try {
                socket = serverSocket.accept();
                OutputStream out = socket.getOutputStream();
                // send files name
                File files = new File(FOLDER);
                Gson gson = new Gson();
                String fileNames = gson.toJson(Arrays.asList(files.list()));
                System.out.println(fileNames);
                out.write(fileNames.length());
                out.write(fileNames.getBytes());
            } catch (Exception ex) {
                System.out.println(ex.toString());
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
