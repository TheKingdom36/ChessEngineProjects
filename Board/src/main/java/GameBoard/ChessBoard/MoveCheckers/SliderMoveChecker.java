package GameBoard.ChessBoard.MoveCheckers;


import GameBoard.ChessBoard.Models.BoardSquare;
import GameBoard.ChessBoard.Models.ChessPiece;
import GameBoard.ChessBoard.Models.Position;
import GameBoard.ChessBoard.Moves.ChessMove;
import GameBoard.ChessBoard.Moves.NormalChessMove;
import GameBoard.ChessBoard.Moves.TakeChessMove;
import GameBoard.ChessBoard.Util.ChessPosStore;
import GameBoard.ChessBoard.Util.Directions;

import java.util.ArrayList;
import java.util.List;

public class SliderMoveChecker extends MoveChecker {
    boolean canMoveDiagonal;
    boolean canMoveVertOrHor;

    public SliderMoveChecker(ChessPiece chessPiece, boolean canMoveDiagonal, boolean canMoveVertOrHor){
        super(chessPiece);

        this.canMoveDiagonal = canMoveDiagonal;
        this.canMoveVertOrHor = canMoveVertOrHor;

    }


    @Override
    public List<ChessMove> checkAllAvailableMoves(Position piecePos, BoardSquare[][] board) {
        List<ChessMove> chessMoves = new ArrayList<>();

        if(canMoveDiagonal==true) {
            //Check Diagonal
            chessMoves.addAll(checkMovesInDirection(piecePos, board, Directions.NE));
            chessMoves.addAll(checkMovesInDirection(piecePos, board, Directions.NW));
            chessMoves.addAll(checkMovesInDirection(piecePos, board, Directions.SE));
            chessMoves.addAll(checkMovesInDirection(piecePos, board, Directions.SW));
        }

        if(this.canMoveVertOrHor ==true) {
            chessMoves.addAll(checkMovesInDirection(piecePos, board, Directions.E));
            chessMoves.addAll(checkMovesInDirection(piecePos, board, Directions.W));
            chessMoves.addAll(checkMovesInDirection(piecePos, board, Directions.N));
            chessMoves.addAll(checkMovesInDirection(piecePos, board, Directions.S));

        }
        return chessMoves;
    }

    private List<ChessMove> checkMovesInDirection(Position piecePos, BoardSquare[][] board, Position direction ) {
        List<ChessMove> chessMoves = new ArrayList<>();
        boolean hitEdge = false;
        boolean hitPiece = false;

        int curXPos = piecePos.getX();
        int curYPos = piecePos.getY();

        do{
             curXPos = curXPos+ direction.getX();
             curYPos = curYPos+ direction.getY();

             if(curXPos <0 || curXPos>7|| curYPos>7||curYPos<0){
                 hitEdge = true;
                 continue;
             }

            if(board[curXPos][curYPos].hasPiece()==false){
                chessMoves.add(new NormalChessMove(ChessPosStore.getPostion(curXPos,curYPos),piecePos, chessPiece));
            }else{
                if(board[curXPos][curYPos].getChessPiece().getColor() != chessPiece.getColor()){
                    chessMoves.add(new TakeChessMove(ChessPosStore.getPostion(curXPos,curYPos),piecePos, chessPiece,board[curXPos][curYPos].getChessPiece()));
                }
                hitPiece = true;
            }
        }while(hitEdge==false && hitPiece ==false);

        return chessMoves;
    }
}
