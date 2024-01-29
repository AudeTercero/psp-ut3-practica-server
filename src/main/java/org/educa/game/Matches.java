package org.educa.game;

public class Matches {
    private static int idMatch = 0;

    protected synchronized Match newPlayerInMatch(Player player, String gameType, int playersNeeded) throws InterruptedException {


        boolean isFull = true;
        System.out.println(Server.matches.size());
        if (Server.matches.isEmpty()) {
            Match match = new Match(plusCount(), gameType, playersNeeded);
            match.getPlayers().add(player);
            Server.matches.put(match.getId(), match);
            System.out.println("--"+Server.matches.size());


            return match;
        } else {
            System.out.println(Server.matches.size());
            for (Match match : Server.matches.values()) {
                System.out.println(match.getGameType().equalsIgnoreCase(gameType) + "" + !match.isMatchFull());
                if (match.getGameType().equalsIgnoreCase(gameType) && !match.isMatchFull()) {//si hay una partida con hueco
                    player.setRoll(1);
                    modifyMatch(match, player);
                    isFull = false;


                    return match;
                }
            }

            if (isFull) {//si hay partidas pero ninguna con hueco
                Match match = new Match(plusCount(), gameType, playersNeeded);
                player.setRoll(0);
                modifyMatch(match, player);
                modifyListMatch(match);
                System.out.println("");

                return match;
            }
        }


        return null;

    }
    public void waitPlayers(Match match) throws InterruptedException {
        while (!match.isMatchFull()) {
            System.out.println("Esperando jugadores");
            //wait();
            Thread.sleep(1000);

        }
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
