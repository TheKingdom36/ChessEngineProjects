package GameBoard.ChessBoard.Moves;


import GameBoard.ChessBoard.Enums.Type;
import GameBoard.ChessBoard.Models.ChessPiece;
import GameBoard.ChessBoard.Models.Position;
import GameBoard.ChessBoard.Util.PieceStore;
import lombok.Getter;
import lombok.Setter;

public class TakePromotionChessMove extends TakeChessMove{

     @Getter
     @Setter
     protected ChessPiece promotedTo = PieceStore.getPiece(Type.Queen,this.chessPiece.getColor());

    public TakePromotionChessMove(Position toPos, Position fromPos, ChessPiece chessPiece, ChessPiece chessPieceTaken) {
        super(toPos, fromPos, chessPiece, chessPieceTaken);
    }




    @Override
    public boolean equals(Object move) {

        if(!(move instanceof TakePromotionChessMove)){
            return false;
        }

        if(super.equals(move)==true){
            if(promotedTo.equals(((TakePromotionChessMove) move).getPromotedTo()) == true){
                return true;
            }


        }
        return false;
    }

    @Override
    public ChessMove Copy() {
        TakePromotionChessMove newMove =   new TakePromotionChessMove(this.to,this.from, PieceStore.getPiece(chessPiece.getType(), chessPiece.getColor()), PieceStore.getPiece(chessPiece.getType(), chessPiece.getColor()));
        newMove.setPromotedTo(PieceStore.getPiece(promotedTo.getType(),promotedTo.getColor()));
        return newMove;
    }

    public String toString(){
        return "From x:"+from.getX()+ " y:"+from.getY()+"To x:"+to.getX()+ " y:"+to.getY() +" piece: " + chessPiece.getType().name() + " taken: "+this.chessPieceTaken.getType().name() + " TakePromotionMove";
    }
}
