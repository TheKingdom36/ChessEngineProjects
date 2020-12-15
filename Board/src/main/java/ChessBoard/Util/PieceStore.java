package ChessBoard.Util;


import ChessBoard.Enums.Color;
import ChessBoard.Enums.Type;
import ChessBoard.Models.ChessPiece;
import ChessBoard.MoveCheckers.KingMoveChecker;
import ChessBoard.MoveCheckers.KnightMoveChecker;
import ChessBoard.MoveCheckers.PawnMoveChecker;
import ChessBoard.MoveCheckers.SliderMoveChecker;

import java.util.HashMap;
import java.util.Map;

public class PieceStore {


    static Map<Type, ChessPiece> pieceWhiteplane = new HashMap<>();
    static Map<Type, ChessPiece> pieceBlackplane = new HashMap<>();

    public PieceStore(){
    }

    public static ChessPiece getPiece(Type type, Color color)  {

        if(color == Color.White) {
            if (pieceWhiteplane.containsKey(type) == false) {
                pieceWhiteplane.put(type, CreatePiece(type, Color.White));
            }

            return pieceWhiteplane.get(type);
        }else {
            if (pieceBlackplane.containsKey(type) == false) {
                pieceBlackplane.put(type, CreatePiece(type, Color.Black));
            }

            return pieceBlackplane.get(type);
        }


    }

    private static ChessPiece CreatePiece(Type type, Color color){
        ChessPiece chessPiece = new ChessPiece();
        chessPiece.setColor(color);
        chessPiece.setType(type);

        switch (type) {
            case King:{

                chessPiece.setMvChecker(new KingMoveChecker(chessPiece));
                break;
            }
            case Knight:{
                chessPiece.setMvChecker(new KnightMoveChecker(chessPiece));
                break;
            }
            case Bishop:{
                chessPiece.setMvChecker(new SliderMoveChecker(chessPiece,true,false));
                break;
            }
            case Pawn:{
                chessPiece.setMvChecker(new PawnMoveChecker(chessPiece));
                break;
            }
            case Rook:{
                chessPiece.setMvChecker(new SliderMoveChecker(chessPiece,false,true));
                break;
            }
            case Queen:{
                chessPiece.setMvChecker(new SliderMoveChecker(chessPiece,true,true));
                break;
            }
            default:{
                System.out.println(chessPiece.getType() + " piece has no defined type");

            }
        }

        return chessPiece;
    }




}
