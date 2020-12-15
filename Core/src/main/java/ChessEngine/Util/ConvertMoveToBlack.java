package ChessEngine.Util;


import ChessBoard.Enums.Color;
import ChessBoard.Models.Position;
import ChessBoard.Moves.*;
import ChessBoard.Util.ChessPosStore;
import ChessBoard.Util.PieceStore;

/**
 * Used to Convert a Move to Black form White
 */
public class ConvertMoveToBlack {
    public static ChessMove Convert(ChessMove chessMove){
        ChessMove newChessMove;

      if(chessMove instanceof TakePromotionChessMove){
          newChessMove = new TakePromotionChessMove(flipPosition(chessMove.getTo()), flipPosition(chessMove.getFrom()), PieceStore.getPiece(chessMove.getChessPiece().getType(), Color.Black), PieceStore.getPiece(((TakePromotionChessMove) chessMove).getChessPieceTaken().getType(), Color.White));
          ((TakePromotionChessMove) newChessMove).setPromotedTo(PieceStore.getPiece(((TakePromotionChessMove) newChessMove).getPromotedTo().getType(),((TakePromotionChessMove) newChessMove).getPromotedTo().getColor()));
      }else if(chessMove instanceof CastleChessMove) {
          newChessMove = new CastleChessMove(flipPosition(chessMove.getTo()), flipPosition(chessMove.getFrom()), flipPosition(((CastleChessMove) chessMove).getRookToPos()), flipPosition(((CastleChessMove) chessMove).getRookFromPos()), PieceStore.getPiece(chessMove.getChessPiece().getType(), Color.Black), PieceStore.getPiece(((CastleChessMove) chessMove).getSwappedRook().getType(), Color.Black));
      }else if(chessMove instanceof TakeChessMove){
          newChessMove = new TakeChessMove(flipPosition(chessMove.getTo()), flipPosition(chessMove.getFrom()), PieceStore.getPiece(chessMove.getChessPiece().getType(), Color.Black), PieceStore.getPiece(((TakeChessMove) chessMove).getChessPieceTaken().getType(), Color.White));
      }else  if(chessMove instanceof NormalPromotionChessMove){
          newChessMove = new NormalPromotionChessMove(flipPosition(chessMove.getTo()), flipPosition(chessMove.getFrom()), PieceStore.getPiece(chessMove.getChessPiece().getType(), Color.Black));
          ((NormalPromotionChessMove) newChessMove).setPromotedTo(PieceStore.getPiece(((NormalPromotionChessMove) newChessMove).getPromotedTo().getType(),((NormalPromotionChessMove) newChessMove).getPromotedTo().getColor()));
      }else {
          newChessMove = new NormalChessMove(flipPosition(chessMove.getTo()), flipPosition(chessMove.getFrom()), PieceStore.getPiece(chessMove.getChessPiece().getType(), Color.Black));
      }

      return newChessMove;
    }

    private static Position flipPosition(Position pos){
        int posX = 7- pos.getX();
        int posY = pos.getY();

        return ChessPosStore.getPostion(posX,posY);
    }
}
