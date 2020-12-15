package ChessBoard.Moves;


import ChessBoard.Models.ChessPiece;
import ChessBoard.Models.Position;
import ChessBoard.Common.Interfaces.Move;
import lombok.Getter;
import lombok.Setter;

public abstract class ChessMove implements Move {

    @Getter @Setter protected ChessPiece chessPiece;
    @Getter
    @Setter
    protected Position to;
    @Getter @Setter protected Position from;

    public ChessMove(Position to , Position from, ChessPiece chessPiece){
        this.to = to;
        this.from = from;
        this.chessPiece = chessPiece;
    }

    @Override
    public boolean equals(Object otherChessMove) {

        if(!(otherChessMove instanceof ChessMove)){
            return false;
        }

        if(this.getClass().equals(otherChessMove.getClass()) == true){
            if(this.to.getX() == ((ChessMove) otherChessMove).getTo().getX() && this.to.getY() == ((ChessMove) otherChessMove).getTo().getY()){
                if(this.from.getX() == ((ChessMove) otherChessMove).getFrom().getX() && this.from.getY() == ((ChessMove) otherChessMove).getFrom().getY()){
                    if(chessPiece.equals(((ChessMove) otherChessMove).getChessPiece()) == true){
                        return true;
                    }
                }
            }
        }
        return false;
    }


    @Override
    public String toString() {
        return "Move{" +
                "piece=" + chessPiece +
                ", to=" + to +
                ", from=" + from +
                '}';
    }


}
