package org.educa.game;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;

public class Server {

    private final String HOST = "localhost";
    private final int PORT = 5555;
    private Map<Integer, Match> matches;
    private List<Player> players;
    public void run() {
        System.out.println("Creando socket servidor");
        Socket clientSocket = null;

        try(ServerSocket serverSocket = new ServerSocket()){
            InetSocketAddress addr = new InetSocketAddress(HOST,PORT);
            serverSocket.bind(addr);

            System.out.println("Aceptando conexiones.");

            while (true){
                try {
                    clientSocket = serverSocket.accept();
                    System.out.println("New connection: " + clientSocket);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);

                    String playerName = reader.readLine();
                    System.out.println("Player connected: " + playerName);



                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
