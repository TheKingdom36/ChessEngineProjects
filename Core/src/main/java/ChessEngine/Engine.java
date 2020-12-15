package ChessEngine;

import ChessBoard.Enums.Color;
import ChessBoard.Moves.ChessMove;
import Common.Plane;
import MontoCarlo.MontoCarloTree;
import NeuralNet.Interfaces.INeuralNetwork;
import ChessEngine.Util.*;


import java.util.List;


public class Engine {
    INeuralNetwork nn;
    MontoCarloTree mcst;

    public ChessMove FindBestMove(ChessBoardState chessBoardState){

        if(chessBoardState.getPlayerColor() == Color.Black) {
            //input is always in perspective of the white player, moves are flipped for black player when outputting move
            chessBoardState.setBoard(ConvertBoardToWhite.Convert(chessBoardState.getBoard()));
        }


        Plane[] inputPlanes = ChessInputConverter.ConvertChessBoardToInput(chessBoardState);

        mcst.findNextMove();


    }

    public List<ChessMove> FindNextBestMoves(List<ChessBoardState> chessBoardStates){
        Plane[][] inputPlanes = new Plane[chessBoardStates.size()][21];

        for(int i = 0; i< chessBoardStates.size(); i++){

            inputPlanes[i]= ChessInputConverter.ConvertChessBoardToInput(chessBoardStates.get(i));
        }



    }
}
