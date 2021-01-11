package Client;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public class Pawn {
    private Color color;
    private int xPosition;
    private int yPosition;
    private String playerNick;
    private String id;
    private int size;

//asking player nick name
    public String getPlayerNick() {
        return playerNick;
    }
    //write players nickname into object
    public void setPlayerNick(String playerNick) {
        this.playerNick = playerNick;
    }

    public Pawn(int size) {
        this.size = size;
    }
//set position of player
    public void setPosition(int xPosition, int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }
//returning position
    public int getX() {
        return this.xPosition;
    }
    public int getY() {
        return this.yPosition;
    }
//set players color
    public void setColor(Color color) {
        this.color = color;
    }
//get color
    public Color getColor() {
        return this.color;
    }
//get id
    public String getId() {
        return this.id;
    }
//set id
    public void setId(String id) {
        this.id = id;
    }
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
}
