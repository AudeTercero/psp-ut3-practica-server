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
        try {

            System.out.println("New connection: " + clientSocket);
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);

            String[] playerInfo = reader.readLine().split(",");

            String nick = playerInfo[0];
            String gameType = playerInfo[1];
            int port = Integer.parseInt(playerInfo[2]);
            String host = playerInfo[3];

            Player player = new Player(nick,host,port);

           // newPlayerInMatch(player, gameType);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
/*
    public void newPlayerInMatch(Player player, String gameType) {
        int count = 0;
        List<Player> auxPlayer = new ArrayList<>();
        if (!players.isEmpty()) {
            for (Player p : players) {
                if (p.type.equalsIgnoreCase(player.type)) {
                    count++;
                }
            }
            if (count >= needPlayers) {
                auxPlayer.add(player);
                for (Player p : players) {
                    if (p.type.equalsIgnoreCase(player.type) && auxPlayer.size() < needPlayers) {
                        auxPlayer.add(p);
                    }
                }
                matches.add(new Match());
            } else {
                players.add(player);
            }

        } else {
            players.add(player);
        }

    }*/

    public void endMatch(int id) {

    }
}
