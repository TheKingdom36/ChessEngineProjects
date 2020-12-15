package ChessBoard.MoveCheckers;



import ChessBoard.Models.BoardSquare;
import ChessBoard.Models.ChessPiece;
import ChessBoard.Models.Position;
import ChessBoard.Moves.ChessMove;
import ChessBoard.Moves.NormalChessMove;
import ChessBoard.Moves.TakeChessMove;
import ChessBoard.Util.Directions;

import java.util.ArrayList;
import java.util.List;

public class KnightMoveChecker extends MoveChecker {


    public KnightMoveChecker(ChessPiece chessPiece){
        super(chessPiece);
    }
    @Override
    public List<ChessMove> checkAllAvailableMoves(Position piecePos, BoardSquare[][] board) {
        List<ChessMove> chessMoves = new ArrayList<>();

        CheckIfValidMove(piecePos,new Position( (Directions.N).getX() + Directions.NE.getX(),(Directions.N).getY() + Directions.NE.getY()),board, chessMoves);
        CheckIfValidMove(piecePos,new Position( (Directions.N).getX() + Directions.NW.getX(),(Directions.N).getY() + Directions.NW.getY()),board, chessMoves);
        CheckIfValidMove(piecePos,new Position( (Directions.E).getX() + Directions.NE.getX(),(Directions.E).getY() + Directions.NE.getY()),board, chessMoves);
        CheckIfValidMove(piecePos,new Position( (Directions.E).getX() + Directions.SE.getX(),(Directions.E).getY() + Directions.SE.getY()),board, chessMoves);
        CheckIfValidMove(piecePos,new Position( (Directions.S).getX() + Directions.SE.getX(),(Directions.S).getY() + Directions.SE.getY()),board, chessMoves);
        CheckIfValidMove(piecePos,new Position( (Directions.S).getX() + Directions.SW.getX(),(Directions.S).getY() + Directions.SW.getY()),board, chessMoves);
        CheckIfValidMove(piecePos,new Position( (Directions.W).getX() + Directions.SW.getX(),(Directions.W).getY() + Directions.SW.getY()),board, chessMoves);
        CheckIfValidMove(piecePos,new Position( (Directions.W).getX() + Directions.NW.getX(),(Directions.W).getY() + Directions.NW.getY()),board, chessMoves);

        return chessMoves;
    }

    private void CheckIfValidMove(Position piecePos, Position direction, BoardSquare[][] board, List<ChessMove> chessMoves){
        Position SquarePosition = new Position(piecePos.getX()+direction.getX(),piecePos.getY()+direction.getY());
        if(SquarePosition.getX()<=7 && SquarePosition.getX()>=0&&SquarePosition.getY()<=7&&SquarePosition.getY()>=0){
            if(board[SquarePosition.getX()][SquarePosition.getY()].hasPiece() == true){
                if(board[SquarePosition.getX()][SquarePosition.getY()].getChessPiece().getColor() != chessPiece.getColor()){
                    chessMoves.add(new TakeChessMove(SquarePosition,piecePos, chessPiece,board[SquarePosition.getX()][SquarePosition.getY()].getChessPiece()));
                }
            }else {
                chessMoves.add(new NormalChessMove(SquarePosition,piecePos, chessPiece));
            }
        }
    }
}


