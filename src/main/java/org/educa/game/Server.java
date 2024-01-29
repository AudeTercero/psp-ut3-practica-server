package org.educa.game;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Server {
    private static int idMatch = 0;
    private final String HOST = "localhost";
    private final int PORT = 5555;
    protected final static Map<Integer, Match> matches = new HashMap<>();
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


    public static synchronized Match newPlayerInMatch(Player player, String gameType, int playersNeeded) throws InterruptedException {

        if (Server.matches.isEmpty()) {//Si no hay partidas
            Match match = new Match(plusCount(), gameType, playersNeeded);
            player.setRoll(0);
            modifyMatch(match, player);
            modifyListMatch(match);



            System.out.println("Esperando jugadores1");


            while (!match.isMatchFull()) {
                //wait();
                Thread.sleep(1000);
            }

            System.out.println("Dejando de esperar");

            return match;
        } else {
            boolean isFull = true;

            for (Match match : Server.matches.values()) {
                if (match.getGameType().equalsIgnoreCase(gameType) && !match.isMatchFull()) {//si hay una partida con hueco
                    player.setRoll(1);
                    modifyMatch(match, player);
                    isFull = false;
                    while (!match.isMatchFull()) {
                        System.out.println("Esperando jugadores2");
                        //wait();
                        Thread.sleep(1000);
                    }

                    return match;
                }
            }

            if (isFull) {//si hay partidas pero ninguna con hueco
                Match match = new Match(plusCount(), gameType, playersNeeded);
                player.setRoll(0);
                modifyMatch(match, player);
                modifyListMatch(match);
                while (!match.isMatchFull()) {
                    System.out.println("Esperando jugadores3");
                    //wait();
                    Thread.sleep(1000);

                }

                return match;
            }
        }

        return null;

    }

    private static synchronized void modifyListMatch(Match match) {
        Server.matches.put(match.getId(), match);

    }

    private static synchronized void modifyMatch(Match match, Player player) {
        match.getPlayers().add(player);

    }
    public static synchronized void endMatch(int id) {
        Server.matches.remove(id);
    }


    public static synchronized int plusCount() {
        return idMatch++;
    }


}
