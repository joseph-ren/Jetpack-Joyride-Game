import javax.swing.*;//this class is a frame class which exists to make and display game panels for the user to see//
//Joseph Ren//
//Jetpack Joyride.java//
//this is a game where the user is attempting to survive all 3 levels of obstacles and targeted attacks, starting with 3 lives. They can collect coins along the way, and can also gain power-ups that will help them along their journey. At any moment, the player can use the main menu to pause, resume, or restart. The player moves up and down in a gravitational manner, by using the space bar. //
class Frame extends JFrame{
    public Frame(){
        super("Jetpack Joyride");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new gamePanel());
        pack();
        setVisible(true);
    }
    public static void main(String[] args){
        Frame ex= new Frame();
    }
}