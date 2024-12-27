
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import javax.swing.Timer;

/**
 *
 * @author rittb0758
 */
public class Pong extends JComponent implements ActionListener {

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
    int Ballx = 350;
    int Directionx = 0;
    int Directiony = 0;
    int Bally = 250;
    int Paddle1y = 220;
    boolean p1Up = false;
    boolean p1Down = false;
    boolean p2Up = false;
    boolean p2Down = false;
    boolean game = false;
    int Paddle2y = 220;
    int points1 = 0;
    int points2 = 0;
    


    // GAME VARIABLES END HERE    

    
    // Constructor to create the Frame and place the panel in
    // You will learn more about this in Grade 12 :)
    public Pong(){
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
        gameTimer = new Timer(desiredTime,this);
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
        g.setColor(Color.MAGENTA);
        g.fillRect(0,0,WIDTH,HEIGHT);
        // GAME DRAWING GOES HERE
        g.setColor(Color.WHITE);
        g.fillRect(750, Paddle1y, 30, 100);
        g.fillRect(20, Paddle2y, 30, 100);
        g.fillOval(Ballx,Bally,50,50);
        Font aFont = new Font("arial", Font.BOLD, 25);
        g.setFont(aFont);
        g.drawString("" + points1, 365, 30);
        g.drawString(" - ", 375, 30);
        g.drawString("" + points2, 395, 30);
        Font bFont = new Font("arial", Font.BOLD, 15);
        g.setFont(bFont);
        g.drawString("Difficulty Level: 1{LEFT}  2{RIGHT}  3{ALT}",245,50);
        g.drawString("Reset{ENTER}", 340, 70);
        
        if(points1>=3){
           Font cFont = new Font("arial", Font.BOLD, 50);
           g.setFont(cFont);
           g.drawString("Player 1 Wins!!", 225, 300);
        }
        if(points2>=3){
           Font cFont = new Font("arial", Font.BOLD, 50);
           g.setFont(cFont);
           g.drawString("Player 2 Wins!!", 225, 300);
        }

        
        
        // GAME DRAWING ENDS HERE
    }

    // This method is used to do any pre-setup you might need to do
    // This is run before the game loop begins!
    public void setup() {
        // Any of your pre setup before the loop starts should go here

    }

    // The main game loop
    // In here is where all the logic for my game will go
    public void loop() {
        Ballx = Ballx + Directionx;
        Bally = Bally + Directiony;
        if(Bally>=(Paddle1y-50) && Bally<=(Paddle1y+100) && Ballx>=700 && Directionx>0){
            Directionx = Directionx*-1;
        }
        else if(Bally>=Paddle2y-50 && Bally<=Paddle2y+100 && Ballx<=50 && Directionx<0){
            Directionx = Directionx*-1;
        }
        else if(Ballx>WIDTH-50){
            Directionx = Directionx*-1;
            points1 = points1 + 1;
        }
        else if(Ballx<0){
            Directionx = Directionx*-1;
            points2 = points2 + 1;
        }
        else if(Bally>HEIGHT-50 || Bally<0){
            Directiony = Directiony*-1;
        }
        
        if(p1Up && Paddle1y>=0){
            Paddle1y = Paddle1y - 5;
        }
        else if(p1Down && Paddle1y<=(HEIGHT - 100)){
            Paddle1y = Paddle1y + 5;
        }
        if(p2Up && Paddle2y>=0){
            Paddle2y = Paddle2y - 5;
        }
        else if(p2Down && Paddle2y<=(HEIGHT - 100)){
            Paddle2y = Paddle2y + 5;
        }
        
        if(points1>=3 || points2>=3 && game==true){
            Ballx=350;
            Bally=325;
            Directionx=0;
            Directiony=0;
            Paddle1y=220;
            Paddle2y=220;
            game = false;
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
            if(key==KeyEvent.VK_LEFT && game==false){
                points1=0;
                points2=0;
                game = true;
                Directionx = 3;
                Directiony = 3;
                System.out.println(Ballx);
                System.out.println(Bally);
            }
            else if(key==KeyEvent.VK_RIGHT && game==false){
                points1=0;
                points2=0;
                game = true;
                Directionx = 5;
                Directiony = 5;
            }
            else if(key==KeyEvent.VK_ALT && game==false){
                points1=0;
                points2=0;
                game = true;
                Directionx = 7;
                Directiony = 7;
                System.out.println(Ballx);
                System.out.println(Bally);
            }
            if(key==KeyEvent.VK_ENTER){
                Ballx=350;
                Bally=250;
                Directionx=0;
                Directiony=0;
                points1=0;
                points2=0;
                Paddle1y=220;
                Paddle2y=220;
                game = false;
            }
            
            if(key==KeyEvent.VK_UP){
                p1Up = true;
            }
            else if(key==KeyEvent.VK_DOWN){
                p1Down = true;
            }
            
            else if(key==KeyEvent.VK_SHIFT){
                p2Up = true;
            }
            else if(key==KeyEvent.VK_CONTROL){
                p2Down = true;
            }
            

        }

        // if a key has been released
        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if(key==KeyEvent.VK_UP){
                p1Up = false;
            }
            else if(key==KeyEvent.VK_DOWN){
                p1Down = false;
            }
            else if(key==KeyEvent.VK_SHIFT){
                p2Up = false;
            }
            else if(key==KeyEvent.VK_CONTROL){
                p2Down = false;
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
        Pong game = new Pong();
    }
}





