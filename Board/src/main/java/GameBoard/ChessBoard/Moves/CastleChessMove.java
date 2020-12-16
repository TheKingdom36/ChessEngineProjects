package GameBoard.ChessBoard.Moves;



import GameBoard.ChessBoard.Models.ChessPiece;
import GameBoard.ChessBoard.Models.Position;
import GameBoard.ChessBoard.Util.PieceStore;
import lombok.Getter;
import lombok.Setter;

public class CastleChessMove extends ChessMove {

    @Getter @Setter private ChessPiece swappedRook;
    @Getter @Setter private Position rookToPos;
    @Getter @Setter private Position rookFromPos;

    public CastleChessMove(Position kingToPos, Position kingFromPos, Position rookToPos, Position rookFromPos, ChessPiece king, ChessPiece Rook) {
        super(kingToPos, kingFromPos, king);
        swappedRook = Rook;
        this.rookFromPos = rookFromPos;
        this.rookToPos = rookToPos;
    }




    @Override
    public boolean equals(Object otherChessMove) {

            if(!(otherChessMove instanceof CastleChessMove)){
                return false;
            }

            if(super.equals(otherChessMove) == true ){
                if(this.rookToPos.getX() == ((CastleChessMove) otherChessMove).getRookToPos().getX() && this.to.getY() == ((CastleChessMove) otherChessMove).getRookToPos().getY()){
                    if(this.from.getX() == ((CastleChessMove) otherChessMove).getRookFromPos().getX() && this.from.getY() == ((CastleChessMove) otherChessMove).getRookFromPos().getX()){
                        if(swappedRook.equals(((CastleChessMove) otherChessMove).getSwappedRook()) == true){
                            return true;
                        }
                    }
                }
            }

            return false;
    }


    @Override
    public ChessMove Copy() {
        return new CastleChessMove(this.to,this.from,this.rookToPos,this.rookFromPos, PieceStore.getPiece(this.chessPiece.getType(),this.chessPiece.getColor()), PieceStore.getPiece(this.swappedRook.getType(),this.swappedRook.getColor()));
    }

}
