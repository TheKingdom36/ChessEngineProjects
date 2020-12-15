package NeuralNet.Output;


import ChessBoard.Enums.Type;
import ChessBoard.Models.Position;

public class PawnPromotionMoveOption extends MoveOption {
    Type promotedToPieceType;

    public Type getPromotedToPieceType() {
        return promotedToPieceType;
    }

    public PawnPromotionMoveOption(Position piecePos, Position direction, Type promotedToPieceType) {
        super(piecePos, direction);
        this.promotedToPieceType = promotedToPieceType;
    }

}
