package ru.bgpu.server;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.*;

public class Server {
    private static String FOLDER = "./files";

    public static void main(String[] args) {

        try {
            MulticastSocket mcServerSocket = new MulticastSocket(2121);
            InetAddress ipGroup = InetAddress.getByName("228.5.6.7");
            NetworkInterface netIf = NetworkInterface.getByName("");
            InetSocketAddress mcastaddr = new InetSocketAddress(ipGroup, 2121);
            mcServerSocket.joinGroup(mcastaddr,netIf);

            byte[] buf = new byte[1000];
            DatagramPacket reqv = new DatagramPacket(
                    buf,
                    buf.length,
                    ipGroup,
                    2121
            );
            mcServerSocket.receive(reqv);
            String stringReqv = new String(reqv.getData());
            System.out.println("receive: " + stringReqv);

            InetAddress ipTcpSocket = InetAddress.getLocalHost();
            ServerSocket serverSocket = new ServerSocket(3000,2,ipTcpSocket);

            ServerInfo serverInfo = new ServerInfo(
                    serverSocket.getLocalPort(),
                    serverSocket.getInetAddress().getHostAddress(),
                    serverSocket.getInetAddress().getHostName()
            );

            Gson gson = new Gson();
            String jsonInfo = gson.toJson(serverInfo);
            System.out.println(jsonInfo);

            DatagramPacket packet = new DatagramPacket(
                    jsonInfo.getBytes(),
                    jsonInfo.length(),
                    reqv.getAddress(),
                    reqv.getPort()
            );
            mcServerSocket.send(packet);
            System.out.println("Sent a multicast message.");

          Socket socket = serverSocket.accept();

            OutputStream out = socket.getOutputStream();

            // send files

            File files = new File(FOLDER);
            FileInfo fileInfo = new FileInfo(files.list());
            String fileNames = gson.toJson(fileInfo);
            System.out.println(fileNames);
                out.write(fileNames.length());
                out.write(fileNames.getBytes());

            System.out.println("Exit");

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
