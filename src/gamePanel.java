import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
//the entire game panel, which runs functions of moving and drawing the entire game and its features for each action that is performed. it hosts multiple screens including the intro, main game, main menu, and end. Users use the space bar to make the player go up or down.//
public class gamePanel extends JPanel implements ActionListener, MouseListener, KeyListener {
    private final boolean [] keys;
    private final Image intro, setting, caveBackground, spaceBackground, zapper, resume, restart, home, gameOver, setting0, setting1, levelUp;
    private String screen="intro";//game starts at the intro screen//
    static String level;
    int mouseX, mouseY;
    int levelUpDelay=0;
    boolean levelDelay=false;
    private int backgroundOneX, distance=0;//first background starts at x coordinate 0//
    int backgroundTwoX=9584;//second background starts at the x coordinate 9584//
    static int backgroundSpeed=20;//background's speed//
    Timer timer;
    static ArrayList<zapper> zappers= new ArrayList<>();
    static ArrayList<coin> coins= new ArrayList<>();
    static player guy;
    static boolean lostLife=false;//player has not lost a life by default//
    int missileInitiate=0;//missile will be created at a set time interval//
    int powerUpInitiate=0;//power up will be created at a set time interval//
    static missile MISSILE;
    static missile MISSILE2;
    static missile MISSILE3;
    static rock ROCK;
    static powerup POWERUP;
    boolean RESUME, RESTART, HOME=false;

    public gamePanel() {
        setPreferredSize(new Dimension(1200, 650));
        addMouseListener(this);
        addKeyListener(this);
        setFocusable(true);
        requestFocus();
        keys=new boolean[2000];
        intro=new ImageIcon("intro.png").getImage();
        caveBackground=new ImageIcon("cave.png").getImage();
        spaceBackground=new ImageIcon("space background.png").getImage();
        setting=new ImageIcon("setting.png").getImage();
        resume=new ImageIcon("resume.png").getImage();
        restart=new ImageIcon("restart.png").getImage();
        home=new ImageIcon("home.png").getImage();
        gameOver=new ImageIcon("gameOver.png").getImage();
        zapper=new ImageIcon("ZAPPER.png").getImage();
        setting0=new ImageIcon("setting0.png").getImage();
        setting1=new ImageIcon("setting1.png").getImage();
        levelUp=new ImageIcon("levelUp.png").getImage();

        guy=new player(200, 590);
        timer = new Timer(17, this);
        timer.start();
        int randomStartX=randomStartX();
        zappers.add(new zapper(randomStartX, randomStartY(), 186, 69, 1));
        zappers.add(new zapper(randomStartX+1200, randomStartY(), 69, 186, 2));
        zappers.add(new zapper(randomStartX+2000, randomStartY(), 186, 69, 1));

        for(int i=5500; i<6000; i+=50){
            for(int j=250; j<375; j+=25){
                coins.add(new coin(i, j));}}}
    public int randomStartX(){//sets a random starting x value for the zappers//
        Random rand=new Random();
        return rand.nextInt(2000, 3000);
    }
    static int randomStartY(){//returns a random starting y coordinate for the zappers//
        Random rand=new Random();
        return rand.nextInt(80, 500);}
    public void restart(){//if the player restarts the game, all the objects' positions will go back to as it was at the start. Any necessary booleans will also be reverted. Any necessary booleans will also be reverted, such as coins and the coinCollected boolean.//
        missileInitiate=0;
        backgroundSpeed=20;
        backgroundTwoX=9584;
        backgroundOneX=0;
        distance=0;
        levelUpDelay=0;
        levelDelay=false;
        if(ROCK!=null){ROCK=null;}
        int randomStartX=randomStartX();
        for(int i=zappers.size()-1; i>=0;i--){
            zappers.remove(i);}
        zappers.add(new zapper(randomStartX, randomStartY(), 186, 69, 1));
        zappers.add(new zapper(randomStartX+1200, randomStartY(), 69, 186, 2));
        zappers.add(new zapper(randomStartX+2000, randomStartY(), 186, 69, 1));
        MISSILE=null;
        MISSILE2=null;
        MISSILE3=null;
        int coin=0;
        for(int i=5500; i<6000; i+=50){
            for(int j=250; j<375; j+=25){
                coins.get(coin).x=i;
                coins.get(coin).y=j;
                coin+=1;
            }}
        POWERUP=null;
        guy=new player(200, 590);
        guy.coins=0;//resets coins//
        guy.deadDelay=0;
        lostLife=false;
    }
    public void levelUp(){//resets all necessary setting and details when you level up//
        missileInitiate=0;
        final int randomStartX=randomStartX();
        zappers.get(0).x=randomStartX;
        zappers.get(1).x=randomStartX+1300;
        zappers.get(2).x=randomStartX+2000;
        //adds zappers//
        zappers.add(new zapper(randomStartX+2500, randomStartY(), 69, 186, 2));
        if(level=="level 3"){
            zappers.add(new zapper(randomStartX+500, randomStartY(), 186, 69, 1));
        }
        ROCK=new rock(5000, randomStartY());//rock gets introduced to the game//
        if(level=="level 3")
        {
            ROCK=new rock(2500, randomStartY());
        }
        lostLife=false;}
    public String powerUp(){//chooses a random power up that can be collected by the player//
        String randomPowerUp;
        Random random=new Random();
        int rand=random.nextInt(3);
        if(rand==0){
            randomPowerUp="extra life";
        }
        else if(rand==1){
            randomPowerUp="magnet";
        }
        else{
            randomPowerUp="shield";
        }
        return randomPowerUp;
    }

    public void move(){//will move any of the objects if necessary//
        backgroundOneX-=backgroundSpeed;//moves the background//
        backgroundTwoX-=backgroundSpeed;
        missileInitiate+=20;
        powerUpInitiate+=20;
        distance+=1;//distance increases by 1 each time the game is run//
        if(backgroundOneX<-9584){//will redraw the background to make it constantly moving//
            backgroundOneX+=19168;}
        else if(backgroundTwoX<-9584){//will redraw the second background to make it constantly moving//
            backgroundTwoX+=19168;}
        //boolean for if the player is currently jumping//
        if(keys[KeyEvent.VK_SPACE]){
            guy.move("space");}


        else {//the guy will fall if the space bar isn't being pressed//
            guy.move("nothing");}
        if(guy.lives==0){
            screen="end";}
        for (zapper ZAPPER : zappers) {
            ZAPPER.move();}
        int coinTotal=0;
        for (coin COIN : coins) {
            COIN.move();
            if(COIN.x<-1650){//if all coins are now off the screen, they will respawn in a random pattern//
                coinTotal+=1;}}
        if(coinTotal==50){//this section will initiate random coin patterns that will show up at set intervals once all coins have gone off the screen//
            Random random=new Random();
            int rand=random.nextInt(4);
            if(level=="level 1"){
            if(rand==0){//first coin arrangement//
                int count=0;
                for(int i=2750; i<3000; i+=50){
                    for(int j=200; j<450; j+=50){
                        coins.get(count).x=i;
                        coins.get(count).y=j;
                        count+=1;}}
                for(int i=3000; i<3250; i+=50){
                    for(int j=200; j<450; j+=50){
                        coins.get(count).x=i;
                        coins.get(count).y=j;
                        count+=1;}}

            }
            else if(rand==1){//second coin arrangement//
                int count=0;
                for(int i=3000; i<3250; i+=50){
                    for(int j=405; j>225; j-=36){
                        coins.get(count).x=i;
                        coins.get(count).y=j;
                        count+=1;}}
                for(int i=3300; i<3550; i+=50){
                    for(int j=405; j>225; j-=36){
                        coins.get(count).x=i;
                        coins.get(count).y=j;
                        count+=1;}}
            }
            else if(rand==2){//third coin arrangement//
                int count=0;
                for(int i=3000; i<3500; i+=50){
                    for(int j=250; j<450; j+=100){
                        coins.get(count).x=i;
                        coins.get(count).y=j;
                        count+=1;}}
                for(int i=3050; i<3550; i+=50){
                    for(int j=300; j<450; j+=100){
                        coins.get(count).x=i;
                        coins.get(count).y=j;
                        count+=1;}}
            }
            else{
                int count=0;
                for(int i=3500; i<4000; i+=50){
                    for(int j=250; j<375; j+=25){
                        coins.get(count).x=i;
                        coins.get(count).y=j;
                        count+=1;}}
            }}
            else if(level=="level 2"){
                if(rand==0){//first coin arrangement//
                    int count=0;
                    for(int i=3100; i<3350; i+=50){
                        for(int j=200; j<450; j+=50){
                            coins.get(count).x=i;
                            coins.get(count).y=j;
                            count+=1;}}
                    for(int i=3350; i<3600; i+=50){
                        for(int j=200; j<450; j+=50){
                            coins.get(count).x=i;
                            coins.get(count).y=j;
                            count+=1;}}

                }
                else if(rand==1){//second coin arrangement//
                    int count=0;
                    for(int i=3000; i<3250; i+=50){
                        for(int j=405; j>225; j-=36){
                            coins.get(count).x=i;
                            coins.get(count).y=j;
                            count+=1;}}
                    for(int i=3300; i<3550; i+=50){
                        for(int j=405; j>225; j-=36){
                            coins.get(count).x=i;
                            coins.get(count).y=j;
                            count+=1;}}
                }
                else if(rand==2){//third coin arrangement//
                    int count=0;
                    for(int i=3100; i<3600; i+=50){
                        for(int j=250; j<450; j+=100){
                            coins.get(count).x=i;
                            coins.get(count).y=j;
                            count+=1;}}
                    for(int i=3050; i<3550; i+=50){
                        for(int j=300; j<450; j+=100){
                            coins.get(count).x=i;
                            coins.get(count).y=j;
                            count+=1;}}
                }
                else{
                    int count=0;
                    for(int i=3200; i<3700; i+=50){
                        for(int j=250; j<375; j+=25){
                            coins.get(count).x=i;
                            coins.get(count).y=j;
                            count+=1;}}
                }
            }
            else if(level=="level 3"){
                if(rand==0){//first coin arrangement//
                    int count=0;
                    for(int i=2500; i<2750; i+=50){
                        for(int j=200; j<450; j+=50){
                            coins.get(count).x=i;
                            coins.get(count).y=j;
                            count+=1;}}
                    for(int i=2750; i<3000; i+=50){
                        for(int j=200; j<450; j+=50){
                            coins.get(count).x=i;
                            coins.get(count).y=j;
                            count+=1;}}

                }
                else if(rand==1){//second coin arrangement//
                    int count=0;
                    for(int i=2200; i<2450; i+=50){
                        for(int j=405; j>225; j-=36){
                            coins.get(count).x=i;
                            coins.get(count).y=j;
                            count+=1;}}
                    for(int i=2500; i<2750; i+=50){
                        for(int j=405; j>225; j-=36){
                            coins.get(count).x=i;
                            coins.get(count).y=j;
                            count+=1;}}
                }
                else if(rand==2){//third coin arrangement//
                    int count=0;
                    for(int i=2300; i<2800; i+=50){
                        for(int j=250; j<450; j+=100){
                            coins.get(count).x=i;
                            coins.get(count).y=j;
                            count+=1;}}
                    for(int i=2250; i<2750; i+=50){
                        for(int j=300; j<450; j+=100){
                            coins.get(count).x=i;
                            coins.get(count).y=j;
                            count+=1;}}
                }
                else{
                    int count=0;
                    for(int i=2400; i<2900; i+=50){
                        for(int j=250; j<375; j+=25){
                            coins.get(count).x=i;
                            coins.get(count).y=j;
                            count+=1;}}
                }
            }
            //if the value 3 is chosen, all coins will go back to the starting arrangement//
            for(coin COIN: coins){ COIN.coinCollected=false;
                COIN.offScreen=false;
                COIN.delay=0;}
        }
        guy.coinIntersect();//always checks for coin intersection//

        if(level.equals("level 1")){
        if(missileInitiate==5000){//will shoot a missile at a set interval//
            MISSILE=new missile(5000, guy.y);
            missileInitiate=0;}} else if (level.equals("level 2")) {
            if(missileInitiate==4000){
                MISSILE=new missile(5000, guy.y);}
        else if(missileInitiate==5000){
                MISSILE2=new missile(5000, guy.y);
                missileInitiate=0;
             }
        }

        else {
            if (missileInitiate == 3000) {
                MISSILE = new missile(5000, guy.y);
            } else if (missileInitiate == 4000) {
                MISSILE2 = new missile(5000, guy.y);
            } else if (missileInitiate == 5000) {
                MISSILE3 = new missile(5000, guy.y);
                missileInitiate = 0;
            }
        }
        if(powerUpInitiate==15000){//will trigger a power up at a set interval//
            POWERUP=new powerup(5200, randomStartY(), powerUp());
            powerUpInitiate=0;
        }
        if(POWERUP!=null){
            POWERUP.move();}
        if(MISSILE!=null){
            MISSILE.move();}
        if (ROCK != null) {
            ROCK.move();
        }
        if(guy.deadDelay==0){//only checks intersections if the guy isn't in his revival period, or if shield isn't activated//
            if(POWERUP!=null){
                if(!POWERUP.shield){
                guy.intersect();}
            }
            else{
                guy.intersect();
            }
        }

        if(lostLife){ //once it loses a life, it will disappear a few times, to show that it has lost a life. During this period, anything the player intersects with on the screen will have no impact on the player//
            if(guy.deadDelay==10){
                guy.lives-=1;}
            guy.deadDelay +=1;
            if(guy.deadDelay%8==0){
                guy.drawDead=true;}
            if(guy.deadDelay==35){
                guy.deadDelay=0;//resets delay//
                lostLife=false;}}

        if(distance==1000) {//level 2 starts//
            levelDelay=true;
            level="level 2";
            levelUp();
        }
        else if(distance==2000) {
            levelDelay=true;
            level="level 3";
            levelUp();
        }
        else if(distance==3000 && guy.lives>0) {
            screen="win";
        }
    }
    public void mainMenu(){
        if(RESTART){
            restart();
            screen="intro";}
        else if(RESUME) {
            screen="game";
        }
        else if (HOME){
            screen="intro";
            restart();}
    }
    public void drawIntro(Graphics g){
        g.drawImage(intro, 0, 0, null);
    }
    public void drawGame(Graphics g){
        g.drawImage(caveBackground, backgroundOneX, 0, null);
        g.drawImage(spaceBackground, backgroundTwoX, 0, null);
        g.drawImage(zapper, 0, 0, null);
        g.drawImage(setting, 60, 10, null);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString("DISTANCE: ", 10, 20);
        g.drawString(distance+"", 100, 20);
        guy.drawLives(g);
        if(mouseX>=1130 && mouseX<=1180 && mouseY>=10 && mouseY<=60){//clicking the setting//
            g.drawImage(setting1, 1126, 6, null);}
        else{g.drawImage(setting0, 1130, 10, null);}
        if(levelDelay){
            levelUpDelay+=1;
        }
        if(levelUpDelay>0 && levelUpDelay%10==0) {//level up icon flashes//
            g.drawImage(levelUp, 500, 10, null);
                backgroundSpeed+=1;
        }
        if(levelUpDelay>50){
            levelUpDelay=0;
            levelDelay=false;
        }
        if(MISSILE!=null){//will only the draw the missile while it exists//
            MISSILE.draw(g);}
        if(POWERUP!=null) {
            POWERUP.draw(g);
        }
        if (ROCK != null) {
            ROCK.draw(g);

        }
        if(!guy.drawDead){//will only draw the guy if it's alive//
            guy.draw(g);}
        guy.drawDead=false;
        for (zapper ZAPPER : zappers) {
            ZAPPER.draw(g);}
        for (coin COIN : coins) {
            COIN.draw(g);}
    }
    public void drawMainMenu(Graphics g){
        g.setColor(Color.GRAY);
        g.drawRect(0, 0, 1200, 650);
        g.fillRect(0, 0, 1200, 650);
        g.drawImage(resume, 250, 500, null);
        g.drawImage(restart, 550, 500, null);
        g.drawImage(home, 850, 500, null);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Eras Bold ITC", Font.BOLD, 40));
        g.drawString("DISTANCE:     "+distance, 400, 250);
        g.drawString("LIVES:           "+guy.lives, 400, 300);
        g.drawString("COINS:        "+guy.coins, 400, 350);
        if(250<=mouseX && mouseX<=350 && 500<= mouseY && mouseY<=600){
            g.drawRect(250, 500, 100, 100);}
        else if(550<=mouseX && mouseX<=650 && 500<= mouseY && mouseY<=600){
            g.drawRect(550, 500, 100, 100);}
        else if(850<=mouseX && mouseX<=950 && 500<= mouseY && mouseY<=600){
            g.drawRect(850, 500, 100, 100);}
    }
    public void drawEnd(Graphics g){
        g.drawImage(gameOver, 0, 0, null);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Eras Bold ITC", Font.BOLD, 40));
        final int DISTANCE=distance;//will take the final distance when the player died//
        g.drawString("DISTANCE:                          "+DISTANCE, 200, 350);
        g.drawString("COINS COLLECTED:        "+guy.coins, 200, 400);
        g.drawString("HOME", 530, 500);
        g.drawRect(515, 450, 170, 75);
        Point mouse = MouseInfo.getPointerInfo().getLocation();
        Point offset = getLocationOnScreen();
        mouseX = mouse.x - offset.x;
        mouseY = mouse.y - offset.y;

    }
    public void drawWin(Graphics g){
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, 1200, 650);
        g.fillRect(0, 0, 1200, 650);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Eras Bold ITC", Font.BOLD, 40));
        final int DISTANCE=distance;//will take the final distance when the player died//
        g.setFont(new Font("Eras Bold ITC", Font.BOLD, 60));
        g.drawString("YOU WIN!", 500, 200);
        g.drawString("DISTANCE:                          "+DISTANCE, 200, 350);
        g.drawString("COINS COLLECTED:        "+guy.coins, 200, 400);
        g.drawString("HOME", 530, 500);
        g.drawRect(515, 450, 238, 75);
        Point mouse = MouseInfo.getPointerInfo().getLocation();
        Point offset = getLocationOnScreen();
        mouseX = mouse.x - offset.x;
        mouseY = mouse.y - offset.y;

    }
    @Override
    public void paint(Graphics g) {
        if(screen=="intro"){
            drawIntro(g);}
        else if(screen=="game"){
            drawGame(g);}
        else if (screen=="main menu"){
            drawMainMenu(g);}
        else if (screen=="end"){
            drawEnd(g);}
        else if(screen=="win"){
            drawWin(g);}
    }
    @Override
    public void actionPerformed(ActionEvent e){

        if (screen=="game") {
            move();
            //will reset the main menu options to have not being clicked//
            RESUME=false;
            RESTART=false;
            HOME=false;
            Point mouse = MouseInfo.getPointerInfo().getLocation();
            Point offset = getLocationOnScreen();
            mouseX = mouse.x - offset.x;
            mouseY = mouse.y - offset.y;
        }
        else if(screen=="main menu"){
            mainMenu();
            Point mouse = MouseInfo.getPointerInfo().getLocation();
            Point offset = getLocationOnScreen();
            mouseX = mouse.x - offset.x;
            mouseY = mouse.y - offset.y;}
        repaint();//the current screen will always be drawn//
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if(screen=="intro"){
            screen="game";
        level="level 1";}
        else if(screen=="game"){
            if(mouseX>=1130 && mouseX<=1180 && mouseY>=10 && mouseY<=60){//clicking the setting//
                screen="main menu";}}
        else if(screen=="main menu"){
            if(250<=mouseX && mouseX<=350 && 500<= mouseY && mouseY<=600){
                RESUME=true;}
            else if(550<=mouseX && mouseX<=650 && 500<= mouseY && mouseY<=600){
                RESTART=true;}
            else if(850<=mouseX && mouseX<=950 && 500<= mouseY && mouseY<=600){
                HOME=true;}}
        else if(screen=="end"){
            if(515<= mouseX && mouseX<=685 && 450<=mouseY && mouseY<=525){
                screen="intro";
                restart();}}
        else if(screen=="win"){
            if(515<= mouseX && mouseX<=685 && 450<=mouseY && mouseY<=525){
                screen="intro";
                restart();}}
    }
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e){}
    @Override
    public void mouseReleased(MouseEvent e) {
    }
    @Override
    public void mouseExited(MouseEvent  e){}
    @Override
    public void keyReleased(KeyEvent e){
        int code=e.getKeyCode();
        keys[code]=false;

    }
    @Override
    public void keyPressed(KeyEvent e){
        int code=e.getKeyCode();
        keys[code]=true;
    }
    @Override
    public void keyTyped(KeyEvent e){}
}