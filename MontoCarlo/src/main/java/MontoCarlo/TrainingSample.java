package MontoCarlo;

import ChessBoard.Enums.Color;
import ChessBoard.Models.ChessBoard;

import java.util.Arrays;

public class TrainingSample {
    private ChessBoard chessBoard;
    private double[] policy;
    private int value;
    private Color playerColor;

    public TrainingSample(ChessBoard chessBoard, double[] policy, int value, Color playerColor){
        this.chessBoard = chessBoard;
        this.policy= policy;
        this.value = value;
        this.playerColor = playerColor;
    }



    public TrainingSample(){

    }

    public ChessBoard getBoard() {
        return chessBoard;
    }

    public void setBoard(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }

    public double[] getPolicy() {
        return policy;
    }

    public void setPolicy(double[] policy) {
        this.policy = policy;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Color getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(Color playerColor) {
        this.playerColor = playerColor;
    }

    @Override
    public String toString() {
        return "MontoCarlo.TrainingSample{" +
                "board=" + chessBoard +
                ", policy=" + Arrays.toString(policy) +
                ", value=" + value +
                ", playerColor=" + playerColor +
                '}';
    }
}
