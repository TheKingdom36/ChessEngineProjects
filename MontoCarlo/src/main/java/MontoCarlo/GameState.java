package MontoCarlo;


import GameBoard.Common.Interfaces.Board;
import GameBoard.Common.Interfaces.Move;
import Common.Plane;


import java.util.List;

public abstract class GameState {

    public abstract Plane[] convertToNeuralNetInput();

    public abstract <MoveType extends Move> List<MoveType> getAllAvailableMoves();

    public abstract GameState createNewState(Move move);

    public abstract GameState createNewState();

    public abstract int getCurrentPlayerID();

    public abstract Move getMove();

    public abstract int getMoveID();

    public abstract void nextPlayer();

    public abstract Board getBoard();

}
