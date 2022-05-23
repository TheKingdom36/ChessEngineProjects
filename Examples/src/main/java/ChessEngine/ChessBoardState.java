package ChessEngine;


import GameBoard.ChessBoard.Enums.Type;
import GameBoard.ChessBoard.Moves.NormalPromotionChessMove;
import GameBoard.ChessBoard.Moves.TakePromotionChessMove;
import GameBoard.ChessBoard.Enums.Color;
import GameBoard.ChessBoard.Models.ChessBoard;
import GameBoard.ChessBoard.MoveCheckers.CastleCheck;
import GameBoard.ChessBoard.Moves.CastleChessMove;
import GameBoard.ChessBoard.Moves.ChessMove;
import GameBoard.ChessBoard.Util.ChessPosStore;
//TODO Just import change to white class
import MontoCarlo.GameState;
import MontoCarlo.NodeState;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to store board and metadata on the board
 */
@Getter
@Setter
public class ChessBoardState extends GameState<ChessMove> {

    private ChessBoard board;
    private Color playerColor;
    private boolean castleQueenSide;
    private boolean castleKingSide;
    private boolean oppCastleQueenSide;
    private boolean oppCastleKingSide;
    private int noProgressCount;
    private int totalMoveCount;


    //The first move in this state
    private ChessMove stateMove;

    public ChessBoardState(){

    }

    //TODO
    public <State extends NodeState> State deepCopy() {
        return null;
    }


    public ChessBoardState(ChessBoardState chessBoardState){
        this.board = chessBoardState.getBoard().copy();
        this.playerColor = chessBoardState.getPlayerColor();
        this.castleQueenSide = chessBoardState.isCastleQueenSide();
        this.castleKingSide = chessBoardState.isCastleKingSide();
        this.noProgressCount = chessBoardState.getNoProgressCount();
        this.totalMoveCount = chessBoardState.getTotalMoveCount();
        this.oppCastleQueenSide = chessBoardState.isOppCastleQueenSide();
        this.castleKingSide = chessBoardState.isOppCastleKingSide();
    }

    public ChessBoardState(ChessBoard chessBoard, Color playerColor) {
        this.board = chessBoard.copy();
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
                    oppCastleQueenSide = true;
                } else if (((CastleChessMove) castlem).getFrom().compareTo(ChessPosStore.getPostion(0, 7)) == 1) {
                    oppCastleKingSide = true;
                }
            } else {
                if (((CastleChessMove) castlem).getRookFromPos().compareTo(ChessPosStore.getPostion(7, 0)) == 1) {
                    oppCastleQueenSide = true;
                } else if (((CastleChessMove) castlem).getFrom().compareTo(ChessPosStore.getPostion(7, 7)) == 1) {
                    oppCastleKingSide = true;
                }
            }
        }

        castleChessMoves.clear();


        //Check if Opponent castling is available
        castleChessMoves = CastleCheck.Check((ChessBoard) getBoard(), chessBoard.getMoveLog(), opponent);
        for (ChessMove castlem : castleChessMoves) {
            if (opponent == Color.White) {
                if (((CastleChessMove) castlem).getRookFromPos().compareTo(ChessPosStore.getPostion(0, 0)) == 1) {
                    oppCastleQueenSide = true;
                } else if (((CastleChessMove) castlem).getFrom().compareTo(ChessPosStore.getPostion(0, 7)) == 1) {
                    oppCastleKingSide = true;
                }
            } else {
                if (((CastleChessMove) castlem).getRookFromPos().compareTo(ChessPosStore.getPostion(7, 0)) == 1) {
                    oppCastleQueenSide = true;
                } else if (((CastleChessMove) castlem).getFrom().compareTo(ChessPosStore.getPostion(7, 7)) == 1) {
                    oppCastleKingSide = true;
                }
            }
        }

        this.totalMoveCount = chessBoard.getMoveLog().getPastMoves().size();

        List<ChessMove> pastChessMoves = this.getBoard().getMoveLog().getPastMoves();


        if(pastChessMoves.size()>3){
            ChessBoard tempChessBoard = this.getBoard().copy();
            tempChessBoard.undoMove(pastChessMoves.get(pastChessMoves.size()-1));
            tempChessBoard.undoMove(pastChessMoves.get(pastChessMoves.size()-2));
            tempChessBoard.undoMove(pastChessMoves.get(pastChessMoves.size()-3));
            tempChessBoard.undoMove(pastChessMoves.get(pastChessMoves.size()-4));
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
        this.setBoard(chessBoardState.getBoard().copy());
        this.getBoard().updateBoard(m);

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
        castleChessMoves = CastleCheck.Check(getBoard(), board.getMoveLog(), opponent);
        for (ChessMove castlem : castleChessMoves) {
            if (opponent == Color.White) {
                if (((CastleChessMove) castlem).getRookFromPos().compareTo(ChessPosStore.getPostion(0, 0)) == 1) {
                    oppCastleQueenSide = true;
                } else if (((CastleChessMove) castlem).getFrom().compareTo(ChessPosStore.getPostion(0, 7)) == 1) {
                    oppCastleKingSide = true;
                }
            } else {
                if (((CastleChessMove) castlem).getRookFromPos().compareTo(ChessPosStore.getPostion(7, 0)) == 1) {
                    oppCastleQueenSide = true;
                } else if (((CastleChessMove) castlem).getFrom().compareTo(ChessPosStore.getPostion(7, 7)) == 1) {
                    oppCastleKingSide = true;
                }
            }
        }


        //update total move count
        this.setTotalMoveCount(chessBoardState.getTotalMoveCount()+1);

        List<ChessMove> pastChessMoves = this.getBoard().getMoveLog().getPastMoves();

        //if the board from 4 moves ago was the same as the current board there has been no progress
        if(pastChessMoves.size()>3){
            ChessBoard tempChessBoard = this.getBoard().copy();
            tempChessBoard.undoMove(pastChessMoves.get(pastChessMoves.size()-1));
            tempChessBoard.undoMove(pastChessMoves.get(pastChessMoves.size()-2));
            tempChessBoard.undoMove(pastChessMoves.get(pastChessMoves.size()-3));
            tempChessBoard.undoMove(pastChessMoves.get(pastChessMoves.size()-4));
            if(tempChessBoard.compareTo(this.getBoard())==1){
                this.setNoProgressCount(chessBoardState.getNoProgressCount()+1);
            }else {

            }
        }else{
            this.setNoProgressCount(0);
        }
    }

    @Override
    public List<ChessMove> getAllAvailableMoves() {
        return board.getAllAvailableMoves(playerColor);
    }



    @Override
    public ChessBoardState createNewState(ChessMove move) {
        ChessBoardState newChessBoardState = new ChessBoardState(this);
        newChessBoardState.setStateMove(move);
        newChessBoardState.getBoard().updateBoard(move);
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
//TODO
    }

    @Override
    protected ChessBoardState[] getAllPossibleStates(List<ChessMove> movesList) {
        //TODO create a list of all possible states from the current state given the list of moves
        ArrayList<ChessBoardState> states = new ArrayList<>();
        for(ChessMove move : movesList){
            states.add( new ChessBoardState(this,move));
        }


        return (ChessBoardState[]) states.toArray();
    }


}
