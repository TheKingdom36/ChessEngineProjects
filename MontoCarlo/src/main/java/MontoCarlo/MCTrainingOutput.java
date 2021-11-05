package MontoCarlo;


import GameBoard.Common.Interfaces.Board;
import GameBoard.Common.Interfaces.Move;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MCTrainingOutput {

    private Board board;
    private double[] policy;
    private int value;
    private int playerID;
   private Move nextMove;

   public MCTrainingOutput(){

   }

    public MCTrainingOutput(Board board,Move nextMove,double[] policy,int value,int playerID){
        this.board = board;
        this.policy= policy;
        this.value = value;
        this.playerID = playerID;
        this.nextMove = nextMove;
    }

    public Move getNextMove() {
        return nextMove;
    }

    public void setNextMove(Move nextChessMove) {
        this.nextMove = nextChessMove;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public double[] getPolicy() {
        return policy;
    }

    public void setPolicy(double[] policy) {
        this.policy = policy;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }


}
