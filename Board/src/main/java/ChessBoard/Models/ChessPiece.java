package ChessBoard.Models;


import ChessBoard.Enums.Color;
import ChessBoard.Enums.Type;
import ChessBoard.MoveCheckers.MoveChecker;
import ChessBoard.Moves.ChessMove;
import ChessBoard.Common.Interfaces.Piece;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class ChessPiece implements Piece {

    @Getter @Setter private Color color;
    @Setter private MoveChecker mvChecker;
    @Getter @Setter private Type type;

    public ChessPiece(){
    }


    @Override
    public List<ChessMove> GetAvailableMoves(Position piecePos, BoardSquare[][] board)  {
        return mvChecker.checkAllAvailableMoves(piecePos,board);
    }

    @Override
    public boolean equals(Object otherChessPiece){
        if(!(otherChessPiece instanceof ChessPiece)){
            return true;
        }

        if(color == ((ChessPiece)otherChessPiece).getColor() && type == ((ChessPiece)otherChessPiece).getType()){
            return true;
        }

        return false;
    }
}
