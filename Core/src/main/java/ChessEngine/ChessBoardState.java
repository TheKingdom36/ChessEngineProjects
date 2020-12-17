package ChessEngine;


import GameBoard.ChessBoard.Enums.Type;
import GameBoard.ChessBoard.Moves.NormalPromotionChessMove;
import GameBoard.ChessBoard.Moves.TakePromotionChessMove;
import GameBoard.Common.Interfaces.Move;
import GameBoard.ChessBoard.Enums.Color;
import GameBoard.ChessBoard.Models.ChessBoard;
import GameBoard.ChessBoard.MoveCheckers.CastleCheck;
import GameBoard.ChessBoard.Moves.CastleChessMove;
import GameBoard.ChessBoard.Moves.ChessMove;
import GameBoard.ChessBoard.Util.ChessPosStore;
//TODO Just import change to white class
import ChessEngine.Util.*;
import Common.Plane;
import MontoCarlo.GameState;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

/**
 * Used to store board and metadata on the board
 */
@Getter
@Setter
public class ChessBoardState extends GameState {

    private ChessBoard Board;
    private Color playerColor;
    private boolean CastleQueenSide;
    private boolean CastleKingSide;
    private boolean OppCastleQueenSide;
    private boolean OppCastleKingSide;
    private int noProgressCount;
    private int totalMoveCount;


    //The first move in this state
    private ChessMove stateMove;

    public ChessBoardState(){

    }



    public ChessBoardState(ChessBoardState chessBoardState){
        this.Board = chessBoardState.getBoard().Copy();
        this.playerColor = chessBoardState.getPlayerColor();
        this.CastleQueenSide = chessBoardState.isCastleQueenSide();
        this.CastleKingSide = chessBoardState.isCastleKingSide();
        this.noProgressCount = chessBoardState.getNoProgressCount();
        this.totalMoveCount = chessBoardState.getTotalMoveCount();
        this.OppCastleQueenSide = chessBoardState.isOppCastleQueenSide();
        this.CastleKingSide = chessBoardState.isOppCastleKingSide();
    }

    public ChessBoardState(ChessBoard chessBoard, Color playerColor) {
        this.Board = chessBoard.Copy();
        this.playerColor = playerColor;

        Color opponent;
        if(playerColor == Color.White){
            opponent = Color.Black;
        }else {
            opponent = Color.White;
        }




        //Check if castling is available
        List<ChessMove> castleChessMoves = CastleCheck.Check(chessBoard, chessBoard.getMoveLog(),this.getPlayerColor());
        for(ChessMove castlem: castleChessMoves){
            if (playerColor == Color.White) {
                if (((CastleChessMove) castlem).getRookFromPos().compareTo(ChessPosStore.getPostion(0, 0)) == 1) {
                    OppCastleQueenSide = true;
                } else if (((CastleChessMove) castlem).getFrom().compareTo(ChessPosStore.getPostion(0, 7)) == 1) {
                    OppCastleKingSide = true;
                }
            } else {
                if (((CastleChessMove) castlem).getRookFromPos().compareTo(ChessPosStore.getPostion(7, 0)) == 1) {
                    OppCastleQueenSide = true;
                } else if (((CastleChessMove) castlem).getFrom().compareTo(ChessPosStore.getPostion(7, 7)) == 1) {
                    OppCastleKingSide = true;
                }
            }
        }

        castleChessMoves.clear();


        //Check if Opponent castling is available
        castleChessMoves = CastleCheck.Check((ChessBoard) getBoard(), chessBoard.getMoveLog(), opponent);
        for (ChessMove castlem : castleChessMoves) {
            if (opponent == Color.White) {
                if (((CastleChessMove) castlem).getRookFromPos().compareTo(ChessPosStore.getPostion(0, 0)) == 1) {
                    OppCastleQueenSide = true;
                } else if (((CastleChessMove) castlem).getFrom().compareTo(ChessPosStore.getPostion(0, 7)) == 1) {
                    OppCastleKingSide = true;
                }
            } else {
                if (((CastleChessMove) castlem).getRookFromPos().compareTo(ChessPosStore.getPostion(7, 0)) == 1) {
                    OppCastleQueenSide = true;
                } else if (((CastleChessMove) castlem).getFrom().compareTo(ChessPosStore.getPostion(7, 7)) == 1) {
                    OppCastleKingSide = true;
                }
            }
        }

        this.totalMoveCount = chessBoard.getMoveLog().getPastMoves().size();

        List<ChessMove> pastChessMoves = this.getBoard().getMoveLog().getPastMoves();


        if(pastChessMoves.size()>3){
            ChessBoard tempChessBoard = this.getBoard().Copy();
            tempChessBoard.UndoMove(pastChessMoves.get(pastChessMoves.size()-1));
            tempChessBoard.UndoMove(pastChessMoves.get(pastChessMoves.size()-2));
            tempChessBoard.UndoMove(pastChessMoves.get(pastChessMoves.size()-3));
            tempChessBoard.UndoMove(pastChessMoves.get(pastChessMoves.size()-4));
            if(tempChessBoard.compareTo(this.getBoard())==1){
                this.setNoProgressCount(1);
            }else {
                this.setNoProgressCount(this.noProgressCount+1);
            }
        }else{
            this.setNoProgressCount(0);
        }



    }

    public ChessBoardState(ChessBoardState chessBoardState, ChessMove m){
        Color opponent;
        //Assign new board
        this.setBoard(chessBoardState.getBoard().Copy());
        this.getBoard().UpdateBoard(m);

        //Assign next player to move
        if(chessBoardState.getPlayerColor() == Color.White){
            this.setPlayerColor(Color.Black);
            opponent = Color.White;
        }else{
            this.setPlayerColor(Color.White);
            opponent = Color.Black;
        }

        //Check if castleing is available
        List<ChessMove> castleChessMoves = CastleCheck.Check(this.getBoard(),this.getBoard().getMoveLog(),this.getPlayerColor());
        for(ChessMove castlem: castleChessMoves){
            if(this.getPlayerColor()== Color.White){
                if(((CastleChessMove)castlem).getRookFromPos().compareTo(ChessPosStore.getPostion(0,0))==1){
                    this.setCastleQueenSide(true);
                }else if(((CastleChessMove)castlem).getFrom().compareTo(ChessPosStore.getPostion(0,7))==1){
                    this.setCastleKingSide(true);
                }
            }else{
                if(((CastleChessMove)castlem).getRookFromPos().compareTo(ChessPosStore.getPostion(7,0))==1){
                    this.setCastleQueenSide(true);
                }else if(((CastleChessMove)castlem).getFrom().compareTo(ChessPosStore.getPostion(7,7))==1){
                    this.setCastleKingSide(true);
                }
            }
        }

        castleChessMoves.clear();


        //Check if Opponent castling is available
        castleChessMoves = CastleCheck.Check(getBoard(), Board.getMoveLog(), opponent);
        for (ChessMove castlem : castleChessMoves) {
            if (opponent == Color.White) {
                if (((CastleChessMove) castlem).getRookFromPos().compareTo(ChessPosStore.getPostion(0, 0)) == 1) {
                    OppCastleQueenSide = true;
                } else if (((CastleChessMove) castlem).getFrom().compareTo(ChessPosStore.getPostion(0, 7)) == 1) {
                    OppCastleKingSide = true;
                }
            } else {
                if (((CastleChessMove) castlem).getRookFromPos().compareTo(ChessPosStore.getPostion(7, 0)) == 1) {
                    OppCastleQueenSide = true;
                } else if (((CastleChessMove) castlem).getFrom().compareTo(ChessPosStore.getPostion(7, 7)) == 1) {
                    OppCastleKingSide = true;
                }
            }
        }


        //update total move count
        this.setTotalMoveCount(chessBoardState.getTotalMoveCount()+1);

        List<ChessMove> pastChessMoves = this.getBoard().getMoveLog().getPastMoves();

        //if the board from 4 moves ago was the same as the current board there has been no progress
        if(pastChessMoves.size()>3){
            ChessBoard tempChessBoard = this.getBoard().Copy();
            tempChessBoard.UndoMove(pastChessMoves.get(pastChessMoves.size()-1));
            tempChessBoard.UndoMove(pastChessMoves.get(pastChessMoves.size()-2));
            tempChessBoard.UndoMove(pastChessMoves.get(pastChessMoves.size()-3));
            tempChessBoard.UndoMove(pastChessMoves.get(pastChessMoves.size()-4));
            if(tempChessBoard.compareTo(this.getBoard())==1){
                this.setNoProgressCount(chessBoardState.getNoProgressCount()+1);
            }else {

            }
        }else{
            this.setNoProgressCount(0);
        }
    }


    @Override
    public Plane[] convertToNeuralNetInput() {

        return ChessBoardToNNInputConverter.ConvertChessBoardToInput(this);

    }

    @Override
    public List<ChessMove> getAllAvailableMoves() {
        return Board.GetAllAvailableMoves(playerColor);
    }



    @Override
    public GameState createNewState(Move move) {
        ChessBoardState newChessBoardState = new ChessBoardState(this);
        newChessBoardState.setStateMove((ChessMove)move);
        newChessBoardState.Board.UpdateBoard((ChessMove)move);
        return newChessBoardState;

    }

    @Override
    public ChessBoardState createNewState() {
        return new ChessBoardState(this);
    }

    @Override
    public int getCurrentPlayerID() {
        if(playerColor==Color.Black){
            return 1;
        }else{
            return 0;
        }
    }

    @Override
    public ChessMove getMove() {

        return stateMove;
    }

    @Override
    public int getMoveID() {


            List<MoveOption> moveOptions = AllPieceMoveOptions.getMoveOptions();

            for (int i = 0; i < moveOptions.size(); i++) {
                MoveOption moveOption = moveOptions.get(i);

                if (moveOption instanceof QueenMoveOption) {

                    if (stateMove.getFrom().compareTo(moveOption.getPiecePos()) == 0 &&
                            (stateMove.getTo().getX() - stateMove.getFrom().getX()) == moveOption.getDirection().getX() &&
                            (stateMove.getTo().getY() - stateMove.getFrom().getY()) == moveOption.getDirection().getY()
                    ) {

                        int higher;
                        if (Math.abs(stateMove.getTo().getX() - stateMove.getFrom().getX()) > Math.abs(stateMove.getTo().getY() - stateMove.getFrom().getY())) {

                            higher = Math.abs(stateMove.getTo().getX() - stateMove.getFrom().getX());
                        } else {


                            higher = Math.abs(stateMove.getTo().getY() - stateMove.getFrom().getY());
                        }

                        if (higher == ((QueenMoveOption) moveOption).getDistanceFromPiecePos()) {
                            return i;
                        }
                    }
                } else if (moveOption instanceof KnightMoveOption) {
                    if (stateMove.getFrom().compareTo(moveOption.getPiecePos()) == 0 && stateMove.getChessPiece().getType() == Type.Knight) {
                        if ((stateMove.getTo().getX() - stateMove.getFrom().getX()) == moveOption.getDirection().getX() &&
                                (stateMove.getTo().getY() - stateMove.getFrom().getY()) == moveOption.getDirection().getY()) {
                          return i;

                        }
                    }
                } else if (moveOption instanceof PawnPromotionMoveOption) {
                    if (stateMove.getFrom().compareTo(moveOption.getPiecePos()) == 0 && (stateMove instanceof NormalPromotionChessMove || stateMove instanceof TakePromotionChessMove) && stateMove.getChessPiece().getType() == Type.Pawn) {
                        if ((stateMove.getTo().getX() - stateMove.getFrom().getX()) == moveOption.getDirection().getX() &&
                                (stateMove.getTo().getY() - stateMove.getFrom().getY()) == moveOption.getDirection().getY()) {

                            return i;
                        }
                    }

                }

            }


        return -1;
    }

    @Override
    public void nextPlayer() {

    }


}
