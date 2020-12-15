package ChessBoard.Models;

import lombok.Getter;
import lombok.Setter;


public class BoardSquare implements Comparable<BoardSquare>{
    @Getter private ChessPiece chessPiece;

    public boolean hasPiece() {
        return hasPiece;
    }

    public void setHasPiece(boolean hasPiece) {
        this.hasPiece = hasPiece;
    }

    private boolean hasPiece;

    public void setChessPiece(ChessPiece chessPiece) {
        if(chessPiece !=null){
        this.chessPiece = chessPiece;
        hasPiece = true;
        }
    }

    public void clear(){
        chessPiece = null;
        hasPiece = false;
    }

    public BoardSquare(){
        hasPiece=false;
    }

    @Override
    public int compareTo(BoardSquare otherBoardSquare) {
        if(this.hasPiece  == otherBoardSquare.hasPiece){

            if(otherBoardSquare.hasPiece==true){
                if(this.chessPiece.getType() == otherBoardSquare.getChessPiece().getType()
                        && this.chessPiece.getColor() == otherBoardSquare.getChessPiece().getColor()
                ){
                    return 0;
                }
            }else {
                return 0;
            }
        }
        return 1;
    }
}
