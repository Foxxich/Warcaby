package Client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Client{
    public static void main(String[] args) {
        System.out.println("Hi from client. ");
        //start menu asking for players name
        StartMenu startMenu = new StartMenu();
//        ReadyMenu readyMenu = new ReadyMenu();
        GameConfig gameConfig = new GameConfig();

        //trying to connect to server
            try (
                    Socket kkSocket = new Socket("localhost", 59001);
                    PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
            ) {
                System.out.println("Waiting for message");
                while (true) {
                    String fromServer;
                    String toServer;
                    do {
                        fromServer = in.readLine();
                    } while (fromServer == null);
                    String command;
                    System.out.println(fromServer);
                    if (fromServer.contains(" ")) {
                        command = fromServer.substring(0, fromServer.indexOf(' '));
                    } else {
                        command = fromServer;
                    }
                    switch (command) {
                        case "NAME_GET":
                            out.println("NAME_SET "+startMenu.getPlayerName());
                            break;
                        case "WAITING_PLAYERS":
                            System.out.println("Waiting 4 players");
                            break;
                        case "PLAYERS_NUMBER":
                            String amountStr = fromServer.substring(15);
                            int amount = Integer.parseInt(amountStr);
                            gameConfig.setPlayersAmount(amount);
                            break;
                        case "PLAYERS_NAMES":
                            String playersNames = fromServer.substring(14);
                            ArrayList<String> names = new ArrayList<>(Arrays.asList(playersNames.split(" ")));
                            gameConfig.setNames(names);
                            break;
                        case "START_GAME":
                            gameConfig.preparePlayers();
                            Game game = new Game(gameConfig);
                            break;
                    }
                }
            }
            //exception if we couldn't find server
            catch (Exception e) {
                e.printStackTrace();
                throw new IllegalStateException("Not connected");
            }
        //start game


    }
}