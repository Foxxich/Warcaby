package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.*;
import java.util.concurrent.Executors;


import java.util.concurrent.Executor;

public class Server {

    private int playerNumber;
    private GameRules gameRules;

    String fromClient;

    private static SharedData data;

    public Server() {
        data = SharedData.getInstance();
    }

    public void broadcast(String message) {
        Collection<PlayerHandler> players = Collections.synchronizedCollection(data.getPlayerHandlers());
            synchronized (players) {
                for (PlayerHandler playerHandler : players) {
                    PlayerSocket playerSocket = playerHandler.getPlayer();
                    playerSocket.sendStr(message);
                }
            }
    }


    private void getNewConfig() {
        playerNumber = UserInterface.getInt("Podaj liczbe graczy.");
        gameRules = new GameRules();

    }

    private void sendPlayersNames() {
        Collection<String> playersNames = Collections.synchronizedCollection(data.getNames());
        StringBuilder stringBuilder = new StringBuilder("PLAYERS_NAMES");
        synchronized (playersNames) {
            for(String name : playersNames) {
                stringBuilder.append(" ");
                stringBuilder.append(name);
            }
        }
        System.out.println(stringBuilder.toString());
        broadcast(stringBuilder.toString());
    }

    //start server
    public void start() {
        UserInterface.print("Starting server");
        while (true) {
            UserInterface.print("Starting game");

            getNewConfig();

            // Wait for players
            UserInterface.print("Waiting for players");
            broadcast("WAITING_PLAYERS");
            int currentPlayerNumber = 0;

            Executor pool = Executors.newFixedThreadPool(6);

            try (ServerSocket listener = new ServerSocket(59001)) {
                System.out.println("Connected players : " + currentPlayerNumber);
                while (currentPlayerNumber < playerNumber) {

                    PlayerSocket playerSocket = new PlayerSocket(listener.accept());
                    pool.execute(new PlayerHandler(playerSocket, data));
                    currentPlayerNumber++;

                    System.out.println("Player connected :)");
                }
            } catch (IOException e) {

            }
            sendPlayersNames();
            // Game
            data.game = new Game(gameRules);
            broadcast("START_GAME");

            Iterator<String> playerNames = data.getNames().iterator();

            while (data.game.ended()) {
                if (!playerNames.hasNext())
                    playerNames = data.getNames().iterator();

                broadcast("MOVE_NOW " + playerNames.next());
                try {
                    pool.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }


        }
    }

    public static void main(String[] args) {
        Server s = new Server();
        s.start();
    }
}
