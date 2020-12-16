package GameBoard.ChessBoard.Moves;


import GameBoard.ChessBoard.Enums.Type;
import GameBoard.ChessBoard.Models.ChessPiece;
import GameBoard.ChessBoard.Models.Position;
import GameBoard.ChessBoard.Util.PieceStore;
import lombok.Getter;
import lombok.Setter;

public class NormalPromotionChessMove extends NormalChessMove {

    @Getter
    @Setter
    protected ChessPiece promotedTo;

    {
        try {
            promotedTo = PieceStore.getPiece(Type.Queen,this.chessPiece.getColor());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public NormalPromotionChessMove(Position to, Position from, ChessPiece chessPiece) {
        super(to, from, chessPiece);
    }


    public String toString(){
        return "From x:"+from.getX()+ " y:"+from.getY()+"To x:"+to.getX()+ " y:"+to.getY() +" " + chessPiece.getType().name() + " NormalPromotionMove ";
    }

    @Override
    public boolean equals(Object move) {

        if(!(move instanceof NormalPromotionChessMove)){
            return false;
        }

        if(super.equals(move)==true){
              if(promotedTo.equals(((NormalPromotionChessMove)move).getPromotedTo()) == true){
                        return true;
              }
        }

        return false;
    }

    @Override
    public ChessMove Copy() {
        NormalPromotionChessMove newMove =   new NormalPromotionChessMove(this.to,this.from, PieceStore.getPiece(chessPiece.getType(), chessPiece.getColor()));
        newMove.setPromotedTo(PieceStore.getPiece(promotedTo.getType(),promotedTo.getColor()));
        return newMove;
    }

}
