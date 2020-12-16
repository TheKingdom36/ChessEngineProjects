package GameBoard.ChessBoard.Moves;


import GameBoard.ChessBoard.Models.ChessPiece;
import GameBoard.ChessBoard.Models.Position;
import GameBoard.ChessBoard.Util.PieceStore;
import lombok.Getter;
import lombok.Setter;

public class TakeChessMove extends ChessMove {


    @Getter
    @Setter
    ChessPiece chessPieceTaken;



    public TakeChessMove(Position toPos, Position fromPos, ChessPiece chessPiece, ChessPiece chessPieceTaken){
        super(toPos,fromPos, chessPiece);
        this.chessPieceTaken = chessPieceTaken;
    }

    public String toString(){
        return "From x:"+from.getX()+ " y:"+from.getY()+"To x:"+to.getX()+ " y:"+to.getY() +" " + chessPiece.getType().name() + " TakeMove "+this.chessPieceTaken.getType().name();
    }

    @Override
    public ChessMove Copy() {
        return new TakeChessMove(this.to,this.from, PieceStore.getPiece(this.chessPiece.getType(),this.chessPiece.getColor()), PieceStore.getPiece(chessPieceTaken.getType(), chessPieceTaken.getColor()));
    }

    @Override
    public boolean equals(Object move) {

        if(!(move instanceof TakeChessMove)){
            return false;
        }

        if(super.equals(move)==true){
            if(chessPieceTaken.equals(((TakeChessMove)move).getChessPieceTaken()) == true){
                return true;
            }
        }
        return false;
    }
}
