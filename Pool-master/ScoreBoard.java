import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class ScoreBoard {
    private int blueScore;
    private int redScore;
    private String potentialEnd = "";

    public ScoreBoard(int blueScore, int redScore){
        this.blueScore = blueScore;
        this.redScore = redScore;
    }

    public void draw(Graphics2D g){
        g.setColor(Color.BLACK);
        Font aFont = new Font("arial", Font.BOLD, 25);
        g.setFont(aFont);
        g.drawString("Red Balls Sunk:", 40, 30);
        g.drawString("Blue Balls Sunk:", 530, 30);
        g.drawString("" + redScore, 230, 30);
        g.drawString("" + blueScore, 730, 30);
        g.drawString(potentialEnd, 320, 200);

    }

    //update score based on collision between balls and pockets (found in the AllBalls class)
    public void changeScore(AllBalls colours){
        this.blueScore = colours.getBlueScore();
        this.redScore = colours.getRedScore();
        if(colours.getGameOver()){
            potentialEnd = ("GAME OVER!!!!!");
        }else if(this.redScore>=7){
            potentialEnd = ("RED WINS!!");
        }else if(this.blueScore>=7){
            potentialEnd = ("BLUE WINS!!");
        }
    }
    
}
