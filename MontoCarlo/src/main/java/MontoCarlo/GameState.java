package MontoCarlo;


import GameBoard.Common.Interfaces.Board;
import GameBoard.Common.Interfaces.Move;
import Common.Plane;
import lombok.Getter;
import lombok.Setter;


import java.util.List;

public abstract class GameState<M extends Move>{
    /**
     * The move which created the current game state
     */
    @Getter @Setter
    private M move;

    @Getter @Setter
    private double isBestActionProbability;

    @Getter @Setter
    private double BestActionProbabilities;

    @Getter @Setter private double winScore;

    @Getter @Setter
    private int visitCount;

    public GameState(){
        visitCount=0;
        winScore=0;
        this.visitCount =0;
    }

    /**
     *
     * @return Returns all possible moves from the current game state
     */
    public abstract List<M> getAllAvailableMoves();

    public abstract GameState createNewState(M move);

    public abstract GameState createNewState();

    /**
     *
     * @return
     */
    public abstract int getCurrentPlayerID();

    public abstract int getMoveID();

    public abstract Board getBoard();

    public abstract void nextPlayer();

    protected abstract  GameState[] getAllPossibleStates(List<M> movesList);

    void incrementVisit() {
        this.visitCount++;
    }

    public void updateWinScore(double value){
        winScore += value;
    }


    /**
     * Returns an array of all possible gameStates from the current gameState
     * @return array of GameStates
     */
    public GameState[] getAllPossibleStates() {

        List<M> availableMoves = getAllAvailableMoves();

        GameState[] gameStates = getAllPossibleStates(availableMoves);

        return gameStates;
    }


}
