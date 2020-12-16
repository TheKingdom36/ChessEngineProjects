package GameBoard.Common.Interfaces;

import GameBoard.ChessBoard.Moves.ChessMove;

public interface Move {

    @Override
    String toString();

    ChessMove Copy();
}
