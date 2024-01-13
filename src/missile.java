import javax.swing.*;
import java.awt.*;
//this is a class of missiles, which are launched according to the player's position at certain intervals, depending on the level. There is a basic constructor, a move & draw function. Certain graphics will be drawn once the missile is at certain stages of its movement//
public class missile {
    int x, y, actionY;
    boolean warning, action=false;//boolean for certain movement stages while the missile is moving//
    int frame, delay, warningDelay=0;
    Image[] images=new Image[11];
    Image INCOMING, WARNING;

    public missile(int xx, int yy) {
        x = xx;
        y = yy;
        for (int i = 0; i < 11; i++) {
            images[i] = new ImageIcon("missile" + i + ".png").getImage();
        }

        INCOMING = new ImageIcon("incoming.png").getImage();
        WARNING = new ImageIcon("warning.png").getImage();

    }

    public void move(){
        if(!action){
        x-=gamePanel.backgroundSpeed*2;}
        else{
            x-=gamePanel.backgroundSpeed*2.5;}
        if(x<0){//will make the missile null once it has been on the screen, and has now come off the screen//
            gamePanel.MISSILE=null;}
        else if(x==2600){//this will initiate the warning graphics//
            actionY=gamePanel.guy.y;}
        else if(x<2600 && x>=1200){//will take the player's y position once it's about to show up on the screen//
            warning=true;
        warningDelay+=1;}
        else if(x<1200){//will draw the missile once it's on the player's screen//
            action=true;
            warning=false;
            if(delay%5==0){
                delay+=1;
                if(frame==10){
                    frame=0;}
                else{
                    frame+=1;}}
        }
    }
    public Rectangle getRect(){
        return new Rectangle(x, actionY+5, 135, 45);}

    public void draw(Graphics g){
        if(warning){//draws warning graphics//
            if(warningDelay%15!=0) {
                g.drawImage(WARNING, 1080, actionY, null);}}
        else if(action){//action stage graphics//
            g.drawImage(images[frame], x ,actionY, null);}
        else{//incoming stage//
            g.drawImage(INCOMING, 1080, gamePanel.guy.y, null);}
        g.setColor(Color.BLUE);
    }
}