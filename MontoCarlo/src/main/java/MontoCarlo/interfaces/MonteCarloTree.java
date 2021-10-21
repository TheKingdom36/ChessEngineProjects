package MontoCarlo.interfaces;


import GameBoard.Common.Interfaces.Move;
import MontoCarlo.GameState;
import MontoCarlo.MonteCarloTrainingOutput;


public interface MonteCarloTree {
    Move findNextMove(GameState gameState, long searchTime) throws InstantiationException, IllegalAccessException;

    MonteCarloTrainingOutput findNextMoveTraining(GameState gameState, long searchTime) throws InstantiationException, IllegalAccessException;
}
