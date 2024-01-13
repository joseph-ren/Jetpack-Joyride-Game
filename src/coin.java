import javax.swing.*;
import java.awt.*;
//this class exists as objects (coins) that the user collects throughout the entire game. There are several coin arrangements, but all arrangements include 50 coins//
public class coin {
    int x, y;
    int delay=0;
    Image [] collectedCoin=new Image[4];
    Image coin=new ImageIcon("coin.png").getImage();
    boolean delete=false;
    int rise, run;//slope between player's position and coin's position; used during magnet power up//
    int largestFactor=1;
    int xAmount, yAmount=0;//amount that the coin moves in x and y direction each time; used during magnet power up//
    boolean coinCollected=false;//boolean for if the coin has been collected by the player. By default, the coin has not been collected by the player//
    boolean offScreen=false;
    public coin(int xx, int yy){
        x=xx;
        y=yy;
        for(int i=0; i<4; i++){
            collectedCoin[i]=new ImageIcon("coinCollected"+i+".png").getImage();}
    }
    public void move(){
        x-=gamePanel.backgroundSpeed;

        if(coinCollected){
            if(gamePanel.POWERUP!=null && gamePanel.POWERUP.magnet){//coins move as if attracted to the magnet to cause a realistic magnet effect//
                Rectangle boundary=new Rectangle(gamePanel.guy.x-25, gamePanel.guy.y-25, 100, 100);
                Rectangle coinRect=new Rectangle(x, y, 20, 20);
                    rise=gamePanel.guy.y+25-(y+10);//change in y//
                    run=gamePanel.guy.x+25-(x+10);//change in x//
                    for(int i=2; i<=run; i++){//finds the largest factor and divides the rise and run by it to find the slope in lowest terms//
                        if(rise%i==0 && run%i==0) {
                            largestFactor = i;}}
                    if(largestFactor!=1){//rise and run in lowest terms//
                        xAmount=run/largestFactor;
                        yAmount=rise/largestFactor;}
                    else{
                        xAmount=run;
                        yAmount=rise;}

                    if(x<gamePanel.guy.x){//moves towards the guy until it collides with the guy//
                    if(!coinRect.intersects(boundary)){x+=xAmount;
                    y-=(yAmount*-1);}
                    else{//after colliding with the guy, the coin will "disappear"//
                        offScreen=true;
                        x=-1651;
                    }
                    }
            }
        else{
            offScreen=true;
        }

    }}
    public Rectangle getRect(){//returns the rectangle area of the laser//
        return new Rectangle(x, y, 20, 20);
    }
    public void draw(Graphics g){
        if(!offScreen) {//draws coin when it has not been collected//
            g.drawImage(coin, x, y, null);}
        else{//draws the stars when it has been collected and off the screen//
            delay+=1;
            if(delay==1){
                g.drawImage(collectedCoin[0], x, y, null);}
            else if (delay==3){
                g.drawImage(collectedCoin[1], x, y, null);}
            else if (delay==5){
                g.drawImage(collectedCoin[2], x, y, null);}
            else if(delay>=7){
                g.drawImage(collectedCoin[3], x, y, null);}

            }
        }
    }