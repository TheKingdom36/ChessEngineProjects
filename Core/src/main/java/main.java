import ChessEngine.ChessBoardState;
import ChessEngine.Engine;
import GameBoard.ChessBoard.Enums.Color;
import GameBoard.ChessBoard.Models.ChessBoard;

import javax.sql.rowset.spi.SyncResolver;
import java.util.*;

public class main{

    static public void main(String[] args){

        Engine engine = new Engine();

        ChessBoard chessBoard = new ChessBoard();

        ChessBoardState chessBoardState = new ChessBoardState(chessBoard, Color.White);

        System.out.println("Hello");
        int[] arr = {1,3,3};
        int out = Arrays.stream(arr).max().getAsInt();
        Arrays.stream(arr).max();







    }
}