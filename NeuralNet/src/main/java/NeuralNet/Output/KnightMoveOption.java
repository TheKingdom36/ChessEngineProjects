package NeuralNet.Output;


import GameBoard.ChessBoard.Models.Position;

public class KnightMoveOption extends MoveOption {
    public KnightMoveOption(Position position, Position direction) {
        super(position,direction);
    }

    @Override
    public String toString() {
        return "KnightMoveOption{" +
                "piecePos=" + piecePos +
                ", direction=" + direction +
                '}';
    }
}
