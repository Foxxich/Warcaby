package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Game implements MouseListener{
    private GameConfig gameConfig;
    private Board board;
    private Frame gameFrame;
    public Game(GameConfig gameConfig) {
        this.gameConfig = gameConfig;
        Board board = new Board(40, gameConfig);
        Frame gameFrame = new Frame(board);
    }

    public void mouseClicked(MouseEvent arg0) {
        System.out.println("game: click");
    }

    public void mouseEntered(MouseEvent arg0) {
    }

    public void mouseExited(MouseEvent arg0) {
    }

    public void mousePressed(MouseEvent arg0) {
    }

    public void mouseReleased(MouseEvent arg0) {
    }

    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub

    }
}
