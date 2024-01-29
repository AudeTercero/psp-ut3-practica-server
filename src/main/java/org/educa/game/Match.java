package org.educa.game;


import java.util.ArrayList;
import java.util.List;

public class Match {

    private int id;
    private static int count = 0;
    private String gameType;
    private int playersNum;
    private List<Player> players;

    public Match(int id, String gameType, int playersNum) {
        this.id = id;
        this.gameType = gameType;
        this.playersNum = playersNum;
        this.players = new ArrayList<>();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public int getPlayersNum() {
        return playersNum;
    }

    public void setPlayersNum(int playersNum) {
        this.playersNum = playersNum;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public boolean isMatchFull() {
        if (players.size() == playersNum) {
            return true;
        }
        return false;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
