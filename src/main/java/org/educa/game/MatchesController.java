package org.educa.game;

/**
 * Clase que controla la logica de las partidas
 * @author Iker Ruiz y Javier Villarta
 */
public class MatchesController {
    private static int idMatch = 1;

    /**
     * Metodo que asigna a un jugador una partida nueva o lo asocia a una ya existente
     * @param player jugador a colocar
     * @param gameType tipo de partida que se busca
     * @param playersNeeded jugadores necesarios en la partida
     * @return partida adjudicada
     * @throws InterruptedException posibles errores
     */
    protected synchronized Match newPlayerInMatch(Player player, String gameType, int playersNeeded) throws InterruptedException {

        boolean isFull = true;

        if (Server.matches.isEmpty()) {
            Match match = new Match(plusCount(), gameType, playersNeeded);
            match.getPlayers().add(player);
            Server.matches.put(match.getId(), match);
            return match;
        } else {

            for (Match match : Server.matches.values()) {
                if (match.getGameType().equalsIgnoreCase(gameType) && !match.isMatchFull()) {//si hay una partida con hueco
                    player.setRole(1);
                    modifyMatch(match, player);
                    isFull = false;

                    System.out.println("Partida: "+match.getId()+". " + match.getPlayers().getFirst().getNickname()+" vs "+ match.getPlayers().getLast().getNickname());
                    return match;
                }
            }

            if (isFull) {//si hay partidas pero ninguna con hueco
                Match match = new Match(plusCount(), gameType, playersNeeded);
                player.setRole(0);
                modifyMatch(match, player);
                modifyListMatch(match);

                return match;
            }
        }

        return null;
    }

    /**
     * Metodo que hace esperar a un hilo(jugador)
     * @param match partida del jugador
     * @throws InterruptedException posibles errores
     */
    public void waitPlayers(Match match) throws InterruptedException {
        while (!match.isMatchFull()) {
            System.out.println("Esperando jugadores");
            //wait();
            Thread.sleep(1000);
        }

    }

    /**
     * Metodo que guarda una partida en la lista de partidas del servidor
     * @param match partida a guardar
     */
    private synchronized void modifyListMatch(Match match) {
        Server.matches.put(match.getId(), match);

    }

    /**
     * Metodo que guarda a un jugador en una partida
     * @param match partida en la que guardar al jugador
     * @param player jugador a guardar
     */
    private synchronized void modifyMatch(Match match, Player player) {
        match.getPlayers().add(player);

    }

    /**
     * Metodo que borra una partida de la listas de partidas una vez ha finalizado
     * @param id id de la partida a finalizar
     */
    public synchronized void endMatch(int id) {
        Server.matches.remove(id);
    }


    /**
     * Metodo que otorga nuevos id´s cada vez que se crea una partida
     * @return
     */
    public synchronized int plusCount() {
        return idMatch++;
    }
}
