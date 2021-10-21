package MontoCarlo;


import GameBoard.Common.Interfaces.Board;
import GameBoard.Common.Interfaces.Move;
import Common.Plane;
import lombok.Getter;
import lombok.Setter;


import java.util.List;

public abstract class GameState  extends NodeState{

    @Getter
    @Setter
    private Move move;

    public abstract Plane[] convertToNeuralNetInput();


    public abstract <MoveType extends Move> List<MoveType> getAllAvailableMoves();

    public abstract GameState createNewState(Move move);

    public abstract GameState createNewState();

    public abstract int getCurrentPlayerID();

    public abstract Move getMove();

    public abstract int getMoveID();

    public abstract void nextPlayer();

    public abstract Board getBoard();

    protected abstract  <State extends NodeState> State[] getAllPossibleStates(List<Move> movesList);

    @Override
    public <State extends NodeState> State[] getAllPossibleStates() {

        List<Move> availableMoves = getAllAvailableMoves();

        State[] nodeStates = getAllPossibleStates(availableMoves);

        for(NodeState nodeState : nodeStates){
            nodeState.setIsActive(false);
        }

        return nodeStates;
    }


}
