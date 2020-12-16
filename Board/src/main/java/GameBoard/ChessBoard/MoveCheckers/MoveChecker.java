package GameBoard.ChessBoard.MoveCheckers;





import GameBoard.ChessBoard.Models.BoardSquare;
import GameBoard.ChessBoard.Models.ChessPiece;
import GameBoard.ChessBoard.Models.Position;
import GameBoard.ChessBoard.Moves.ChessMove;

import java.util.List;

public abstract class MoveChecker {
    ChessPiece chessPiece;

    public MoveChecker(ChessPiece chessPiece){
        this.chessPiece = chessPiece;
    }

   public abstract List<ChessMove> checkAllAvailableMoves(Position piecePos, BoardSquare[][] board) ;
}
