package org.educa.game;

/**
 * Clase que simula al jugador en el servidor
 * @author Iker Ruiz y Javier Villarta
 */
public class Player {
    private String nickname;
    private  int role;
    private String host;
    private int port;

    /**
     * Constructor de la clase jugador
     * @param nickname nombre del jugador
     * @param host host del jugador
     * @param port puerto del jugador
     */
    public Player(String nickname, String host, int port) {
        this.nickname = nickname;
        this.host = host;
        this.port = port;
    }

    /**
     * Metodo que devuelve el nickname de la clase
     * @return nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Metodo que modifica el nickname de la clase
     * @param nickname nuevo
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Metodo que devuelve el role de la clase
     * @return role
     */
    public int getRole() {
        return role;
    }

    /**
     * Metodo que modifica el role de la clase
     * @param role nuevo
     */
    public void setRole(int role) {
        this.role = role;
    }

    /**
     * Metodo que devuelve el host de la clase
     * @return host
     */
    public String getHost() {
        return host;
    }

    /**
     * Metodo que modifica el host de la clase
     * @param host nuevo
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * Metodo que devuelve el port de la clase
     * @return port
     */
    public int getPort() {
        return port;
    }

    /**
     * Metodo que modifica el port de la clase
     * @param port nuevo
     */
    public void setPort(int port) {
        this.port = port;
    }
}
