package ChessBoard.Common.Interfaces;

import ChessBoard.Models.BoardSquare;
import ChessBoard.Models.Position;
import ChessBoard.Moves.ChessMove;

import java.util.List;

public interface Piece {
    List<ChessMove> GetAvailableMoves(Position piecePos, BoardSquare[][] board);
}
