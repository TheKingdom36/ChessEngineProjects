package GameBoard.ChessBoard.MoveCheckers;



import GameBoard.ChessBoard.Models.BoardSquare;
import GameBoard.ChessBoard.Models.ChessPiece;
import GameBoard.ChessBoard.Models.Position;
import GameBoard.ChessBoard.Moves.ChessMove;
import GameBoard.ChessBoard.Moves.NormalChessMove;
import GameBoard.ChessBoard.Moves.TakeChessMove;
import GameBoard.ChessBoard.Util.Directions;

import java.util.ArrayList;
import java.util.List;

public class KingMoveChecker extends MoveChecker {

    public KingMoveChecker(ChessPiece chessPiece) {
        super(chessPiece);
    }

    @Override
    public List<ChessMove> checkAllAvailableMoves(Position piecePos, BoardSquare[][] board) {
        List<ChessMove> chessMoves = new ArrayList<>();
        checkMovesInDirection(piecePos, board, Directions.N, chessMoves);
        checkMovesInDirection(piecePos, board, Directions.NE, chessMoves);
        checkMovesInDirection(piecePos, board, Directions.E, chessMoves);
        checkMovesInDirection(piecePos, board, Directions.SE, chessMoves);
        checkMovesInDirection(piecePos, board, Directions.S, chessMoves);
        checkMovesInDirection(piecePos, board, Directions.SW, chessMoves);
        checkMovesInDirection(piecePos, board, Directions.W, chessMoves);
        checkMovesInDirection(piecePos, board, Directions.NW, chessMoves);

        return chessMoves;
    }

    private void checkMovesInDirection(Position piecePos, BoardSquare[][] board, Position direction, List<ChessMove> chessMoves) {

        int XPosToCheck = piecePos.getX() + direction.getX();
        int YPosToCheck = piecePos.getY() + direction.getY();


        if (SquareSaftyCheck.isSquareSafe(new Position(XPosToCheck, YPosToCheck), chessPiece.getColor(), board) == true) {


            if (XPosToCheck >= 0 && XPosToCheck <= 7 && YPosToCheck <= 7 && YPosToCheck >= 0) {
                if (board[XPosToCheck][YPosToCheck].hasPiece() == false) {
                    chessMoves.add(new NormalChessMove(new Position(XPosToCheck, YPosToCheck), piecePos, chessPiece));
                } else {
                    if (board[XPosToCheck][YPosToCheck].getChessPiece().getColor() != chessPiece.getColor()) {
                        chessMoves.add(new TakeChessMove(new Position(XPosToCheck, YPosToCheck), piecePos, chessPiece, board[XPosToCheck][YPosToCheck].getChessPiece()));
                    }
                }
            }
        }
    }

}
