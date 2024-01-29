package org.educa.game;


import java.util.ArrayList;
import java.util.List;

/**
 * Clase de las partidas en el servidor
 * @author Iker Ruiz y Javier Villarta
 */
public class Match {

    private int id;
    private String gameType;
    private int playersNum;
    private List<Player> players;

    /**
     * Constructor de la clase Match
     * @param id identificador de la clase partida
     * @param gameType tipo de la clase partida
     * @param playersNum jugadores necesarios para la partida
     */
    public Match(int id, String gameType, int playersNum) {
        this.id = id;
        this.gameType = gameType;
        this.playersNum = playersNum;
        this.players = new ArrayList<>();

    }

    /**
     * Metodo booleano que confirma o niega si una partida esta al completo
     * de jugadores o no
     * @return booleano
     */
    public boolean isMatchFull() {
        if (players.size() == playersNum) {
            return true;
        }
        return false;
    }

    /**
     * Metodo que devuelve la id de la clase
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Metodo que modifica la id de la clase
     * @param id nueva
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Metodo que devuelve el gameType de la clase
     * @return gameType
     */
    public String getGameType() {
        return gameType;
    }

    /**
     * Metodo que modifica el gameType de la clase
     * @param gameType nuevo
     */
    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    /**
     * Metodo que devuelve el numero de jugadores de la clase
     * @return playersNum
     */
    public int getPlayersNum() {
        return playersNum;
    }

    /**
     * Metodo que modifica el numero de jugadores de la clase
     * @param playersNum nuevo
     */
    public void setPlayersNum(int playersNum) {
        this.playersNum = playersNum;
    }

    /**
     * Metodo que devuelve la lista de jugadores de la clase
     * @return players
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Metodo que modifica la lista de jugadores de la clase
     * @param players nuevo
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
