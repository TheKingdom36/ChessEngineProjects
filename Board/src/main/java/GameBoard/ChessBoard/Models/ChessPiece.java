package GameBoard.ChessBoard.Models;


import GameBoard.ChessBoard.Enums.Color;
import GameBoard.ChessBoard.Enums.Type;
import GameBoard.ChessBoard.MoveCheckers.MoveChecker;
import GameBoard.ChessBoard.Moves.ChessMove;
import GameBoard.Common.Interfaces.Piece;
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
