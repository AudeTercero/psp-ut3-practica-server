package org.educa.game;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


/**
 * Clase servidor que recoge y guarda la informacion de los jugadores que entran
 * y los empareja
 * @author Iker Ruiz y Javier Villarta
 */
public class Server {

    private final String HOST = "localhost";
    private final int PORT = 5555;
    protected static Map<Integer, Match> matches = new HashMap<>();
    protected static MatchesController mat = new MatchesController();

    /**
     * Clase run que inicia la logica de la clase servidor
     */
    public void run() {
        System.out.println("Creando socket servidor");
        Socket clientSocket = null;

        try (ServerSocket serverSocket = new ServerSocket()) {
            InetSocketAddress addr = new InetSocketAddress(HOST, PORT);
            serverSocket.bind(addr);

            System.out.println("Aceptando conexiones.");

            while (true) { //Se recogen los hilos de los jugadores y se crean hilos a modo de respuesta
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
