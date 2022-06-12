package ru.bgpu.server.transfer;

import com.google.gson.Gson;
import ru.bgpu.server.dto.FileInfoDto;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;

public class SocketWorker extends Thread {
    private Socket socket;
    private BufferedReader in;
    private OutputStream out;
    private static String FOLDER = "./files";

    public SocketWorker(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            in =  new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            String commandMsg = in.readLine();
            System.out.println(commandMsg);

            switch (commandMsg){
                case ("FilesList"):
                    this.sendFileList();
                    break;
                case ("SendFile"):
                    this.sendFile(in.readLine());
                    break;
            }
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendFileList() {
        try {
            out = socket.getOutputStream();
            // send files name
            File files = new File(FOLDER);
            Gson gson = new Gson();
            String fileNames = gson.toJson(Arrays.stream(files.listFiles()).map(FileInfoDto::new).collect(Collectors.toList()));
            System.out.println(fileNames);
            out.write(fileNames.length());
            out.write(fileNames.getBytes());

            out.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void sendFile (String fileName) throws IOException {
        System.out.println(fileName);
        File file = new File(new File(FOLDER),fileName);
        byte[] b = new byte[1048576];

        out = new DataOutputStream (new BufferedOutputStream (socket.getOutputStream()));
        FileInputStream fIn = new FileInputStream(file);

        fIn.read(b,0,b.length);
        out.write(b, 0, b.length);

        fIn.close();
        out.close();
    }
}
