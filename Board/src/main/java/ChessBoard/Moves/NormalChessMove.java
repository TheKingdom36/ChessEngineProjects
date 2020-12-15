package ChessBoard.Moves;


import ChessBoard.Models.ChessPiece;
import ChessBoard.Models.Position;
import ChessBoard.Util.PieceStore;

public class NormalChessMove extends ChessMove{
    public NormalChessMove(Position to , Position from, ChessPiece chessPiece){
        super(to,from, chessPiece);
    }



    @Override
    public String toString() {
        return "NormalMove{" +
                "piece=" + chessPiece +
                ", to=" + to.toString() +
                ", from=" + from.toString() +
                '}';
    }

    @Override
    public ChessMove Copy() {
        return  new NormalChessMove(this.to,this.from, PieceStore.getPiece(chessPiece.getType(), chessPiece.getColor()));
    }


    @Override
    public boolean equals(Object move) {
        return super.equals(move);
    }
}
