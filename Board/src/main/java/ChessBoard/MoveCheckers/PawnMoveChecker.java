package ChessBoard.MoveCheckers;


import ChessBoard.Enums.Color;
import ChessBoard.Models.BoardSquare;
import ChessBoard.Models.ChessPiece;
import ChessBoard.Models.Position;
import ChessBoard.Moves.*;
import ChessBoard.Util.ChessPosStore;

import java.util.ArrayList;
import java.util.List;

public class PawnMoveChecker extends MoveChecker {

    //If piece is white its moving positive i.e 1 else its black moving negitive i.e -1
    private int forwardDir;

    public PawnMoveChecker(ChessPiece chessPiece){
        super(chessPiece);

        if(chessPiece.getColor()== Color.White){
            forwardDir = 1;
        }else{
            forwardDir = -1;
        }
    }

    @Override
    public List<ChessMove> checkAllAvailableMoves(Position piecePos, BoardSquare[][] board)  {


        List<ChessMove> chessMoves = new ArrayList<>();

        if(chessPiece.getColor()== Color.White){

            //check single move forward
            if(board[piecePos.getX()+forwardDir][piecePos.getY()].hasPiece()==false){
                //Check Rank
                if(piecePos.getX()+forwardDir == 7){
                    chessMoves.add(new NormalPromotionChessMove(ChessPosStore.getPostion(piecePos.getX()+forwardDir,piecePos.getY()),piecePos, chessPiece));
                }else {
                    chessMoves.add(new NormalChessMove(ChessPosStore.getPostion(piecePos.getX()+forwardDir,piecePos.getY()),piecePos, chessPiece));
                }

                //if on rank 1 check double move forward
                if(piecePos.getX()==1){
                    if(board[piecePos.getX()+forwardDir*2][piecePos.getY()].hasPiece()==false){
                        //Check Rank

                        chessMoves.add(new NormalChessMove(ChessPosStore.getPostion(piecePos.getX()+forwardDir*2,piecePos.getY()),piecePos, chessPiece));
                    }
                }
            }

        }else{

            //check single move forward
            if(board[piecePos.getX()+forwardDir][piecePos.getY()].hasPiece()==false){
                //Check Rank
                if(piecePos.getX()+forwardDir == 0){
                    chessMoves.add(new NormalPromotionChessMove(ChessPosStore.getPostion(piecePos.getX()+forwardDir,piecePos.getY()),piecePos, chessPiece));
                }else {
                    chessMoves.add(new NormalChessMove(ChessPosStore.getPostion(piecePos.getX()+forwardDir,piecePos.getY()),piecePos, chessPiece));
                }

                //if on rank 6 check double move forward
                if(piecePos.getX()==6){

                    if(board[piecePos.getX()+forwardDir*2][piecePos.getY()].hasPiece()==false){
                        //Check Rank
                        chessMoves.add(new NormalChessMove(ChessPosStore.getPostion(piecePos.getX()+forwardDir*2,piecePos.getY()),piecePos, chessPiece));
                    }
                }
            }


        }

        //check attacks on diagonals
        if(piecePos.getY()+1<=7 && board[piecePos.getX()+forwardDir][piecePos.getY()+1].hasPiece()==true){
            if(board[piecePos.getX()+forwardDir][piecePos.getY()+1].getChessPiece().getColor() != chessPiece.getColor()){

                if(piecePos.getX()+forwardDir == 0 || piecePos.getX()+forwardDir == 7){
                    chessMoves.add(new TakePromotionChessMove(ChessPosStore.getPostion(piecePos.getX()+forwardDir,piecePos.getY()+1),piecePos, chessPiece,board[piecePos.getX()+forwardDir][piecePos.getY()+1].getChessPiece()));
                }else {
                    chessMoves.add(new TakeChessMove(ChessPosStore.getPostion(piecePos.getX()+forwardDir,piecePos.getY()+1),piecePos, chessPiece,board[piecePos.getX()+forwardDir][piecePos.getY()+1].getChessPiece()));
                }

            }
        }


        if(piecePos.getY()-1>=0 && board[piecePos.getX()+forwardDir][piecePos.getY()-1].hasPiece()==true){
            if(board[piecePos.getX()+forwardDir][piecePos.getY()-1].getChessPiece().getColor() != chessPiece.getColor()){
                if(piecePos.getX()+forwardDir == 0 || piecePos.getX()+forwardDir == 7){
                    chessMoves.add(new TakePromotionChessMove(ChessPosStore.getPostion(piecePos.getX()+forwardDir,piecePos.getY()-1),piecePos, chessPiece,board[piecePos.getX()+forwardDir][piecePos.getY()-1].getChessPiece()));
                }else {
                    chessMoves.add(new TakeChessMove(ChessPosStore.getPostion(piecePos.getX()+forwardDir,piecePos.getY()-1),piecePos, chessPiece,board[piecePos.getX()+forwardDir][piecePos.getY()-1].getChessPiece()));
                }
            }
        }

        return chessMoves;

    }
}
