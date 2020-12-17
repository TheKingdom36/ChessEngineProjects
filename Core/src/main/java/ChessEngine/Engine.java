package ChessEngine;

import GameBoard.ChessBoard.Enums.Color;
import GameBoard.ChessBoard.Moves.ChessMove;
import MontoCarlo.MonteCarloTree;
import NeuralNet.NNExamples.CNN3LayerNet;
import NeuralNet.NNExamples.CNN3LayerNetWeights;
import NeuralNet.Interfaces.INeuralNetwork;
import ChessEngine.Util.*;


public class Engine {
    INeuralNetwork nn;
    MonteCarloTree mcst;

    public Engine(){
        nn = new CNN3LayerNet(new CNN3LayerNetWeights(true),AllPieceMoveOptions.getMoveOptions().size());
        mcst = new MonteCarloTree(nn);
    }

    public ChessMove FindBestMove(ChessBoardState chessBoardState){
        boolean isBlackMove=false;

        //input is always in perspective of the white player, moves are flipped for black player when outputting move
        if(chessBoardState.getPlayerColor() == Color.Black) {
            chessBoardState.setBoard(ConvertBoardToWhite.Convert(chessBoardState.getBoard()));
            isBlackMove = true;
        }

        ChessMove bestMove = (ChessMove) mcst.findNextMove(chessBoardState,10);


        if(isBlackMove == true){
            bestMove = ConvertMoveToBlack.Convert(bestMove);
        }

       return bestMove;


    }
/*
    public List<ChessMove> FindNextBestMoves(List<ChessBoardState> chessBoardStates){
        Plane[][] inputPlanes = new Plane[chessBoardStates.size()][21];

        for(int i = 0; i< chessBoardStates.size(); i++){

            inputPlanes[i]= ChessBoardToNNInputConverter.ConvertChessBoardToInput(chessBoardStates.get(i));
        }



    }*/
}
