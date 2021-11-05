package ChessEngine;

import GameBoard.Common.Interfaces.Move;
import MontoCarlo.GameState;
import MontoCarlo.MCTrainingOutput;
import MontoCarlo.MonteCarloTree;
import MontoCarlo.PolicyPredictor;
import lombok.Getter;
import lombok.Setter;


public class Engine<M extends Move,GState extends GameState> {

    MonteCarloTree monteCarloTree;
    @Getter @Setter
    PolicyPredictor predictor;

    public Engine(PolicyPredictor predictor){
        this.predictor = predictor;
        this.monteCarloTree = new MonteCarloTree(predictor);
    }

    public M findBestMove(GState gameState) throws IllegalAccessException, InstantiationException{
        Move bestMove = monteCarloTree.findNextMove(gameState,10);
        return (M)bestMove;
    }

    public MCTrainingOutput findBestMoveTraining(GState gameState) throws IllegalAccessException, InstantiationException {
        MCTrainingOutput  output= monteCarloTree.findNextMoveTraining(gameState,1000);

        return output;
    }


/*
    public List<ChessMove> FindNextBestMoves(List<ChessBoardState> chessBoardStates){
        Plane[][] inputPlanes = new Plane[chessBoardStates.size()][21];

        for(int i = 0; i< chessBoardStates.size(); i++){

            inputPlanes[i]= ChessBoardToNNInputConverter.ConvertChessBoardToInput(chessBoardStates.get(i));
        }



    }*/
}
