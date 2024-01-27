package org.educa.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Request implements Runnable{
    private Socket clientSocket;
    public Request(Socket clientSocket){
        this.clientSocket = clientSocket;
    }
    @Override
    public void run() {
        try {

            System.out.println("New connection: " + clientSocket);
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);

            String playerName = reader.readLine();
            System.out.println("Player connected: " + playerName);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*public void newPlayerInMatch(Player player) {
        int count;
        List<Player>auxPlayer = new ArrayList<>();
        if(!players.isEmpty()) {
            for (Player p : players) {
                if (p.type.equalsIgnoreCase(player.type)) {
                    count++;
                }
            }
            if (count >= needPlayers) {
                auxPlayer.add(player);
                for (Player p : players) {
                    if (p.type.equalsIgnoreCase(player.type)&&auxPlayer.size()<needPlayers) {
                        auxPlayer.add(p);
                    }
                }
                matches.add(new Match());
            }else{
                players.add(player);
            }

        }else{
            players.add(player);
        }

    }*/
}
