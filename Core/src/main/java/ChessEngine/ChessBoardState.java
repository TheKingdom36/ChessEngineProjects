package ChessEngine;


import ChessBoard.Common.Interfaces.Move;
import ChessBoard.Enums.Color;
import ChessBoard.Models.ChessBoard;
import ChessBoard.MoveCheckers.CastleCheck;
import ChessBoard.Moves.CastleChessMove;
import ChessBoard.Moves.ChessMove;
import ChessBoard.Util.ChessPosStore;
//TODO Just import change to white class
import ChessEngine.Util.*;
import Common.Plane;
import MontoCarlo.GameState;

import java.util.List;

/**
 * Used to store board and metadata on the board
 */
public class ChessBoardState extends GameState {

    private ChessBoard chessBoard;
    private Color playerColor;
    private boolean CastleQueenSide;
    private boolean CastleKingSide;
    private boolean OppCastleQueenSide;
    private boolean OppCastleKingSide;
    private int noProgressCount;
    private int totalMoveCount;

    public ChessBoardState(){

    }



    public ChessBoardState(ChessBoardState chessBoardState){
        this.chessBoard = chessBoardState.getBoard().Copy();
        this.playerColor = chessBoardState.getPlayerColor();
        this.CastleQueenSide = chessBoardState.isCastleQueenSide();
        this.CastleKingSide = chessBoardState.isCastleKingSide();
        this.noProgressCount = chessBoardState.getNoProgressCount();
        this.totalMoveCount = chessBoardState.getTotalMoveCount();
        this.OppCastleQueenSide = chessBoardState.isOppCastleQueenSide();
        this.CastleKingSide = chessBoardState.isOppCastleKingSide();
    }

    public ChessBoardState(ChessBoard chessBoard, Color playerColor) {
        Color opponent;
        if(playerColor == Color.White){
            this.chessBoard = chessBoard.Copy();
            opponent = Color.Black;
        }else {
            this.chessBoard = ConvertBoardToWhite.Convert(chessBoard.Copy());
            opponent = Color.White;
        }
        this.chessBoard = chessBoard;
        this.playerColor = playerColor;

        //Check if castling is available
        List<ChessMove> castleChessMoves = CastleCheck.Check(chessBoard, chessBoard.getMoveLog(),this.getPlayerColor());
        for(ChessMove castlem: castleChessMoves){
                if(((CastleChessMove)castlem).getRookFromPos().compareTo(ChessPosStore.getPostion(0,0))==1){
                    CastleQueenSide = true;
                }else if(((CastleChessMove)castlem).getFrom().compareTo(ChessPosStore.getPostion(0,7))==1){
                    CastleKingSide = true;
                }
        }

        castleChessMoves.clear();


        //Check if Opponent castling is available
        castleChessMoves = CastleCheck.Check(getBoard(), chessBoard.getMoveLog(), opponent);
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
        castleChessMoves = CastleCheck.Check(getBoard(), chessBoard.getMoveLog(), opponent);
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

    public ChessBoard getBoard() {
        return chessBoard;
    }

    public void setBoard(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }

    public Color getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(Color playerColor) {
        this.playerColor = playerColor;
    }

    public int getNoProgressCount() {
        return noProgressCount;
    }

    public void setNoProgressCount(int noProgressCount) {
        this.noProgressCount = noProgressCount;
    }

    public int getTotalMoveCount() {
        return totalMoveCount;
    }

    public void setTotalMoveCount(int totalMoveCount) {
        this.totalMoveCount = totalMoveCount;
    }

    public boolean isCastleQueenSide() {
        return CastleQueenSide;
    }

    public void setCastleQueenSide(boolean castleQueenSide) {
        CastleQueenSide = castleQueenSide;
    }

    public boolean isCastleKingSide() {
        return CastleKingSide;
    }

    public void setCastleKingSide(boolean castleKingSide) {
        CastleKingSide = castleKingSide;
    }

    public boolean isOppCastleQueenSide() {
        return OppCastleQueenSide;
    }

    public void setOppCastleQueenSide(boolean oppCastleQueenSide) {
        OppCastleQueenSide = oppCastleQueenSide;
    }

    public boolean isOppCastleKingSide() {
        return OppCastleKingSide;
    }

    public void setOppCastleKingSide(boolean oppCastleKingSide) {
        OppCastleKingSide = oppCastleKingSide;
    }

    @Override
    public Plane[][] ConvertToNeuralNetInput() {
        return new Plane[0][];
    }

    @Override
    public <MoveType extends Move> List<MoveType> getAllAvailableMoves() {
        return null;
    }

    @Override
    public GameState CreateNewState() {
        return null;
    }

    @Override
    public void NextPlayer() {

    }
}
