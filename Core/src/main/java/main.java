import ChessEngine.ChessBoardState;
import ChessEngine.Engine;
import ChessEngine.Training.TrainChessNetwork;
import GameBoard.ChessBoard.Enums.Color;
import GameBoard.ChessBoard.Models.ChessBoard;
import NeuralNetwork.ActivationFunctions.ReLU;
import NeuralNetwork.Block.ValuePolicyNetwork;
import NeuralNetwork.LossFunctions.MSE;
import NeuralNetwork.Operations.SoftmaxOp;
import NeuralNetwork.Utils.Dim3Struct;

public class main{

    static public void main(String[] args){

        Engine engine = new Engine();

        ChessBoard chessBoard = new ChessBoard();

        ChessBoardState chessBoardState = new ChessBoardState(chessBoard, Color.White);

        ValuePolicyNNBuilder builder = new ValuePolicyNNBuilder();
        int numOfNeurons = 128;

        ValuePolicyNetwork network = builder.addInputBlock(new Dim3Struct.Dims(8,8,21))
                .addConvBlock(1,0,3,3,3,new ReLU())
                .addFullyConnectedBlock(numOfNeurons,new ReLU())
                .addPolicyOutputBlock(10,new MSE()).withPostOperation(new SoftmaxOp())
                .build();



        TrainChessNetwork chessNetwork = new TrainChessNetwork(engine,network);
    }
}