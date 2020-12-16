package GameBoard.Common.Interfaces;

import GameBoard.ChessBoard.Models.BoardSquare;
import GameBoard.ChessBoard.Models.Position;
import GameBoard.ChessBoard.Moves.ChessMove;

import java.util.List;

public interface Piece {
    List<ChessMove> GetAvailableMoves(Position piecePos, BoardSquare[][] board);
}
