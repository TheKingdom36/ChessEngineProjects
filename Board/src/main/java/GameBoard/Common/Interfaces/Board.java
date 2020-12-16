package GameBoard.Common.Interfaces;

import GameBoard.ChessBoard.Enums.Color;
import GameBoard.ChessBoard.Models.ChessBoard;
import GameBoard.ChessBoard.Moves.ChessMove;
import GameBoard.Common.MoveLog;

import java.util.List;

public interface Board<GameMove> extends Comparable<Board> {
    MoveLog getMoveLog();

    void setMoveLog(MoveLog moveLog);

    List<ChessMove> GetAllAvailableMoves(Color color);

    void UndoMove(GameMove Move);

    void UpdateBoard(GameMove Move);

    void PrintBoard();

    void Reset();

    void Setup();

    ChessBoard Copy();

    @Override
    int compareTo(Board otherBoard);
}
