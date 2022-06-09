package ru.bgpu.server;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.*;

public class UdpServerConnect extends Thread{
    private MulticastSocket mcServerSocket;
    private DatagramPacket reqv;
    private InetAddress ipGroup = InetAddress.getByName("228.5.6.7");
    private NetworkInterface netIf = NetworkInterface.getByName("");
    private InetSocketAddress mcastaddr = new InetSocketAddress(ipGroup, 2121);
    private boolean running;
    private InetAddress addressCliSoc;
    private int portCliSoc;


    private int tcpPort = 0;
    private String ipTcpSocket = "";
    private String hostName = "";

    public UdpServerConnect(int port, String ip, String name) throws IOException {
            mcServerSocket = new MulticastSocket(2121);
            mcServerSocket.joinGroup(mcastaddr, netIf);

            this.ipTcpSocket = ip;
            this.hostName = name;
            this.tcpPort = port;
    }


    public void run() {
        try {
            receiveMsgFromClient();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void receiveMsgFromClient() throws IOException {
        running = true;
        while (running) {
            byte[] buf = new byte[1000];
            reqv = new DatagramPacket(
                    buf,
                    buf.length,
                    ipGroup,
                    2121
            );
            mcServerSocket.receive(reqv);

            String stringReqv = new String(reqv.getData());
            System.out.println("receive: " + stringReqv);

            this.sendInfoToClient(tcpPort, ipTcpSocket, hostName, reqv.getAddress(), reqv.getPort());
        }
        mcServerSocket.close();
    }

    public void sendInfoToClient (int port, String ipTcpSocket, String hostName, InetAddress reqvIddress, int reqvPort) throws IOException {
        ServerInfo serverInfo = new ServerInfo(
                port,
                ipTcpSocket,
                hostName
        );

        Gson gson = new Gson();
        String jsonInfo = gson.toJson(serverInfo);
        System.out.println(jsonInfo);

        DatagramPacket packet = new DatagramPacket(
                jsonInfo.getBytes(),
                jsonInfo.length(),
                reqvIddress,
                reqvPort
        );
        mcServerSocket.send(packet);
        System.out.println("Sent a multicast message.");
    }
}
