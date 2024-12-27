import java.awt.*;
import java.awt.geom.AffineTransform;

public class Stick {
    private Rectangle stick;
    private Rectangle tip;
    private double theta;
    private Shape rotatedShape;
    private Shape finalShape;
    private Shape rotatedTip;
    private Shape finalTip;
    private boolean rightGo = false;
    private boolean leftGo = false;
    private boolean upGo = false;
    private boolean downGo = false;
    private boolean angleGo = false;
    private boolean angleGoOpp = false;
    private boolean shootGo = false;
    private int count;
    private AffineTransform transformR = new AffineTransform();
    private AffineTransform transformT = new AffineTransform();

    public Stick(int xCor, int yCor, int width, int height, double theta){
        //set cue angle
        this.theta = Math.toRadians(theta);
        //create rectangle for the cue stick
        this.stick = new Rectangle(0,0,width,height);
        //create (differently coloured) rectangle for the tip of the cue
        this.tip = new Rectangle(0,0,20,10);
        //create rotation
        transformR.rotate(this.theta);
        //create translation
        transformT.translate(xCor, yCor);
        this.count = 0;
        //rotate and translate cue and tip
        this.rotatedShape = transformR.createTransformedShape(stick);
        this.finalShape = transformT.createTransformedShape(rotatedShape);
        //transformT.translate(xCor, yCor);
        this.rotatedTip = transformR.createTransformedShape(tip);
        this.finalTip = transformT.createTransformedShape(rotatedTip);
    }


    //draw cue
    public void draw(Graphics2D g){
        g.setColor(Color.WHITE);
        g.fill(this.finalShape);
        g.setColor(Color.BLACK);
        g.fill(this.finalTip);
    }
    
    //get value for theta
    public double getTheta(){
        return this.theta;
    }

    //check if tip intersects given values(cue ball position and size)
    public boolean collideBall(int x, int y, int width, int height){
        return finalTip.intersects(x,y,width,height);
    }

    public void update(){
        if(this.rightGo){
            //move cue right
            transformT.translate(1, 0);
            this.finalShape = transformT.createTransformedShape(rotatedShape);
            this.finalTip = transformT.createTransformedShape(rotatedTip); 
        }
        if(this.leftGo){
            //move cue left
            transformT.translate(-1, 0);
            this.finalShape = transformT.createTransformedShape(rotatedShape);
            this.finalTip = transformT.createTransformedShape(rotatedTip);
        }
        if(this.upGo){
            //move cue up
            transformT.translate(0, -1);
            this.finalShape = transformT.createTransformedShape(rotatedShape);
            this.finalTip = transformT.createTransformedShape(rotatedTip);
        }
        if(this.downGo){
            //move cue down
            transformT.translate(0, 1);
            this.finalShape = transformT.createTransformedShape(rotatedShape);
            this.finalTip = transformT.createTransformedShape(rotatedTip);
        }
        if(this.angleGo){
            //roate cue clockwise
            transformR.rotate(0.01);
            this.theta+=0.01;
            this.rotatedShape = transformR.createTransformedShape(stick);
            this.rotatedTip = transformR.createTransformedShape(tip);
            this.finalShape = transformT.createTransformedShape(rotatedShape);
            this.finalTip = transformT.createTransformedShape(rotatedTip);
        }
        if(this.angleGoOpp){
            //rotate cue counter-clockwise
            transformR.rotate(-0.01);
            this.theta-=0.01;
            this.rotatedShape = transformR.createTransformedShape(stick);
            this.rotatedTip = transformR.createTransformedShape(tip);
            this.finalShape = transformT.createTransformedShape(rotatedShape);
            this.finalTip = transformT.createTransformedShape(rotatedTip);
        }
        if(this.shootGo){
            shoot();
        }
    }

    
    public void shoot(){
        //move cue in direction of tip
        transformR.translate(-10, 0);
        count++;
        this.rotatedShape = transformR.createTransformedShape(stick);
        this.rotatedTip = transformR.createTransformedShape(tip);
        this.finalShape = transformT.createTransformedShape(rotatedShape);
        this.finalTip = transformT.createTransformedShape(rotatedTip);
        if(count>5){
            count=0;
            this.shootGo = false;
        }
        
        
    }


    public void setStickAngle(boolean value){
        this.angleGo = value;
    }

    public void setStickAngleOpp(boolean value){
        this.angleGoOpp = value;
    }
    
    public void setStickRight(boolean value){
        this.rightGo = value;
    }

    public void setStickLeft(boolean value){
        this.leftGo = value;
    }

    public void setStickUp(boolean value){
        this.upGo = value;
    }

    public void setStickDown(boolean value){
        this.downGo = value;
    }

    public void setShoot(boolean value){
        this.shootGo = value;
    }


    

  
}
