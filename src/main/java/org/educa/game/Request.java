package org.educa.game;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Clase que controla la logica enfocada al control de jugadores y la comunicacion con los mismos
 * @author Iker Ruiz y Javier Villarta
 */
public class Request implements Runnable {

    private Socket clientSocket;
    private int playersReady;

    /**
     * Constructor de la clase
     * @param clientSocket socket propio de cada jugador para la comunicacion en red con los mismos
     */
    public Request(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    /**
     * Metodo que inicia la clase
     */
    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {

            String data = reader.readLine();//se recibe la informacion del jugador

            String[] playerInfo = data.split(",");//la informacion se separa

            if (playerInfo.length == 3) {//si el jugador trae cierta cantidad de informacion, es que esta buscando iniciar una partida
                //Guardamos la informacion en variables concretas
                String nick = playerInfo[0];
                String gameType = playerInfo[1];
                int playersNeeded = Integer.parseInt(playerInfo[2]);

                String host = clientSocket.getInetAddress().getHostAddress();
                int port = clientSocket.getPort();

                //Creamos el jugador en el servidor a partir de su informacion
                Player player = new Player(nick, host, port);

                System.out.println("Conectado jugador: "+player.getNickname());

                //Guardamos al jugador en una partida nueva o ya creada
                Match match = Server.mat.newPlayerInMatch(player, gameType, playersNeeded);

                //Esperamos hasta que la partida este llena
                Server.mat.waitPlayers(match);

                //Devolvemos al jugador la informacion necesaria para comenzar la partida con su emparejamiento
                out.writeObject(playerListInMatch(match));
                out.flush();

            } else {//Condicion en la que el jugador host informa del fin de la partida
                int id = Integer.parseInt(playerInfo[0]);
                Server.mat.endMatch(id);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo que devuelve un listado de la informacion que se devuelve a su jugador
     * @param match partida correspondiente al jugador
     * @return listado de informacion para el jugador
     */
    private ArrayList<String> playerListInMatch(Match match) {
        ArrayList<String> playerList = new ArrayList<>();
        for (Player p : match.getPlayers()) {
            playerList.add(match.getId() + "," + p.getNickname() + "," + p.getHost() + "," + p.getPort() + "," + p.getRole());
        }
        return playerList;
    }

}
