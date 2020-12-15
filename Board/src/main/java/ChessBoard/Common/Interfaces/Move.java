package ChessBoard.Common.Interfaces;

import ChessBoard.Moves.ChessMove;

public interface Move {

    @Override
    String toString();

    ChessMove Copy();
}
