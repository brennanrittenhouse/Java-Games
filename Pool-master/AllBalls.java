import java.awt.*;

public class AllBalls extends Ball{
    private Ball[] blues;
    private Ball[] reds;
    private Ball one;
    private Ball two;
    private Ball three;
    private Ball four;
    private Ball five;
    private Ball six;
    private Ball seven;
    private Ball eight;
    private Ball nine;
    private Ball ten;
    private Ball eleven;
    private Ball twelve;
    private Ball thirteen;
    private Ball fourteen;
    private Ball fifteen;
    private Ball collisionBall1;
    private Ball collisionBall2;
    private Rectangle[] collisionBoxes;
    private int redScore;
    private int blueScore;
    private boolean hitEdge = false;
    private boolean gameOver = false;
    private double newdirectionX = 0;
    private double newdirectionY = 0;
    private double colordirectionX1 = 0;
    private double colordirectionY1 = 0;
    private double colordirectionX2 = 0;
    private double colordirectionY2 = 0;
    private double newSpeed = 0;
    private double  colorSpeed1 = 0;
    private double colorSpeed2 = 0;


    public AllBalls(int x, int y, int width, int height, double directionX, double directionY, double speed){
        super(x, y, width, height, directionX, directionY, speed);
        //create rectangles to surround each ball
        collisionBoxes = new Rectangle[15];
        collisionBoxes[0] = new Rectangle(x+120,y,width,height);
        collisionBoxes[1] = new Rectangle(x+90, y+20, 20,20);
        collisionBoxes[2] = new Rectangle(x+90, y-20,20,20);
        collisionBoxes[3] = new Rectangle(x+60, y+40, 20, 20);
        collisionBoxes[4] = new Rectangle(x+60, y, 20, 20);
        collisionBoxes[5] = new Rectangle(x+60, y-40,20,20);
        collisionBoxes[6] = new Rectangle(x+30, y+60,20,20);
        collisionBoxes[7] = new Rectangle(x+30, y+20, 20,20);
        collisionBoxes[8] = new Rectangle(x+30, y-20, 20, 20);
        collisionBoxes[9] = new Rectangle(x+30, y-60, 20, 20);
        collisionBoxes[10] = new Rectangle(x, y+80, 20, 20);
        collisionBoxes[11] = new Rectangle(x, y+40, 20, 20);
        collisionBoxes[12] = new Rectangle(x, y, 20, 20);
        collisionBoxes[13] = new Rectangle(x, y-40, 20, 20);
        collisionBoxes[14] = new Rectangle(x, y-80, 20, 20);

        //create arrays of balls based on colour
        this.blues = new Ball[7];
        this.reds = new Ball[7];

        //give balls x and y values of their assigned collision box
        this.one = new Ball(collisionBoxes[0].x,collisionBoxes[0].y,width,height,0,0,0);
        this.two = new Ball(collisionBoxes[1].x,collisionBoxes[1].y,20,20,0,0,0);
        this.three = new Ball(collisionBoxes[2].x,collisionBoxes[2].y,20,20,0,0,0);
        this.four = new Ball(collisionBoxes[3].x,collisionBoxes[3].y,20,20,0,0,0);
        this.five = new Ball(collisionBoxes[4].x,collisionBoxes[4].y,20,20,0,0,0);
        this.six = new Ball(collisionBoxes[5].x,collisionBoxes[5].y,20,20,0,0,0);
        this.seven = new Ball(collisionBoxes[6].x,collisionBoxes[6].y,20,20,0,0,0);
        this.eight = new Ball(collisionBoxes[7].x,collisionBoxes[7].y,20,20,0,0,0);
        this.nine = new Ball(collisionBoxes[8].x,collisionBoxes[8].y,20,20,0,0,0);
        this.ten = new Ball(collisionBoxes[9].x,collisionBoxes[9].y,20,20,0,0,0);
        this.eleven = new Ball(collisionBoxes[10].x,collisionBoxes[10].y,20,20,0,0,0);
        this.twelve = new Ball(collisionBoxes[11].x,collisionBoxes[11].y,20,20,0,0,0);
        this.thirteen = new Ball(collisionBoxes[12].x,collisionBoxes[12].y,20,20,0,0,0);
        this.fourteen = new Ball(collisionBoxes[13].x,collisionBoxes[13].y,20,20,0,0,0);
        this.fifteen = new Ball(collisionBoxes[14].x,collisionBoxes[14].y,20,20,0,0,0);

        //put balls into colour arrays
        this.blues[0] = this.one;
        this.blues[1] = this.two;
        this.blues[2] = this.three;
        this.blues[3] = this.four;
        this.blues[4] = this.five;
        this.blues[5] = this.six;
        this.blues[6] = this.seven;

        this.reds[0] = this.nine;
        this.reds[1] = this.ten;
        this.reds[2] = this.eleven;
        this.reds[3] = this.twelve;
        this.reds[4] = this.thirteen;
        this.reds[5] = this.fourteen;
        this.reds[6] = this.fifteen;
    }

    public void colourBallsCollide(){
        for(int i = 0; i<collisionBoxes.length; i ++){
            for(int j=i+1; j<collisionBoxes.length; j++){ 
                //if two balls' collision boxes collide...  
                if(this.collisionBoxes[i].intersects(this.collisionBoxes[j])){
                    System.out.println("colours collide between " + i + " & " + j);
                    //set collisionBall1 and collisionBall2 based on which balls are collidng
                    if(i<=6){
                        this.collisionBall1 = this.blues[i];
                        }else if(i==7){
                        this.collisionBall1 = this.eight;
                        }else if(i>7){
                        this.collisionBall1 = this.reds[i-8];
                        }
                        if(j<=6){
                        this.collisionBall2 = blues[j];
                        frictionTwo(2, this.blues[j]);
                        }else if(j==7){
                        this.collisionBall2 = this.eight;
                            frictionTwo(2,this.eight);
                        }else if(j>7){
                        this.collisionBall2 = this.reds[j-8];
                            frictionTwo(2, this.reds[j-8]);
                        }
                        //get direction and speed of each ball involved in the collision
                        colordirectionX1 = this.collisionBall1.getdirectionX();
                        colordirectionY1 = this.collisionBall1.getdirectionY();
                        colordirectionX2 = this.collisionBall2.getdirectionX();
                        colordirectionY2 = this.collisionBall2.getdirectionY();
                        colorSpeed1 = this.collisionBall1.getSpeed();
                        colorSpeed2 = this.collisionBall2.getSpeed();
                        //swap the direction and speed of the balls
                        this.collisionBall1.setdirectionX(colordirectionX2);
                        this.collisionBall1.setdirectionY(colordirectionY2);
                        this.collisionBall2.setdirectionX(colordirectionX1);
                        this.collisionBall2.setdirectionY(colordirectionY1);
                        this.collisionBall1.setSpeed(colorSpeed2);
                        this.collisionBall2.setSpeed(colorSpeed1); 
                    
                }   
            }
        }
    }



    public void cueAndColourCollide(Ball cueball){
        for(int i = 0; i<collisionBoxes.length; i++){
            //if the cue ball collides with any coloured ball...
            if(cueball.checkCollisionOtherBall(collisionBoxes[i])){
               System.out.println("cue & " + i);
               //store the direction and speed of the cue ball
                newdirectionX = cueball.getdirectionX();
                newdirectionY = cueball.getdirectionY();
                newSpeed = cueball.getSpeed();
                //if the ball isn't hiting the wall set the direction and speed of coloured ball to be that of cue ball
                wallCollides(i);
                if(!hitEdge){
                    if(i<=6){
                        blues[i].setdirectionX(newdirectionX);
                        blues[i].setdirectionY(newdirectionY);
                        blues[i].setSpeed(newSpeed);
                     }else if(i==7){
                         this.eight.setdirectionX(newdirectionX);
                         this.eight.setdirectionY(newdirectionY);
                         eight.setSpeed(newSpeed);
                     }else if(i>7){
                         this.reds[i-8].setdirectionX(newdirectionX);
                         this.reds[i-8].setdirectionY(newdirectionY);
                         reds[i-8].setSpeed(newSpeed);
                     }
                     //cueball will stop moving
                     cueball.handleCollisionBalls();
                }
                
            }
        }
    }

    public void wallCollides(int z){
        //if a ball hits the wall, it will change direction
        if(this.collisionBoxes[z].y > 540 || collisionBoxes[z].y < 40){
            hitEdge = true;
            if(z<=6){
                this.blues[z].setdirectionY(this.blues[z].getdirectionY()*-1);
            }else if(z==7){
                this.eight.setdirectionY(this.eight.getdirectionY()*-1);
            }else if(z>7){
                this.reds[z-8].setdirectionY(this.reds[z-8].getdirectionY()*-1);
            }
            
        }
        if(this.collisionBoxes[z].x < 40 || this.collisionBoxes[z].x>740){
            hitEdge = true;
            if(z<=6){
                this.blues[z].setdirectionX(this.blues[z].getdirectionX()*-1);
            }else if(z==7){
                this.eight.setdirectionX(this.eight.getdirectionX()*-1);
            }else if(z>7){
                this.reds[z-8].setdirectionX(this.reds[z-8].getdirectionX()*-1);
            }
        }
    }

    //slows ball to a gradual stop
    public void frictionTwo(double friction, Ball ball){
        if(ball.getSpeed() <= 0){
            ball.setdirectionX(0);
            ball.setdirectionY(0);
            ball.setSpeed(0);
            hitEdge = false;
        }
        else{
            ball.setSpeed(ball.getSpeed()-friction);
        }
    }

    //if a ball lands in a corner pocket, return true;
    public boolean sankColour(Rectangle ballBox){
        if(ballBox.intersects(40,40,40,40) || ballBox.intersects(720,40,40,40) || ballBox.intersects(40,530,40,40) || ballBox.intersects(720,530,40,40)){
            return true;
        }return false;
    }

    //send the ball off the screen
    public void outerSpace(Rectangle beenSunk, Ball sanked){
        beenSunk.x = 100000;
        beenSunk.y = 100000;
        sanked.setballX(10000000);
        sanked.setballY(100000);
    }

    //update x and y coordinates based on direction and speed
    public void move(){
        for(int i =0; i<reds.length; i++){
            wallCollides(i+8);
                this.reds[i].setballX(reds[i].getballX() +reds[i].getdirectionX()*reds[i].getSpeed());
                this.reds[i].setballY(reds[i].getballY() +reds[i].getdirectionY()*reds[i].getSpeed());
                this.collisionBoxes[i+8].x = (int)this.reds[i].getballX();
                this.collisionBoxes[i+8].y = (int)this.reds[i].getballY();
                //slow ball
                frictionTwo(0.01, this.reds[i]);
                //update score if a ball lands in a pocket
                if(sankColour(collisionBoxes[i+8])){
                    redScore++;
                    //send ball off screen
                    outerSpace(collisionBoxes[i+8], reds[i]);
                }
        }
        for(int i =0; i<blues.length; i++){
           wallCollides(i);
                this.blues[i].setballX(blues[i].getballX() +blues[i].getdirectionX()*blues[i].getSpeed());
                this.blues[i].setballY(blues[i].getballY() +blues[i].getdirectionY()*blues[i].getSpeed());
                this.collisionBoxes[i].x = (int)this.blues[i].getballX();
                this.collisionBoxes[i].y = (int)this.blues[i].getballY();
                frictionTwo(0.01, this.blues[i]);
                if(sankColour(collisionBoxes[i])){
                    blueScore++;
                    outerSpace(collisionBoxes[i], blues[i]);
                }

        }
        wallCollides(7);
            this.eight.setballX(eight.getballX() +eight.getdirectionX()*eight.getSpeed());
            this.eight.setballY(eight.getballY() +eight.getdirectionY()*eight.getSpeed());
            this.collisionBoxes[7].x = (int)this.eight.getballX();
            this.collisionBoxes[7].y = (int)this.eight.getballY();
           frictionTwo(0.01, this.eight);
            if(sankColour(collisionBoxes[7])){
                //game ends if eight ball is sunk
                gameOver = true;
                outerSpace(collisionBoxes[7], eight);
            }
    }

    public boolean getGameOver(){
        return this.gameOver;
    }

    public int getRedScore(){
        return this.redScore;
    }
    public int getBlueScore(){
        return this.blueScore;
    }


    //draw balls and numbers
    public void draw(Graphics2D g){
        g.setColor(Color.BLUE);
        for(int i = 0; i<blues.length; i++){
            g.fillOval((int)this.blues[i].getballX(), (int)this.blues[i].getballY(), 20, 20);
            Font aFont = new Font("arial", Font.BOLD, 15);
            g.setFont(aFont);
            g.drawString(""+i, (int)this.blues[i].getballX(), (int)this.blues[i].getballY());
        }

        g.setColor(Color.BLACK);
        g.fillOval((int)eight.getballX(), (int)eight.getballY(), 20, 20);
        g.drawString(""+8, (int)this.eight.getballX(), (int)this.eight.getballY());

        for(int i = 0; i<reds.length; i++){
            g.setColor(Color.RED);
            g.fillOval((int)this.reds[i].getballX(), (int)this.reds[i].getballY(), 20, 20);
            g.setColor(Color.BLUE);
            g.drawString(""+i, (int)this.reds[i].getballX(), (int)this.reds[i].getballY());
        }  
    }
}
