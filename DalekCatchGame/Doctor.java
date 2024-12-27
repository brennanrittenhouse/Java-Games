
/** This class models the Doctor in the game. A Doctor has
 *  a position and can move to a new position.
 */
public class Doctor {

    private int row, col;
    private boolean hasDied;
    private boolean hasWon;


    /**
     * Initializes the variables for a Doctor.
     *
     * @param theRow The row this Doctor starts at.
     * @param theCol The column this Doctor starts at.
     */
    public Doctor(int row, int col) {
        this.row = row;
        this.col = col;
        this.hasDied = false;
        this.hasWon = false;
    }

    /**
     * Move the Doctor. If the player clicks on one of the squares immediately
     * surrounding the Doctor, the peg is moved to that location. Clicking on
     * the Doctor does not move the peg, but instead allows the Doctor to wait
     * in place for a turn. Clicking on any other square causes the Doctor to
     * teleport to a random square (perhaps by using a �sonic screwdriver�).
     * Teleportation is completely random.
     *
     * @param newRow The row the player clicked on.
     * @param newCol The column the player clicked on.
     */
    public void move(int newRow, int newCol) {
        if(newRow == this.row - 1 || newRow == this.row + 1 && ((newCol == this.col|| newCol == this.col+1 || newCol == this.col-1))){
                this.row = newRow;
                this.col = newCol;
        }else if(newRow == this.row && (newCol == this.col+1 || newCol == this.col-1)){
                this.row = newRow;
                this.col = newCol;
        }else if(newRow == this.row && newCol == this.col){
            this.row = newRow;
            this.col = newCol;
        }else{
            int min = 0;
            int max = 11;
            int randRow = (int)(Math.random()*(max-min+1)+min);
            int randCol = (int)(Math.random()*(max-min+1)+min);
            this.row = randRow;
            this.col = randCol;
        }
        
        

    }

    /**
     * Returns the row of this Doctor.
     *
     * @return This Doctor's row.
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Returns the column of this Doctor.
     *
     * @return This Doctor's column.
     */
    public int getCol() {
        return this.col;
    }

    public void dead() {
        this.hasDied = true;
    }

    public boolean hasDied() {
        return this.hasDied;
    }

    public void youWon(){
        this.hasWon = true;
    }

    public boolean hasWon(){
        return this.hasWon;
    }


}
