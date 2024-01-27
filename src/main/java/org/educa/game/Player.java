package org.educa.game;

public class Player {
    private String nickname;
    private String host;
    private int port;

    public Player(String nickname, String host, int port) {
        this.nickname = nickname;
        this.host = host;
        this.port = port;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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
