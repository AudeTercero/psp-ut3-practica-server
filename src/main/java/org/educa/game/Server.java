package org.educa.game;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Server {

    private final String HOST = "localhost";
    private final int PORT = 5555;
    protected static Map<Integer, Match> matches = new HashMap<>();
    protected static List<Player> playersWaiting;

    public void run() {
        System.out.println("Creando socket servidor");
        Socket clientSocket = null;

        try (ServerSocket serverSocket = new ServerSocket()) {
            InetSocketAddress addr = new InetSocketAddress(HOST, PORT);
            serverSocket.bind(addr);

            System.out.println("Aceptando conexiones.");

            while (true) {
                clientSocket = serverSocket.accept();
                Request request = new Request(clientSocket);
                Thread thread = new Thread(request);
                thread.start();


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
