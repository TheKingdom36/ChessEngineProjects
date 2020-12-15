package ChessBoard.Common.Interfaces;

import ChessBoard.Enums.Color;
import ChessBoard.Models.ChessBoard;
import ChessBoard.Moves.ChessMove;
import ChessBoard.Common.MoveLog;

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
