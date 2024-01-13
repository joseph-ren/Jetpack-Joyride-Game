import javax.swing.*;
import java.awt.*;//COMMENTS//
//this class exists to incorporate asteroids, which are an obstacle that it unlocked during level 2. THe rocks move at random y coordinates, and show indication of where they will go, making them potentially harder to deal with than the zappers//
public class rock {
    int x, y;
    int width=100;
    int length=82;

    Image rock=new ImageIcon("rock.png").getImage();
    public rock(int xx, int yy){
        x=xx;
        y=yy;
    }
    public void move(){
        x-=gamePanel.backgroundSpeed*2;
        if(x<-500){//will go back and go onto the screen again//
            if(gamePanel.level.equals("level 2")){
            x+=6000;}
            else if(gamePanel.level.equals("level 3")){
                x+=3000;}
            y=gamePanel.randomStartY();}
    }
    public Rectangle getRect(){//returns the rectangle area//
        return new Rectangle(x, y, width, length);
    }

    public void draw(Graphics g){
        g.drawImage(rock, x, y, null);
}}