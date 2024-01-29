package org.educa.game;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Request implements Runnable {
    private static int idMatch = 0;
    private Socket clientSocket;
    private int playersReady;

    public Request(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {

            System.out.println("New connection: " + clientSocket);
            String data = reader.readLine();

            String[] playerInfo = data.split(",");
            System.out.println("hola");
            System.out.println(playerInfo[0]);

            if (playerInfo.length == 3) {
                String nick = playerInfo[0];
                String gameType = playerInfo[1];
                int playersNeeded = Integer.parseInt(playerInfo[2]);
                String host = clientSocket.getInetAddress().getHostAddress();
                int port = clientSocket.getPort();
                Player player = new Player(nick, host, port);
                System.out.println(player.getNickname());
                Match match = newPlayerInMatch(player, gameType, playersNeeded);
                out.writeObject(playerListInMatch(match));
                out.flush();
                //newPlayerInMatch(player, gameType, playersNeeded);


            } else {
                int id = Integer.parseInt(playerInfo[0]);
                endMatch(id);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void waitForPlayers(int playersNeeded) throws InterruptedException {
        playersReady++;
        while (playersReady < playersNeeded) {
            wait();
        }
    }


    public synchronized Match newPlayerInMatch(Player player, String gameType, int playersNeeded) throws InterruptedException {
        if (Server.matches.isEmpty()) {
            Match match = new Match(plusCount(), gameType, playersNeeded);
            player.setRoll(0);
            modifyMatch(match,player);
            modifyListMatch(match);
            while (!match.isMatchFull()) {
                System.out.println("Esperando jugadores1");
                wait();
            }
            System.out.println("Dejando de esperar");
            notifyAll();
            return match;
        } else {
            boolean isFull = true;

            for (Match match : Server.matches.values()) {
                if (match.getGameType().equalsIgnoreCase(gameType) && !match.isMatchFull()) {
                    player.setRoll(1);
                    modifyMatch(match,player);
                    isFull = false;
                    while (!match.isMatchFull()) {
                        System.out.println("Esperando jugadores2");
                        wait();
                    }
                    notifyAll();
                    return match;
                }

            }

            if (isFull) {
                Match match = new Match(plusCount(), gameType, playersNeeded);
                player.setRoll(0);
                modifyMatch(match,player);
                modifyListMatch(match);
                while (!match.isMatchFull()) {
                    System.out.println("Esperando jugadores3");
                    wait();
                }
                notifyAll();
                return match;
            }
        }
        return null;

    }
    private synchronized void modifyListMatch(Match match){
        Server.matches.put(match.getId(), match);
    }
    private synchronized void modifyMatch(Match match,Player player){
        match.getPlayers().add(player);
    }

    private ArrayList<String> playerListInMatch(Match match) {
        ArrayList<String> playerList = new ArrayList<>();
        for (Player p : match.getPlayers()) {
            playerList.add(match.getId() + "," + p.getNickname() + "," + p.getHost() + "," + p.getPort() + "," + p.getRoll());
        }
        return playerList;
    }

    public synchronized void endMatch(int id) {
        Server.matches.remove(id);
    }


    public synchronized int plusCount() {
        return idMatch++;
    }
}
