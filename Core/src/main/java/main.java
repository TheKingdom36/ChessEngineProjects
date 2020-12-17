import ChessEngine.ChessBoardState;
import ChessEngine.Engine;
import GameBoard.ChessBoard.Enums.Color;
import GameBoard.ChessBoard.Models.ChessBoard;

public class main{

    static public void main(String[] args){

        Engine engine = new Engine();

        ChessBoard chessBoard = new ChessBoard();

        ChessBoardState chessBoardState = new ChessBoardState(chessBoard, Color.White);

        System.out.println("Hello");

    }
}