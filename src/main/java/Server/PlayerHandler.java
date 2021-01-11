package Server;

import java.util.ArrayList;

public class PlayerHandler implements Runnable {
    private String name;
    PlayerSocket player;

    SharedData data;

    public PlayerHandler(PlayerSocket player, SharedData data) {
        this.player = player;
        this.data = data;
    }

    private synchronized String reciveName() {
        String temp = null;
            ArrayList<String> names = data.getNames();
            player.sendStr("NAME_GET");
            while(temp == null) {
                temp = player.recive();
            }
            System.out.println(temp);
            // Name recived: NAME_SET userName
            temp = temp.substring(9);
            if(!temp.isBlank() && !names.contains(temp)) {
                data.addName(temp);
        }

        player.sendStr("NAME_ACCEPTED");

        return temp;
    }

    private synchronized void sendPlayerAmount() {
        player.sendStr("PLAYERS_NUMBER " + data.getNumberOfPlayers());
    }

    public synchronized PlayerSocket getPlayer() {
        return player;
    }

    public void run() {
        try {

            name = reciveName();
            sendPlayerAmount();
            data.addPlayerHandlers(this);
            UserInterface.print(name + " joined. "+player.getSocket().getLocalSocketAddress());
            boolean connected = true;
            while(connected) {
                String input = null;
                while(input == null) {
                    input = player.recive();
                }
                String[] res = input.split("\\s");
                switch (res[0]) {
                    case "DISCONNECT": {connected = false;} break;
                    case "MOVE": {
                        if(!data.game.interpretMove(input.substring(5)))
                            player.sendStr("MOVE_BAD");
                        else
                            this.notify();
                    } break;
                }
            }

        }
        catch(Exception e) {
            System.out.println(e);
        }
        finally {
            if (player != null) {
                player.sendStr("DISCONNECTED");
                data.deletePlayerHandlers(this);
            }
            if (name != null) {
                UserInterface.print(name + " left" );
                data.deleteName(name);
            }
            player.close();

        }

    }
}
