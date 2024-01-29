package org.educa.game;

public class Matches {
    private static int idMatch = 0;

    protected synchronized Match newPlayerInMatch(Player player, String gameType, int playersNeeded) throws InterruptedException {


        boolean isFull = true;

        for (Match match : Server.matches.values()) {
            if (match.getGameType().equalsIgnoreCase(gameType) && !match.isMatchFull()) {//si hay una partida con hueco
                player.setRoll(1);
                modifyMatch(match, player);
                isFull = false;
//                while (!match.isMatchFull()) {
//                    System.out.println("Esperando jugadores2");
//                    //wait();
//                    //Thread.sleep(1000);
//                }

                return match;
            }
        }

        if (isFull) {//si hay partidas pero ninguna con hueco
            Match match = new Match(plusCount(), gameType, playersNeeded);
            player.setRoll(0);
            modifyMatch(match, player);
            modifyListMatch(match);
//            while (!match.isMatchFull()) {
//                System.out.println("Esperando jugadores3");
//                //wait();
//                //Thread.sleep(1000);
//
//            }

            return match;
        }


        return null;

    }

    private synchronized void modifyListMatch(Match match) {
        Server.matches.put(match.getId(), match);

    }

    private synchronized void modifyMatch(Match match, Player player) {
        match.getPlayers().add(player);

    }

    public synchronized void endMatch(int id) {
        Server.matches.remove(id);
    }


    public synchronized int plusCount() {
        return idMatch++;
    }
}
