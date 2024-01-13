import javax.swing.*;
import java.awt.*;
import java.util.Objects;
//this class exists as an object that the user can collect for assistance during the game. All power ups provide some benefit to the player for a limited time duration when the power up is collected by the player. Power ups are created at set intervals, and become null once off the screen. Depending on the power up, the corresponding graphics can be drawn within this class to create an animation visible to the player.
public class powerup {
    int x, y;
    String powerUp;
    boolean initiate=false;//boolean for if the power has been collected, and thus initiated//
    Image[] pics=new Image[4];
    Image[] magnets=new Image[4];
    Image[] shields=new Image[5];
    Image extraLife;
    int shieldDelay, shieldFrame=0;
    int magnetDelay, magnetFrame=0;
    int counter=-1;
    int extraLifeDelay;
    int delay, frame=0;
    boolean shield, magnet, newLife, coins=false;
    int initiation=300;

    public powerup(int xx, int yy, String pow){
        x=xx;
        y=yy;
        powerUp=pow;
        for(int i=0; i<4; i++){
            pics[i]=new ImageIcon("powerUp"+i+".png").getImage();}
        for(int i=0; i<5; i++){
            shields[i]=new ImageIcon("shield"+i+".png").getImage();}
        for(int i=0; i<4; i++){
            magnets[i]=new ImageIcon("magnet"+i+".png").getImage();}
        extraLife=new ImageIcon("extraLife.png").getImage();
    }
    public void move(){
        x-=gamePanel.backgroundSpeed;
        delay+=1;
        if(delay%5==0){
            if(frame==3){
                frame=0;}
            else{
                frame+=1;}}

        if(initiate){
            initiation+=1;}
        if(shield){//draws shield around the player if shield is initiated//
            shieldDelay+=1;
            if(shieldDelay%3==0){
                if(shieldFrame==4){
                    counter*=-1;}
                else if(shieldFrame==0){
                    counter*=-1;}
                shieldFrame+=counter;}}
        else if(magnet){//draws magnet around the player if magnet is initiated//
            magnetDelay+=1;
            if(magnetDelay%2==0){
                if(magnetFrame==3){
                    magnetFrame=0;}
                else{
                    magnetFrame+=1;}}}
        else if(newLife){
            extraLifeDelay+=1;
        }
        if(initiation==800) {
            gamePanel.POWERUP = null;
            //will make the power up null once it has been on the screen, and has now come off the screen//}}
        }
    }

    public Rectangle getRect(){//change coordinates//
        return new Rectangle(x+10, y+12, 80, 75);}

    public void draw(Graphics g){
        if(!initiate){
            g.drawImage(pics[frame], x, y, null);}
        else{//timer bar//
            if(powerUp.equals("magnet") || powerUp.equals("shield") || powerUp.equals("coins")){
                g.setColor(Color.BLUE);
                g.drawRect((int)initiation, 10, (int)(800-initiation), 10);
                g.fillRect((int)initiation, 10, (int)(800-initiation), 10);
                if(shield){g.drawImage(shields[shieldFrame], player.x-(shieldFrame*2), player.y-(shieldFrame*2),null);}
                else if(magnet){g.drawImage(magnets[magnetFrame], player.x-25, player.y-25,null);}
            }
            else{//extra life icon will flash instead of having the timer bar//
                if(extraLifeDelay<61 && extraLifeDelay%15!=0) {
                    g.drawImage(extraLife, 1000, 10, null);}}}}}

