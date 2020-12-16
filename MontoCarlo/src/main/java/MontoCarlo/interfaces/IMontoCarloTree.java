package MontoCarlo.interfaces;


import GameBoard.Common.Interfaces.Move;
import MontoCarlo.GameState;
import MontoCarlo.MonteCarloTrainingOutput;


public interface IMontoCarloTree {
    Move findNextMove(GameState gameState, long searchTime);

    MonteCarloTrainingOutput findNextMoveTraining(GameState gameState, long searchTime);
}
