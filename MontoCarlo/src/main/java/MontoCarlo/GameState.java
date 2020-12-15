package MontoCarlo;


import ChessBoard.Common.Interfaces.Move;
import Common.Plane;

import java.util.List;

public abstract class GameState {


    public abstract Plane[][] ConvertToNeuralNetInput();

    public abstract <MoveType extends Move> List<MoveType> getAllAvailableMoves();

    public abstract GameState CreateNewState(Move move);

    public abstract GameState CreateNewState();

    public abstract void NextPlayer();

}
