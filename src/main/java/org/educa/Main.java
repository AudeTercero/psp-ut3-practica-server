package org.educa;

import org.educa.game.Server;

public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando servidor...");
        Server server = new Server();
        server.run();
    }
}