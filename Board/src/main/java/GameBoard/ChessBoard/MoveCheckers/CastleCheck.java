package GameBoard.ChessBoard.MoveCheckers;



import GameBoard.ChessBoard.Enums.Color;
import GameBoard.ChessBoard.Enums.Type;
import GameBoard.ChessBoard.Models.ChessBoard;
import GameBoard.ChessBoard.Models.Position;
import GameBoard.ChessBoard.Moves.CastleChessMove;
import GameBoard.ChessBoard.Moves.ChessMove;
import GameBoard.ChessBoard.Util.ChessPosStore;
import GameBoard.Common.MoveLog;

import java.util.ArrayList;
import java.util.List;

public class CastleCheck {

    static private Position KingStartPos;
    static private Position RookQStartPos;
    static private Position RookKStartPos;


    static private boolean CanCastleQueenSide = true;
    static private boolean CanCastleKingSide = true;


    public static List<ChessMove> Check(ChessBoard chessBoard, MoveLog<ChessMove> movelog, Color color){
        List<ChessMove> chessMoves = new ArrayList<>();

        CanCastleKingSide=true;
        CanCastleQueenSide=true;

        if(color== Color.White){
            KingStartPos = ChessPosStore.getPostion(0,4);
            RookQStartPos = ChessPosStore.getPostion(0,0);
            RookKStartPos = ChessPosStore.getPostion(0,7);
        }else{
            KingStartPos = ChessPosStore.getPostion(7,4);
            RookQStartPos = ChessPosStore.getPostion(7,0);
            RookKStartPos = ChessPosStore.getPostion(7,7);
        }

        //if there is no piece at king pos the king has moved
        if(chessBoard.getBoardSquares()[KingStartPos.getX()][KingStartPos.getY()].getChessPiece() == null){
            return chessMoves;
        }else{
            //if there is a piece at 0 4 but its not king the king has moved
            if(chessBoard.getBoardSquares()[KingStartPos.getX()][KingStartPos.getY()].getChessPiece().getType() != Type.King){
                return chessMoves;
            }else if(SquareSaftyCheck.isSquareSafe(KingStartPos,color, chessBoard.getBoardSquares()) == false){
                return chessMoves;
            }
        }

        //Check if the squares between king and rook are empty and safe
        //Check king side
        CanCastleKingSide = isSafeToKingSideCastle(chessBoard,color);

        //Check Queen side
        CanCastleQueenSide = isSafeToQueenSideCastle(chessBoard,color);


        //check move log if the king or rook moved
        if(CanCastleQueenSide==true || CanCastleKingSide==true){
            for(ChessMove m: movelog.getPastMoves()){
                if(m.getChessPiece().getType()== Type.Rook && m.getChessPiece().getColor() == Color.White && m.getFrom() == RookQStartPos){
                    CanCastleQueenSide=false;
                }else if(m.getChessPiece().getType()== Type.Rook && m.getChessPiece().getColor()== Color.White && m.getFrom() == RookKStartPos){
                    CanCastleKingSide=false;
                }else if(m.getChessPiece().getType()== Type.King && m.getChessPiece().getColor()== Color.White && m.getFrom() == KingStartPos){
                    //no available castle moves because king moved
                    return chessMoves;
                }
            }
        }

        if(CanCastleKingSide==true){
            chessMoves.add(new CastleChessMove(ChessPosStore.getPostion(KingStartPos.getX(),2), KingStartPos, ChessPosStore.getPostion(KingStartPos.getX(),3), RookKStartPos, chessBoard.getBoardSquares()[KingStartPos.getX()][KingStartPos.getY()].getChessPiece(), chessBoard.getBoardSquares()[RookKStartPos.getX()][RookKStartPos.getY()].getChessPiece()));
        }

        if (CanCastleQueenSide==true){
            chessMoves.add(new CastleChessMove(ChessPosStore.getPostion(KingStartPos.getX(),6), KingStartPos, ChessPosStore.getPostion(KingStartPos.getX(),5), RookQStartPos, chessBoard.getBoardSquares()[KingStartPos.getX()][KingStartPos.getY()].getChessPiece(), chessBoard.getBoardSquares()[RookQStartPos.getX()][RookQStartPos.getY()].getChessPiece()));
        }

        return chessMoves;

    }

     private static boolean isSafeToQueenSideCastle(ChessBoard chessBoard, Color color){

        if(!SquareSaftyCheck.isSquareSafe(RookQStartPos, color, chessBoard.getBoardSquares())){

            return false;
        }

         //Check if the squares between king and rook are empty and safe
         //Check Queen side
         for(int i = KingStartPos.getY()-1; i>0; i--){

             if(chessBoard.getBoardSquares()[KingStartPos.getX()][i].hasPiece()==true){
                 return false;
             }else{
                 if(false == SquareSaftyCheck.isSquareSafe(ChessPosStore.getPostion(KingStartPos.getX(),i),color, chessBoard.getBoardSquares())){
                    return false;
                 }

             }
         }
         return true;

     }

    private static boolean isSafeToKingSideCastle(ChessBoard chessBoard, Color color){
        if(false == SquareSaftyCheck.isSquareSafe(RookKStartPos,color, chessBoard.getBoardSquares())){

            return false;
        }

        for(int i = KingStartPos.getY()+1; i<7; i++){

            if(chessBoard.getBoardSquares()[KingStartPos.getX()][i].hasPiece()==true){

                    return false;
            }else{
                if(SquareSaftyCheck.isSquareSafe(ChessPosStore.getPostion(KingStartPos.getX(),i),color, chessBoard.getBoardSquares())==false){

                    return false;
                }
            }
        }

        return true;
    }


}
