package org.educa.game;


import java.util.ArrayList;
import java.util.List;

public class Match {

    private int id;
    private static int count = 0;
    private String gameType;
    private int playersNum;
    private List<Player> players;

    public Match(String gameType, int playersNum,List<Player> players) {
        id = count;
        this.gameType = gameType;
        this.playersNum = playersNum;
        this.players = players;
        count ++;
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

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
