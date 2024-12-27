
/** This class manages the interactions between the different pieces of
 *  the game: the Board, the Daleks, and the Doctor. It determines when
 *  the game is over and whether the Doctor won or lost.
 */
public class CatchGame {

    /**
     * Instance variables go up here
     * Make sure to create a Board, 3 Daleks, and a Doctor
     */
    private Board board;
    private Dalek dalek1;
    private Dalek dalek2;
    private Dalek dalek3;
    private Doctor doctor;



    /**
     * The constructor for the game.
     * Use it to initialize your game variables.
     * (create people, set positions, etc.)
     */
    public CatchGame(){
        this.board = new Board(12,12);
        int min = 0;
        int max = 11;
        int randRow = (int)(Math.random()*(max-min+1)+min);
        int randCol = (int)(Math.random()*(max-min+1)+min);
        this.dalek1 = new Dalek(randRow, randCol);
        do{
        randRow = (int)(Math.random()*(max-min+1)+min);
        randCol = (int)(Math.random()*(max-min+1)+min);
        this.dalek2 = new Dalek(randRow, randCol);
        }while(dalek1.getRow() == dalek2.getRow() && dalek1.getCol() == dalek2.getCol());
        do{
            randRow = (int)(Math.random()*(max-min+1)+min);
            randCol = (int)(Math.random()*(max-min+1)+min);
            this.dalek3 = new Dalek(randRow, randCol);
            }while(dalek1.getRow() == dalek3.getRow() && dalek1.getCol() == dalek3.getCol() || (dalek2.getRow() == dalek3.getRow() && dalek2.getCol() == dalek3.getCol() ));
        do{
            randRow = (int)(Math.random()*(max-min+1)+min);
            randCol = (int)(Math.random()*(max-min+1)+min);
            this.doctor = new Doctor(randRow,randCol);
        }while(doctor.getRow() == dalek1.getRow() && doctor.getCol() == dalek1.getCol() || (doctor.getRow() == dalek2.getRow() && doctor.getCol() == dalek2.getCol()) || (doctor.getRow() == dalek3.getRow() && doctor.getCol() == dalek3.getCol()));
        
            
        
        this.board.putPeg(Board.BLACK, this.dalek1.getRow(), this.dalek1.getCol());
        this.board.putPeg(Board.BLACK, this.dalek2.getRow(), this.dalek2.getCol());
        this.board.putPeg(Board.BLACK, this.dalek3.getRow(), this.dalek3.getCol());
        this.board.putPeg(Board.GREEN, this.doctor.getRow(), this.doctor.getCol());
    }

    /**
     * The playGame method begins and controls a game: deals with when the user
     * selects a square, when the Daleks move, when the game is won/lost.
     */
    public void playGame() {
        //!(doctor.getRow() == dalek1.getRow() && doctor.getCol() == dalek1.getCol()|| (doctor.getRow() == dalek2.getRow() && doctor.getCol() == dalek2.getCol()) || (doctor.getRow() == dalek3.getRow() && doctor.getCol() == dalek3.getCol()))
        while(doctor.hasWon() == false && doctor.hasDied() == false){
        Coordinate click = this.board.getClick();
        this.board.removePeg(this.doctor.getRow(), this.doctor.getCol());
        doctor.move(click.getRow(), click.getCol());
        System.out.println(click.getRow());
        System.out.println(click.getCol());
        this.board.putPeg(Board.GREEN, this.doctor.getRow(), this.doctor.getCol());
        this.board.removePeg(this.dalek1.getRow(), this.dalek1.getCol());
        this.board.removePeg(this.dalek2.getRow(), this.dalek2.getCol());
        this.board.removePeg(this.dalek3.getRow(), this.dalek3.getCol());
    if(!dalek1.hasCrashed()){
        dalek1.advanceTowards(this.doctor);
    }
    if(!dalek2.hasCrashed()){
        dalek2.advanceTowards(this.doctor);
    }
    if(!dalek3.hasCrashed()){
        dalek3.advanceTowards(this.doctor);
    }
        if(doctor.getRow() == dalek1.getRow() && doctor.getCol() == dalek1.getCol()|| (doctor.getRow() == dalek2.getRow() && doctor.getCol() == dalek2.getCol()) || (doctor.getRow() == dalek3.getRow() && doctor.getCol() == dalek3.getCol())){
            System.out.println("dead");
            this.board.removePeg(this.dalek1.getRow(), this.dalek1.getCol());
            this.board.removePeg(this.dalek2.getRow(), this.dalek2.getCol());
            this.board.removePeg(this.dalek3.getRow(), this.dalek3.getCol());
            this.board.putPeg(Board.YELLOW, this.doctor.getRow(), this.doctor.getCol());
            doctor.dead();
        }
        else if(dalek1.getRow() == dalek2.getRow() && dalek1.getCol() == dalek2.getCol()){
            this.board.putPeg(Board.RED, this.dalek1.getRow(), this.dalek1.getCol());
            dalek1.crash();
            dalek2.crash();
            if(dalek1.getRow() == dalek3.getRow() && dalek1.getCol() == dalek3.getCol()){
                this.board.putPeg(Board.RED, this.dalek3.getRow(), this.dalek3.getCol());
                doctor.youWon();
                this.board.displayMessage("YOU WON!!!!");
            }else{
                this.board.putPeg(Board.BLACK, this.dalek3.getRow(), this.dalek3.getCol());
                System.out.println("1&2 crash");
            }
        }else if((dalek1.getRow() == dalek3.getRow() && dalek1.getCol() == dalek3.getCol())){
            this.board.putPeg(Board.RED, this.dalek1.getRow(), this.dalek1.getCol());
            dalek1.crash();
            dalek3.crash();
            if(dalek1.getRow() == dalek2.getRow() && dalek1.getCol() == dalek2.getCol()){
                this.board.putPeg(Board.RED, this.dalek2.getRow(), this.dalek2.getCol());
                doctor.youWon();
                this.board.displayMessage("YOU WON!!!!");
            }else{
                this.board.putPeg(Board.BLACK, this.dalek2.getRow(), this.dalek2.getCol());
                System.out.println("1&3 crash");
            }
        }else if(dalek2.getRow() == dalek3.getRow() && dalek2.getCol() == dalek3.getCol()){
            this.board.putPeg(Board.RED, this.dalek2.getRow(), this.dalek2.getCol());
            dalek2.crash();
            dalek3.crash();
            if(dalek1.getRow() == dalek2.getRow() && dalek1.getCol() == dalek2.getCol()){
                this.board.putPeg(Board.RED, this.dalek1.getRow(), this.dalek1.getCol());
                doctor.youWon();
                this.board.displayMessage("YOU WON!!!!");
            }else{
                this.board.putPeg(Board.BLACK, this.dalek2.getRow(), this.dalek2.getCol());
                System.out.println("2&3 crash");
            }
        }else{
            this.board.putPeg(Board.BLACK, this.dalek1.getRow(), this.dalek1.getCol());
            this.board.putPeg(Board.BLACK, this.dalek2.getRow(), this.dalek2.getCol());
            this.board.putPeg(Board.BLACK, this.dalek3.getRow(), this.dalek3.getCol());
        }
        }
        

        
        //this.board.putPeg(Board.GREEN,click.getRow(),click.getCol());
        //this.board.removePeg(2,4);
        
        /*
        this.board.putPeg(Board.BLACK, 2, 4);
        while(true){
            Coordinate click = this.board.getClick();
            this.board.putPeg(Board.RED,click.getRow(),click.getCol());
            this.board.removePeg(2,4);
            this.board.displayMessage("Hello");
        */
        }
    
    }


