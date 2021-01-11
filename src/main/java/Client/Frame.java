package Client;

import javax.swing.JFrame;

public class Frame extends JFrame {
//rendering a window of the game
private int width;
private int height;
    public Frame(Board board) {
        super("Chinese checkers");
        add(board);
        this.width = 1000;
        this.height = 800;
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
