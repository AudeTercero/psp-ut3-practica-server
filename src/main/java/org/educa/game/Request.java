package org.educa.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Request implements Runnable {
    private Socket clientSocket;

    public Request(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);) {

            System.out.println("New connection: " + clientSocket);

            String[] playerInfo = reader.readLine().split(",");

            if (playerInfo.length == 3) {
                String nick = playerInfo[0];
                String gameType = playerInfo[1];
                int playersNeeded = Integer.parseInt(playerInfo[2]);

                String host = clientSocket.getInetAddress().getHostAddress();
                int port = clientSocket.getPort();

                Player player = new Player(nick, gameType, host, port);

                newPlayerInMatch(player, gameType, playersNeeded);


            } else {
                int id = Integer.parseInt(playerInfo[0]);
                endMatch(id);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void newPlayerInMatch(Player player, String gameType, int playersNeeded) {
        int count = 0;
        List<Player> auxPlayer = new ArrayList<>();
        if (!Server.playersWaiting.isEmpty()) {
            for (Player p : Server.playersWaiting) {
                if (p.getGameType().equalsIgnoreCase(player.getGameType())) {
                    count++;
                }
            }
            if (count >= playersNeeded) {
                auxPlayer.add(player);
                for (Player p : Server.playersWaiting) {
                    if (p.getGameType().equalsIgnoreCase(player.getGameType()) && auxPlayer.size() < playersNeeded) {
                        auxPlayer.add(p);
                        Server.playersWaiting.remove(p);
                    }
                }
                Match match = new Match(gameType, playersNeeded, auxPlayer);
                Server.matches.put(match.getId(), match);
            } else {
                Server.playersWaiting.add(player);
            }

        } else {
            Server.playersWaiting.add(player);
        }

    }

    public synchronized void endMatch(int id) {
        Server.matches.remove(id);
    }
}
