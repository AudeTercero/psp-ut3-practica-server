package org.educa.game;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Request implements Runnable {

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

            if (playerInfo.length == 3) {
                String nick = playerInfo[0];
                String gameType = playerInfo[1];
                int playersNeeded = Integer.parseInt(playerInfo[2]);

                String host = clientSocket.getInetAddress().getHostAddress();
                int port = clientSocket.getPort();

                Player player = new Player(nick, host, port);

                System.out.println(player.getNickname());

                Match match = Server.newPlayerInMatch(player, gameType, playersNeeded);
                notifyAll();

                out.writeObject(playerListInMatch(match));
                out.flush();
                //newPlayerInMatch(player, gameType, playersNeeded);


            } else {
                int id = Integer.parseInt(playerInfo[0]);
                Server.endMatch(id);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private ArrayList<String> playerListInMatch(Match match) {
        ArrayList<String> playerList = new ArrayList<>();
        for (Player p : match.getPlayers()) {
            playerList.add(match.getId() + "," + p.getNickname() + "," + p.getHost() + "," + p.getPort() + "," + p.getRoll());
        }
        return playerList;
    }


}
