package MontoCarlo.interfaces;


import ChessBoard.Moves.ChessMove;
import MontoCarlo.GameState;
import MontoCarlo.MontoCarloTrainingOutput;


public interface IMontoCarloTree {
    ChessMove findNextMove(GameState gameState, long searchTime);

    MontoCarloTrainingOutput findNextMoveTraining(GameState gameState, long searchTime);
}
