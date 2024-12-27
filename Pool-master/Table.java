import java.awt.*;
public class Table {
    private Rectangle base;
    private Rectangle green;
    private Rectangle[] holes;



    public Table(){
        base = new Rectangle(5,5,790,600);
        green = new Rectangle(40,40,720,530);
        //this.holes is an array of rectangles representing the four corner pockets
        this.holes = new Rectangle[4];
        holes[0] = new Rectangle(40,40,40,40);
        holes[1] = new Rectangle(720,40,40,40);
        holes[2] = new Rectangle(40,530,40,40);
        holes[3] = new Rectangle(720,530,40,40);


    }

    public void draw(Graphics2D g){
        g.setColor(Color.BLUE);
        g.fill(base);
        g.setColor(Color.GREEN);
        g.fill(green);
        g.setColor(Color.BLACK);
        for(Rectangle r: this.holes){
            g.fill(r);
        }
    }


    //check collision between cue ball and each pocket in hole array
    public boolean sunk(Ball ball){
        for(Rectangle r: this.holes){
            if(ball.checkCollisionHole(r)){
                //bring ball to top of screen so it can be placed by player
                ball.resetBall();
                return true;
                
            }
        }return false;
    }



}
