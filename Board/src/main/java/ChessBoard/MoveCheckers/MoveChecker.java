package ChessBoard.MoveCheckers;





import ChessBoard.Models.BoardSquare;
import ChessBoard.Models.ChessPiece;
import ChessBoard.Models.Position;
import ChessBoard.Moves.ChessMove;

import java.util.List;

public abstract class MoveChecker {
    ChessPiece chessPiece;

    public MoveChecker(ChessPiece chessPiece){
        this.chessPiece = chessPiece;
    }

   public abstract List<ChessMove> checkAllAvailableMoves(Position piecePos, BoardSquare[][] board) ;
}
