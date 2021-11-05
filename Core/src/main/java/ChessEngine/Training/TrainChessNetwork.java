package ChessEngine.Training;

import ChessEngine.ChessBoardState;
import ChessEngine.ChessPolicyPredictor;
import ChessEngine.Engine;
import ChessEngine.Util.ConvertBoardToWhite;
import ChessEngine.Util.ConvertMoveToBlack;
import GameBoard.ChessBoard.Enums.Color;
import GameBoard.ChessBoard.Models.ChessBoard;
import GameBoard.ChessBoard.Moves.ChessMove;
import NeuralNetwork.Block.ValuePolicyNetwork;
import NeuralNetwork.Utils.DataSet;
import NeuralNetwork.Utils.ValuePolicyDataSetRow;
import lombok.Getter;
import lombok.Setter;

import java.nio.file.Path;

public class TrainChessNetwork {

    @Getter @Setter
    private Engine<ChessMove,ChessBoardState> engine;

    @Getter @Setter
    private ValuePolicyNetwork network;

    private ChessBoard chessBoard;

    ChessPolicyPredictor predictor;

    ChessBoardState chessBoardState = new ChessBoardState(chessBoard, Color.White);

    DataSet<ValuePolicyDataSetRow> dataSet;

    public TrainChessNetwork(Engine engine, ValuePolicyNetwork networkToTrain){

        this.engine = engine;
        this.network = networkToTrain;
        chessBoard = new ChessBoard();

        predictor = new ChessPolicyPredictor(networkToTrain);

        engine.setPredictor(predictor);
    }


    public ValuePolicyNetwork train() throws IllegalAccessException, InstantiationException {
        //Create dataset by playing game
        ChessBoard board = new ChessBoard();
        Color playerColor;

        for(int i=0; i<50;i++){
            if(i%2==0){
                playerColor = Color.White;
            }else {
                playerColor = Color.Black;
            }

            ChessMove move = findBestMove(board,playerColor);

            board.updateBoard(move);
        }

        // train network
        network.learn(dataSet);

        //test network

        return network;
    }

    private void playGames(){

    }

    private void trainNetwork(){

    }

    private ChessMove findBestMove(ChessBoard chessBoard,Color color) throws InstantiationException, IllegalAccessException {
        boolean isBlackMove=false;
        //input is always in perspective of the white player, moves are flipped for black player when outputting move
        if(chessBoardState.getPlayerColor() == Color.Black) {
            chessBoardState.setBoard(ConvertBoardToWhite.Convert(chessBoardState.getBoard()));
            isBlackMove = true;
        }

        ChessBoardState state = new ChessBoardState(chessBoard,color);
        ChessMove bestMove = engine.findBestMove(state);

        if(isBlackMove == true){
            //Convert move back to black
            bestMove = ConvertMoveToBlack.Convert(bestMove);
        }

        return bestMove;
    }



    public String saveToFile(Path path){

        return "Not implemented";
    }
}
