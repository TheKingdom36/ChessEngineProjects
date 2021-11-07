import ChessEngine.ChessBoardState;
import ChessEngine.Engine;
import ChessEngine.Training.TrainChessNetwork;
import GameBoard.ChessBoard.Enums.Color;
import GameBoard.ChessBoard.Models.ChessBoard;
import NeuralNetwork.ActivationFunctions.ReLU;
import NeuralNetwork.LossFunctions.MSE;
import NeuralNetwork.Networks.ConvNetwork;
import NeuralNetwork.Operations.SoftmaxOp;
import NeuralNetwork.Utils.Dim3Struct;

public class main{

    static public void main(String[] args){

        Engine engine = new Engine();

        ChessBoard chessBoard = new ChessBoard();

        ChessBoardState chessBoardState = new ChessBoardState(chessBoard, Color.White);
//TODO
        ConvNetwork network= null;



        TrainChessNetwork chessNetwork = new TrainChessNetwork(engine,network);
    }
}