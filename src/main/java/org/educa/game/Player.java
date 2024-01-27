package org.educa.game;

public class Player {
    private String nickname;
    private String gameType;
    private String host;
    private int port;

    public Player(String nickname, String gameType, String host, int port) {
        this.nickname = nickname;
        this.gameType = gameType;
        this.host = host;
        this.port = port;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
