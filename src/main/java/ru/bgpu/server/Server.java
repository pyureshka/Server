package ru.bgpu.server;

import ru.bgpu.server.transfer.TcpServerConenct;
import ru.bgpu.server.transfer.UdpServerConnect;

import java.io.IOException;

public class Server {
    private static String FOLDER = "./files";

    public static void main(String[] args) throws IOException {
        TcpServerConenct tcp = new TcpServerConenct();
        UdpServerConnect udp = new UdpServerConnect(tcp.getPort(), tcp.getIpTcpSocket(), tcp.getServerName());

        udp.start();
        tcp.start();
    }
}
