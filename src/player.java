import javax.swing.*;
import java.awt.*;
//this is the player class, which exists to create the main player. It can move, and check for obstacle/powerup intersections. it has 3 lives, and also keeps track of the coins it has earned during its journey. //
public class player {
    static int x, y;
    int upSpeed=0;
    int lives=3;
    int deadDelay=0;
    static int coins=0;
    boolean drawDead=false;//by default the guy is not dead, so the game doesn't need to draw its death//
    Image[] groundPics=new Image[6];
    Image[] upPics=new Image[4];
    int delay, frame, upDelay, upFrame=0;//delay and image index variables for when the guy is on the ground running, or flying in the air//
    boolean up=false;//the guy starts off by not moving up//
    Image lifeIcon=new ImageIcon("lifeIcon.png").getImage();

    public player(int xx, int yy){
        x=xx;
        y=yy;
        for(int i=0; i<6; i++){
            groundPics[i]=new ImageIcon("guy"+i+".png").getImage();}
        for(int i=6; i<10; i++){
            upPics[i-6]=new ImageIcon("guy"+(i)+".png").getImage();}
    }
    public void move(String button){
        if(button=="space"){//if the user is pressing the space bar//
            up=true;
            upDelay+=1;
            if(y-upSpeed>=30){//will make it go up, as long as it's in bounds of the game//
                y-=upSpeed;}
            else{//will make sure the guy will always go the same spot once it reached the top//
                y=30;}

            if(upDelay%3==0){//draws the sprite when it is in the air//
                if(upFrame==3){
                    upFrame=0;}
                else{
                    upFrame+=1;}}

            if(upDelay%7==0 && y-upSpeed>=15){
                upSpeed+=7;}
        }

        else if(button=="nothing"){//when the user isn't pressing the space bar//
            up=false;
            delay+=1;
            if(y+20<590){//will keep the guy in bounds of the game//
                y+=20;}
            else{
                y=590;}

            if(y==590){//draws the graphics when the guy is on the ground//
                if(delay%4==0){
                    if(frame==5){
                        frame=0;}
                    else{
                        frame+=1;}}}
            if(delay%15==0 && upSpeed-10>=0){//the speed when going up will decrease as the guy is moving downward, so the guy will need to "reaccelerate" its speed when going up again. This makes the game appear to have a realistic rocket effect//
                upSpeed-=14;}
        }

    }
    public void coinIntersect(){//checks for coin intersections//
        Rectangle playerRect = new Rectangle(x, y, 50, 50);
        if(gamePanel.POWERUP!=null && gamePanel.POWERUP.magnet){//if the magnet is initiated, the player's boundary to collect coins is larger//
            playerRect = new Rectangle(x, y, 175, 175);
            for(coin COIN: gamePanel.coins) {//checks for intersections with coins//
                if (playerRect.intersects(COIN.getRect())) {
                    COIN.coinCollected=true;//the coin has been collected, and will disappear from the screen//
                    }}}
        else{
        for(coin COIN: gamePanel.coins) {//checks for intersections with coins//
            if (playerRect.intersects(COIN.getRect())) {
                COIN.coinCollected=true;//the coin has been collected, and will disappear from the screen//
                coins+=1;}}
    }}
    public void intersect() {//checks for intersections with any of the objects//
        Rectangle playerRect = new Rectangle(x, y, 50, 50);
        for (zapper ZAPPER: gamePanel.zappers) {//checks for intersections with lasers//
            if (playerRect.intersects(ZAPPER.getRect())) {
                gamePanel.lostLife=true;
            }}
        if(gamePanel.MISSILE!=null){//loses a life if they intersect with a missile//
            if(playerRect.intersects(gamePanel.MISSILE.getRect())){
                gamePanel.lostLife=true;}}
        if(gamePanel.ROCK!=null){
        if(playerRect.intersects(gamePanel.ROCK.getRect())){
            gamePanel.lostLife=true;}}
        if(gamePanel.POWERUP!=null){//initiates power up if they intersect with it//
            if(playerRect.intersects(gamePanel.POWERUP.getRect())){
                gamePanel.POWERUP.initiate=true;
                if(gamePanel.POWERUP.powerUp.equals("extra life")){//player gains an extra life, unless they already have 3 lives//
                    if(lives<3){
                        lives+=1;}
                    gamePanel.POWERUP.newLife=true;;
                }
                else if(gamePanel.POWERUP.powerUp.equals("shield")){//shield power up activates, and the program will not check for intersection with objects, except coins//
                    gamePanel.POWERUP.shield=true;;
                }
                else {//magnet power up activates, and the program will not check for intersection with objects, except coins//
                    gamePanel.POWERUP.magnet=true;}
            }}
    }
    public void draw(Graphics g){//draws animations and sprites depending on the player's location//
        if(y==590){
            g.drawImage(groundPics[frame], x, y, null);}
        else if (y>=100 && y<=250 && !up){
            g.drawImage(upPics[3], x, y, null);}
        else if(y>250 && y<590 && !up){
            g.drawImage(groundPics[2], x, y, null);}
        else{
            g.drawImage(upPics[upFrame], x, y, null);}
    }

    public void drawLives(Graphics g){//draws all the player's stats//
        int livesX=140;
        for(int i=0; i<lives; i++){
            g.drawImage(lifeIcon, livesX, 5, null);
            livesX+=15;}
        g.setColor(Color.YELLOW);
        g.drawOval(10, 30, 15, 15);
        g.fillOval(10, 30, 15, 15);
        g.drawString(""+coins, 40, 43);
    }
}