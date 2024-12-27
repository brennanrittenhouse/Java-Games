import java.awt.*;
public class Ball {
    private double ballY;
    private double ballX;
    private double directionY;
    private double directionX;
   private double speedy;
    private Rectangle collisionBox;
    private boolean collisionGo = false;
    private double angle;
    private boolean hitEdge = false;
    private boolean keyMoveBall;
    private boolean ballLeft;
    private boolean ballRight;
    private boolean ballUp;
    private boolean ballDown;

    public Ball(int x, int y, int width, int height, double directionX, double directionY, double speed){
        this.directionY = directionY;
        this.directionX = directionX;
        this.speedy = speed;
        ballX = x;
        ballY = y;
        this.collisionBox = new Rectangle((int)ballX, (int)ballY, width, height);
    }


    public void draw(Graphics2D g){
        g.setColor(Color.WHITE);
        g.fillOval((int)ballX, (int)ballY, 20, 20);
    }
    


    //change ball direction if it hits the wall
    public void wallCollide(){
        if(this.collisionBox.y > 540 || collisionBox.y < 40){
           // System.out.println("hit the edge");
            hitEdge = true;
            this.directionY = this.directionY*-1;
            
        }
        if(this.collisionBox.x < 40 || this.collisionBox.x>740){
            //System.out.println("hit the edge");
            hitEdge = true;
            this.directionX = this.directionX*-1;
        }
    }

    //does the cue hit the cue ball?
    public void handleCollision(Stick stick){
        if(stick.collideBall(collisionBox.x, collisionBox.y, 20, 20)){
            this.collisionGo = true;
        }
    }

  
    //gradually slow and then stop the ball
    public void friction(double friction){
        if(getSpeed() <=0){
            setSpeed(0);
            collisionGo = false;
        }
        else{
            setSpeed(getSpeed()-friction);

        }
        
    }

   //allow cue ball to be reset if it lands in one of the pockets
    public boolean checkCollisionHole(Rectangle hole){
        if(this.collisionBox.intersects(hole)){
            resetBall();
            keyMoveBall = true;
            return true;
        }return false;
    }


    //check is cue ball collides with any other ball
    public boolean checkCollisionOtherBall(Rectangle ballCollisionBox){
        if(this.collisionBox.intersects(ballCollisionBox)){
            return true;
        }return false;
    }

    //stop cue ball once it collides with another ball
    public void handleCollisionBalls(){
       setSpeed(0);
    }


    //getters and setters
    public double getballX(){
        return ballX;
    }
    public double getSpeed(){
        return speedy;
    }
    public void setSpeed(double fast){
        speedy = fast;
    }
    public double getballY(){
        return ballY;
    }
    public void setballX(double x){
        ballX = x;
    }
    public void setballY(double y){
        ballY = y;
    }
    public double getdirectionX(){
        return directionX;
    }
    public double getdirectionY(){
        return directionY;
    }
    public void setdirectionX(double newdirectionX){
        directionX = newdirectionX;
    }
    public void setdirectionY(double newdirectionY){
        directionY = newdirectionY;
    }
    public void getAngle(Stick stick){
        this.angle = stick.getTheta();
    }




   

    public void resetBall(){
        this.ballX = 350;
        this.ballY = 20;
        this.speedy = 0;
        this.directionX = 0;
        this.directionY = 0;
        this.collisionBox.x = 100;
        this.collisionBox.y = 300;
    }
   
    //setters for moving ball
    public void setBallUp(boolean value){
        this.ballUp = value;
    }
    public void setBallDown(boolean value){
        this.ballDown = value;
    }
    public void setBallLeft(boolean value){
        this.ballLeft = value;
    }
    public void setBallRight(boolean value){
        this.ballRight = value;
    }
    public void setKeyMoveBall(boolean value){
        this.keyMoveBall = value;
    }


    public void update(){
        //is it hitting the wall?
        wallCollide();
        //if the ball isn't hiting the wall, set the directions with same angle as cue stick
        if(!hitEdge){
            directionX = Math.cos(angle+Math.PI);
            directionY = Math.sin(angle+Math.PI);
        }

      
        //if stick hits cue ball...
        if(collisionGo){
            //update x and y positions
            this.ballX += this.directionX*speedy;
            this.ballY += this.directionY*speedy;
            this.collisionBox.x = (int)this.ballX;
            this.collisionBox.y = (int)this.ballY;
            //slow ball
          friction(0.0001);
            
        }
        if(!collisionGo){
            //reset speed
            this.speedy = 8;
            hitEdge = false;
        }

        //change ball x and y if the up,down,right,left variables are true(these will be set in by key presses in main)
        if(keyMoveBall){
            if(this.ballUp){
                this.ballY--;
                this.collisionBox.y--;
            }
            if(this.ballDown){
                this.ballY++;
                this.collisionBox.y++;
            }
            if(this.ballLeft){
                this.ballX--;
                this.collisionBox.x--;
            }
            if(this.ballRight){
                this.ballX++;
                this.collisionBox.x++;
            }
        }
        

            
        
       
    }

}