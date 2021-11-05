package GameBoard.Common.Interfaces;

import GameBoard.ChessBoard.Enums.Color;
import GameBoard.ChessBoard.Models.ChessBoard;
import GameBoard.ChessBoard.Moves.ChessMove;
import GameBoard.Common.MoveLog;

import java.util.List;

public interface Board<GameMove> extends Comparable<Board> {
    MoveLog getMoveLog();

    void setMoveLog(MoveLog moveLog);

    List<ChessMove> getAllAvailableMoves(Color color);

    void undoMove(GameMove Move);

    void updateBoard(GameMove Move);

    void printBoard();

    void reset();

    void setup();

    ChessBoard copy();

    @Override
    int compareTo(Board otherBoard);
}
