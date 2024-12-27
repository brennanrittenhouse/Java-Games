
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.io.InputStream;
import javax.swing.JOptionPane;
import java.io.FileInputStream;
import javax.swing.Timer;
//import sun.audio.AudioPlayer;
//import sun.audio.AudioStream;

/**
 *
 * @author rittb0758
 */
public class SpaceInvaders extends JComponent implements ActionListener {

    // Height and Width of our game
    static final int WIDTH = 800;
    static final int HEIGHT = 600;

    //Title of the window
    String title = "My Game";

    // sets the framerate and delay for our game
    // this calculates the number of milliseconds per frame
    // you just need to select an approproate framerate
    int desiredFPS = 60;
    int desiredTime = Math.round((1000 / desiredFPS));

    // timer used to run the game loop
    // this is what keeps our time running smoothly :)
    Timer gameTimer;

    // YOUR GAME VARIABLES WOULD GO HERE
    //AudioStream audios;
    BufferedImage heartPic;
    boolean game = false;
    int direction = 1;
    int playerDirection = 5;
    boolean left = false;
    boolean right = false;
    boolean shoot = false;
    Rectangle enemy = new Rectangle(10, 10, 25, 25);
    Rectangle badLaser = new Rectangle(20, 10, 5, 25);
    Rectangle enemyCheck = new Rectangle(10, 10, 25, 25);
    Rectangle player = new Rectangle(375, 560, 50, 25);
    Rectangle laser = new Rectangle(398, 560, 5, 25);
    Rectangle shieldA = new Rectangle(30, 500, 60, 50);
    Rectangle shieldB = new Rectangle(370, 500, 60, 50);
    Rectangle shieldC = new Rectangle(710, 500, 60, 50);
    Rectangle loseLine = new Rectangle(0, 490, 800, 1);
    int shootSpeed = 15;
    int badShootSpeed = 10;
    boolean superShoot = false;
    int down = enemy.height;
    boolean[][] enemyState = new boolean[8][4];
    int enemyDisplayX[] = new int[8];
    int enemyDisplayY[] = new int[4];
    boolean wholeRow = false;
    int points = 0;
    int lossCount = 0;
    boolean bre = false;
    boolean funnyStrategy = false;
    boolean restore = false;

    // GAME VARIABLES END HERE    
    // Constructor to create the Frame and place the panel in
    // You will learn more about this in Grade 12 :)
    public SpaceInvaders() {
        // creates a windows to show my game
        JFrame frame = new JFrame(title);

        // sets the size of my game
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // adds the game to the window
        frame.add(this);

        // sets some options and size of the window automatically
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // shows the window to the user
        frame.setVisible(true);

        // add listeners for keyboard and mouse
        frame.addKeyListener(new Keyboard());
        Mouse m = new Mouse();
        this.addMouseMotionListener(m);
        this.addMouseWheelListener(m);
        this.addMouseListener(m);

        // Set things up for the game at startup
        setup();

        // Start the game loop
        gameTimer = new Timer(desiredTime, this);
        gameTimer.setRepeats(true);
        gameTimer.start();
    }

    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g) {
        // always clear the screen first!
        g.clearRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        //win/lose display
        //if the player has won...
        if (win()) {
            g.setColor(Color.GREEN);
            Font fontB = new Font("arial", Font.BOLD, 50);
            g.setFont(fontB);
            g.drawString("YOU WIN!!", 260, 300);
            //stop the game timer so the text remains on the screen until the timer starts again
            gameTimer.stop();
        }
        //if the player has lost...
        if (loss()) {
            g.setColor(Color.RED);
            Font fontB = new Font("arial", Font.BOLD, 50);
            g.setFont(fontB);
            g.drawString("YOU LOSE!!", 235, 300);
            gameTimer.stop();
        }
        //start display
        //when game = false (the player is not currently playing the game)
        if (!game) {
            Font fontD = new Font("arial", Font.PLAIN, 20);
            g.setFont(fontD);
            g.drawString("Score: " + points, 10, 15);
            g.setColor(Color.PINK);
            Font fontA = new Font("arial", Font.BOLD, 30);
            g.setFont(fontA);
            g.drawString("Choose a level", 275, 350);
            Font fontC = new Font("arial", Font.PLAIN, 20);
            g.setFont(fontC);
            g.drawString("easy[1], medium[2], difficult[3]", 250, 380);
        }

        //if the player is currently playing the game
        if (game) {
            //points display
            g.setColor(Color.WHITE);
            Font fontD = new Font("arial", Font.PLAIN, 10);
            g.setFont(fontD);
            g.drawString("Score: " + points, 10, 10);
            //funny strategy
            if (funnyStrategy && shoot) {
                g.drawString("hmm... Funny strategy", 10, 50);
            }
            //lives display
            //draw the same number of hearts as the player has lives, life one positioned on the left
            for (int h = 3; h > lossCount; h--) {
                g.drawImage(heartPic, 80 - h * 25, 20, 20, 20, null);
            }
            //lose line (shows the player how far the enemies can get before they lose)
            g.setColor(Color.RED);
            g.fillRect(loseLine.x, loseLine.y, loseLine.width, loseLine.height);
            //player
            g.setColor(Color.BLUE);
            g.fillRect(player.x, player.y, player.width, player.height);
            //enemies
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 4; j++) {
                    //top row of enemies are red
                    if (j == 0) {
                        g.setColor(Color.RED);
                    } //second row of enemies are orange
                    else if (j == 1) {
                        g.setColor(Color.ORANGE);
                    } //third row of enemies are green
                    else if (j == 2) {
                        g.setColor(Color.GREEN);
                    } //fourth row of enemies are blue
                    else if (j == 3) {
                        g.setColor(Color.BLUE);
                    }
                    //if the enemy is still alive, draw it in its designated space
                    if (enemyState[i][j]) {
                        enemyDisplayY[j] = enemy.y + j * 50;
                        enemyDisplayX[i] = enemy.x + i * 50;
                        g.fillRect(enemy.x + i * 50, enemy.y + j * 50, enemy.width, enemy.height);
                        g.setColor(Color.WHITE);
                    }
                }
            }
            //draw random position bad laser
            g.setColor(Color.PINK);
            g.fillRect(badLaser.x, badLaser.y, badLaser.width, badLaser.height);
            //draw 3 shields near the bottom of the screen
            g.setColor(Color.WHITE);
            g.fillRect(shieldA.x, shieldA.y, shieldA.width, shieldA.height);
            g.fillRect(shieldB.x, shieldB.y, shieldB.width, shieldB.height);
            g.fillRect(shieldC.x, shieldC.y, shieldC.width, shieldC.height);
            //draw the player's laser
            g.setColor(Color.RED);
            g.fillRect(laser.x, laser.y, laser.width, laser.height);
            //cheat code display when cheat is true (is only displayed at a certain time)
            if (cheat()) {
                g.setColor(Color.PINK);
                g.setFont(fontD);
                g.drawString("This is your CUE", 710, 580);
                g.drawString("to shoot faster", 710, 590);
                g.drawString("Why do you", 10, 580);
                g.drawString("Want to Win, Willis?", 10, 590);
            }
        }
    }

    // This method is used to do any pre-setup you might need to do
    // This is run before the game loop begins!
    public void setup() {
        // Any of your pre setup before the loop starts should go here
        //calls the reset method to make sure all variables are properly set
        reset();
        //load in heart image in a try catch
        try {
            heartPic = ImageIO.read(new File("heart.png"));
        } catch (IOException ex) {
            Logger.getLogger(SpaceInvaders.class.getName()).log(Level.SEVERE, null, ex);
        }
        //laser sound effect
        InputStream sound;
        try {
            sound = new FileInputStream(new File("heat-vision (1).wav"));
            //audios = new AudioStream(sound);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }
        //make sure all enemies are "alive" (enemyState = true)
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                enemyState[i][j] = true;
            }
        }
    }

    public void moveEnemies() {
        //enemies move horizontally by adding the int direction to the x position
        enemy.x = enemy.x + direction;
        for (int i = 0; i < 4; i++) {
            //right
            //the following if statements make the enemies change direction when the rightmost row of "living enemies" hits the side of the screen
            if (enemy.x > 740 && enemyState[0][i]) {
                //change direction
                direction = direction * -1;
                //move the enemies closer to the bottom of the screen
                enemy.y = enemy.y + down;
                //prevents some errors - enemy.x will no longer meet the requirements for the if statement
                enemy.x = enemy.x - 5;
            }
            if (enemy.x > 695 && enemyState[1][i]) {
                direction = direction * -1;
                enemy.y = enemy.y + down;
                enemy.x = enemy.x - 5;
            }
            if (enemy.x > 650 && enemyState[2][i]) {
                direction = direction * -1;
                enemy.y = enemy.y + down;
                enemy.x = enemy.x - 5;
            }
            if (enemy.x > 605 && enemyState[3][i]) {
                direction = direction * -1;
                enemy.y = enemy.y + down;
                enemy.x = enemy.x - 5;
            }
            if (enemy.x > 560 && enemyState[4][i]) {
                direction = direction * -1;
                enemy.y = enemy.y + down;
                enemy.x = enemy.x - 5;
            }
            if (enemy.x > 515 && enemyState[5][i]) {
                direction = direction * -1;
                enemy.y = enemy.y + down;
                enemy.x = enemy.x - 5;
            }
            if (enemy.x > 470 && enemyState[6][i]) {
                direction = direction * -1;
                enemy.y = enemy.y + down;
                enemy.x = enemy.x - 5;
            }
            if (enemy.x > 425 && enemyState[7][i]) {
                direction = direction * -1;
                enemy.y = enemy.y + down;
                enemy.x = enemy.x - 5;
            }
            //left
            //the following if statements make the enemies change direction when the leftmost row of "living enemies" hits the side of the screen
            if (enemy.x < 0 && enemyState[0][i]) {
                direction = direction * -1;
                enemy.y = enemy.y + down;
                enemy.x = enemy.x + 5;
            }
            if (enemy.x < -45 && enemyState[1][i]) {
                direction = direction * -1;
                enemy.y = enemy.y + down;
                enemy.x = enemy.x + 5;
            }
            if (enemy.x < -90 && enemyState[2][i]) {
                direction = direction * -1;
                enemy.y = enemy.y + down;
                enemy.x = enemy.x + 5;
            }
            if (enemy.x < -135 && enemyState[3][i]) {
                direction = direction * -1;
                enemy.y = enemy.y + down;
                enemy.x = enemy.x + 5;
            }
            if (enemy.x < -180 && enemyState[4][i]) {
                direction = direction * -1;
                enemy.y = enemy.y + down;
                enemy.x = enemy.x + 5;
            }
            if (enemy.x < -225 && enemyState[5][i]) {
                direction = direction * -1;
                enemy.y = enemy.y + down;
                enemy.x = enemy.x + 5;
            }
            if (enemy.x < -270 && enemyState[6][i]) {
                direction = direction * -1;
                enemy.y = enemy.y + down;
                enemy.x = enemy.x + 5;
            }
            if (enemy.x < -315 && enemyState[7][i]) {
                direction = direction * -1;
                enemy.y = enemy.y + down;
                enemy.x = enemy.x + 5;
            }
        }
    }

    public void movePlayer() {
        //if left is true and player isn't shooting, player and laser(starting position) will move to the left
        if (left == true && shoot == false) {
            player.x = player.x - playerDirection;
            laser.x = laser.x - playerDirection;
        } //if right is true and player isn't shooting, player and laser(starting position) will move to the right
        else if (right == true && !shoot) {
            player.x = player.x + playerDirection;
            laser.x = laser.x + playerDirection;
        } //lets player move left even after they've released a laser
        else if (left) {
            player.x = player.x - playerDirection;
        } //lets player move right even after they've released a laser
        else if (right) {
            player.x = player.x + playerDirection;
        }
    }

    public void shoot() {
        //change the speed of the laser based on the cheat codes used
        if (superShoot) {
            shootSpeed = 25;
        } else {
            shootSpeed = 15;
        }
        //laser moves upwards based on the established speed
        laser.y = laser.y - shootSpeed;
        //when the laser leaves the screen...
        if (laser.y < 0) {
            //reset laser in the middle of the player rectangle
            laser.y = 560;
            laser.x = player.x + 23;
            shoot = false;
        }
    }

    public void collision() {
        //nested for loops are used to check the state of every enemy in the array
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                if (enemyState[j][i]) {
                    //enemyCheck will be set as the area where a "living" enemy is
                    enemyCheck.x = enemy.x + j * 50;
                    enemyCheck.y = enemy.y + i * 50;
                    //only check the following is the player's laser has hit an area where a "living" enemy is
                    if (laser.intersects(enemyCheck)) {
                        //if "cheat code" bre is true...
                        if (bre) {
                            //kills all enemies
                            for (int w = 0; w < 8; w++) {
                                for (int z = 0; z < 4; z++) {
                                    enemyState[w][z] = false;
                                    //gives player a massive score
                                    points = 1000000000;
                                }
                            }
                        } //if "cheat code" funnyStrategy is true...
                        else if (funnyStrategy) {
                            //revives all enemies
                            for (int w = 0; w < 8; w++) {
                                for (int z = 0; z < 4; z++) {
                                    enemyState[w][z] = true;
                                    //reset points to zero
                                    points = 0;
                                }
                            }
                        } //if "cheat code" wholeRow is true...
                        else if (wholeRow) {
                            //one shot kills the whole row and increases score
                            for (int k = 0; k < 4; k++) {
                                if (enemyState[j][k]) {
                                    points++;
                                }
                                enemyState[j][k] = false;
                            }
                        } //if none of the "cheat codes" are being used
                        else {
                            //set the state of whichever enemy was hit by the player's laser
                            enemyState[j][i] = false;
                            //different points values are assigned to each row of enemies (upper ones are worth more)
                            if (i == 0) {
                                points += 4;
                            } else if (i == 1) {
                                points += 3;
                            }
                            if (i == 2) {
                                points += 2;
                            }
                            if (i == 3) {
                                points++;
                            }
                        }
                        System.out.println("points: " + points);
                        //reset laser
                        laser.y = 560;
                        laser.x = player.x + 23;
                        shoot = false;
                    }
                }
            }
        }
    }

    public void enemyShoot() {
        //shoot enemy's laser downward if it isn't past the bottom of the screen
        if (badLaser.y <= 600) {
            badLaser.y = badLaser.y + badShootSpeed;
        } else {
            //create two random numbers and use them to chose which enemy the enemyLaser starts from
            int badShootX = (int) (Math.random() * (8 - 1 + 1) + 1);
            int badShootY = (int) (Math.random() * (4 - 1 + 1) + 1);
            if (enemyState[badShootX - 1][badShootY - 1]) {
                badLaser.x = enemyDisplayX[badShootX - 1];
                badLaser.y = enemyDisplayY[badShootY - 1];
            }
        }
    }

    public void shieldCollision() {
        if (badLaser.intersects(shieldA)) {
            //make the shield thinner each time it is hit
            shieldA.width = shieldA.width - 10;
            System.out.println("A subtract");
            //move laser outside or the range of the shield so it doesn't seem as though the laser has hit the shield multiple times
            badLaser.y = 700;
            //call enemyShoot method
            enemyShoot();
        } else if (badLaser.intersects(shieldB)) {
            shieldB.width = shieldB.width - 10;
            System.out.println("B subtract");
            badLaser.y = 700;
            enemyShoot();
        } else if (badLaser.intersects(shieldC)) {
            shieldC.width = shieldC.width - 10;
            System.out.println("C subtract");
            badLaser.y = 700;
            enemyShoot();
        }
        //prevents the player from being able to shoot through any of the shields
        if (laser.intersects(shieldA) || laser.intersects(shieldB) || laser.intersects(shieldC)) {
            laser.y = 560;
            laser.x = player.x + 23;
            shoot = false;
        }
    }

    public boolean playerCollision() {
        //if restore "cheat code" is true, player has three lives again
        if (restore) {
            lossCount = 0;
        }
        //if the player is hit, increase lossCount and move laser out of range of the player (to prevent errors)
        if (badLaser.intersects(player)) {
            lossCount++;
            badLaser.y = 700;
            System.out.println("lossCount = " + lossCount);
            //if the player has used all their lives, return true
            if (lossCount >= 3) {
                badLaser.y = 700;
                player.y = 700;
                return true;
            }
        }
        return false;
    }

    public boolean win() {
        int checkWin = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                //if an enemy is dead, increase the checkWin integer
                if (enemyState[j][i] == false) {
                    checkWin++;
                }
            }
        }
        //if all the enemies are dead, the game is over and the player has won
        if (checkWin == 32) {
            game = false;
            return true;
        }
        //otherwise, the player has yet to win
        return false;
    }

    public boolean loss() {
        if (playerCollision()) {
            //if the playerCollision method is true, the game is over and the loss method is also true
            game = false;
            System.out.println("game = false");
            return true;
        }
        //if the lowest row of enemies gets too far down the screen, the player has lost
        if (enemy.y > 300) {
            for (int i = 0; i < 8; i++) {
                if (enemyState[i][3]) {
                    direction = 0;
                    down = 0;
                    game = false;
                    return true;
                }
            }
        }
        if (enemy.y > 340) {
            for (int i = 0; i < 8; i++) {
                if (enemyState[i][2]) {
                    direction = 0;
                    down = 0;
                    game = false;
                    return true;
                }
            }
        }
        if (enemy.y > 380) {
            for (int i = 0; i < 8; i++) {
                if (enemyState[i][1]) {
                    direction = 0;
                    down = 0;
                    game = false;
                    return true;
                }
            }
        }
        if (enemy.y > 420) {
            for (int i = 0; i < 8; i++) {
                if (enemyState[i][0]) {
                    direction = 0;
                    down = 0;
                    game = false;
                    return true;
                }
            }
        }
        //otherwise, return false
        return false;
    }

    public boolean cheat() {
        //once the enemies get far enough down the screen, the cheat method is true (used to display cheat hints)
        if (enemy.y > 100) {
            return true;
        }
        return false;
    }

    public void reset() {
        //reset all necessary variables
        points = 0;
        lossCount = 0;
        direction = 1;
        shootSpeed = 15;
        badShootSpeed = 10;
        enemy.x = 10;
        enemy.y = 10;
        player.x = 375;
        player.y = 560;
        laser.x = 398;
        laser.y = 560;
        shieldA.width = 60;
        shieldB.width = 60;
        shieldC.width = 60;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                enemyState[i][j] = true;
            }
        }
    }

    public void loop() {
        //call the methods above in a the loop when the game is true (player is playing the game)
        if (game) {
            moveEnemies();
            movePlayer();
            if (shoot) {
                shoot();
            }
            collision();
            enemyShoot();
            shieldCollision();
            cheat();
        }
    }

    // Used to implement any of the Mouse Actions
    private class Mouse extends MouseAdapter {

        // if a mouse button has been pressed down
        @Override
        public void mousePressed(MouseEvent e) {

        }

        // if a mouse button has been released
        @Override
        public void mouseReleased(MouseEvent e) {

        }

        // if the scroll wheel has been moved
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {

        }

        // if the mouse has moved positions
        @Override
        public void mouseMoved(MouseEvent e) {

        }
    }

    // Used to implements any of the Keyboard Actions
    private class Keyboard extends KeyAdapter {

        // if a key has been pressed down
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT) {
                left = true;
            } else if (key == KeyEvent.VK_RIGHT) {
                right = true;
            } else if (key == KeyEvent.VK_UP) {
                shoot = true;
                //System.out.println("play sound now ding dong");
                //plays the laser sound effect when the player is shooting
                //AudioPlayer.player.start(audios);
            } else if (key == KeyEvent.VK_Q) {
                System.out.println("superShoot");
                //when this key is pressed, superShoot boolean is true
                superShoot = true;
            } else if (key == KeyEvent.VK_W) {
                System.out.println("whole row");
                //when this key is pressed, wholeRow boolean is true
                wholeRow = true;
            } else if (key == KeyEvent.VK_B && player.x >= 90 && player.x <= 370 && enemy.y > 100) {
                System.out.println("y = " + enemy.y);
                //if this key is pressed and both the player and the enemies are in a certain position, bre boolean is true
                bre = true;
            } else if (key == KeyEvent.VK_F) {
                System.out.println("funnyStrategy");
                //when this key is pressed, funnyStrategy boolean is true
                funnyStrategy = true;
            } else if (key == KeyEvent.VK_R && player.x >= 710 && shieldC.width > 0) {
                System.out.println("restore");
                //when this key is pressed and the player is under the third shield area, the player will resotre their lives
                restore = true;
            } else if (key == KeyEvent.VK_1) {
                //starts game and calls reset function
                game = true;
                gameTimer.start();
                reset();
            } else if (key == KeyEvent.VK_2) {
                //starts game and calls reset function
                //increases enemy and bad laser speed from difficulty level one
                game = true;
                gameTimer.start();
                reset();
                direction = 2;
                badShootSpeed = 15;
            } else if (key == KeyEvent.VK_3) {
                //starts game and calls reset function
                //increases enemy and bad laser speed from difficulty level one and two
                game = true;
                gameTimer.start();
                reset();
                direction = 3;
                badShootSpeed = 20;
            }
        }

        // if a key has been released
        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            //set booleans to false when the keys that made them true, and released
            if (key == KeyEvent.VK_LEFT) {
                left = false;
            } else if (key == KeyEvent.VK_RIGHT) {
                right = false;
            } else if (key == KeyEvent.VK_Q) {
                superShoot = false;
                //AudioPlayer.player.stop(audios);
            } else if (key == KeyEvent.VK_W) {
                wholeRow = false;
            } else if (key == KeyEvent.VK_B) {
                bre = false;
            } else if (key == KeyEvent.VK_F) {
                funnyStrategy = false;
            } else if (key == KeyEvent.VK_R) {
                restore = false;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        loop();
        repaint();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // creates an instance of my game
        SpaceInvaders game = new SpaceInvaders();
    }
}