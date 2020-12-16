package MontoCarlo;

import GameBoard.ChessBoard.Enums.Color;
import GameBoard.ChessBoard.Models.ChessBoard;
import GameBoard.Common.Interfaces.Board;

import java.util.Arrays;

public class TrainingSample {
    private Board board;
    private double[] policy;
    private int value;
    private String playerID;

    public TrainingSample(Board board, double[] policy, int value, String playerID){
        this.board = board;
        this.policy= policy;
        this.value = value;
        this.playerID = playerID;
    }



    public TrainingSample(){

    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
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

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    @Override
    public String toString() {
        return "MontoCarlo.TrainingSample{" +
                "board=" + board +
                ", policy=" + Arrays.toString(policy) +
                ", value=" + value +
                ", playerColor=" + playerID +
                '}';
    }
}
