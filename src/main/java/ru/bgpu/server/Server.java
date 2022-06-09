package ru.bgpu.server;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.Arrays;

public class Server {
    private static String FOLDER = "./files";

    public static void main(String[] args) throws IOException {
        TcpServerConenct tcp = new TcpServerConenct();
        UdpServerConnect udp = new UdpServerConnect(tcp.getPort(), tcp.getIpTcpSocket(), tcp.getName());

        udp.start();
        tcp.start();
    }
}
