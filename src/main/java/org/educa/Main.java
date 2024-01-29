package org.educa;

import org.educa.game.Server;

/**
 * Clase principal que inicia el servidor
 * @author Iker Ruiz y Javier Villarta
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando servidor...");
        Server server = new Server();
        server.run();
    }
}