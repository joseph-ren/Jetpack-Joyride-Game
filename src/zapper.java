import javax.swing.*;
import java.awt.*;
//this class is for the zappers, which are obstacles that the player must face in all 3 levels. Depending on the level, the zapper may be stationary, in motion, and can come in different sizes and orientations.
public class zapper {
    int x, y, width, length, orientation;
    Image[] pics=new Image[2];
    int delay, frame=0;
    public zapper(int xx, int yy, int wid, int len, int orient){
        x=xx;
        y=yy;
        width=wid;
        length=len;
        orientation=orient;
        if(orient==1){//horizontal orientation//
            pics[0]=new ImageIcon("zapperStraight0.png").getImage();
            pics[1]=new ImageIcon("zapperStraight1.png").getImage();}
        else if(orient==2){//vertical orientation//
            pics[0]=new ImageIcon("zapperUp0.png").getImage();
            pics[1]=new ImageIcon("zapperUp1.png").getImage();}
    }
    public void move(){//the zappers will move along with the screen
        x-=gamePanel.backgroundSpeed;
        if(x<-2000){//will go back and go onto the screen again//
            x+=5000;
            y=gamePanel.randomStartY();}
        delay+=1;
        if(delay%3==0){
            if(frame==1){
                frame=0;}
            else{
                frame=1;}}
    }
    public Rectangle getRect(){//returns the rectangle area of the laser//
        return new Rectangle(x+15, y+12, width-30, length-25);
    }

    public void draw(Graphics g){
        g.drawImage(pics[frame], x, y, null);}
}