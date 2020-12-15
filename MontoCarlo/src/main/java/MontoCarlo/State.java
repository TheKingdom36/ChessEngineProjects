package MontoCarlo;

import ChessBoard.Common.Interfaces.Move;
import ChessBoard.Enums.Color;
import ChessBoard.Enums.Type;
import ChessBoard.Models.ChessBoard;
import ChessBoard.Moves.ChessMove;
import ChessBoard.Moves.NormalPromotionChessMove;
import ChessBoard.Moves.TakePromotionChessMove;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class State {


    private static List<MoveOption> movesOptions;
    private int visitCount;

    @Getter @Setter private Boolean isActive;
    @Getter @Setter private double winScore;

    private double isBestMoveProbability;
    private GameState gameState;
    private ChessMove chessMove;
    private int idMove;

    public ChessMove getChessMove() {
        return chessMove;
    }

    public void setChessMove(ChessMove chessMove) {
        this.chessMove = chessMove;
    }

    public State(){
        isActive = false;
        visitCount=0;
        winScore=0;
        isActive=false;
        this.isBestMoveProbability = 0;
        this.visitCount =0;
    }

    public State(State state) {
        this.gameState = this.gameState.CreateNewState();
        this.visitCount = state.getVisitCount();
        this.isBestMoveProbability = state.getBestMoveProbability();
        winScore=0;
        isActive=false;
    }

    public State(GameState gameState){
        this.gameState = gameState;
        this.visitCount = 0;
        this.isBestMoveProbability = 0;
        visitCount=0;
        winScore=0;
        isActive=false;
    }




    public static List<MoveOption> getMovesOptions() {
        return movesOptions;
    }
    public static void setMovesOptions(List<MoveOption> movesOptions) {
        State.movesOptions = movesOptions;
    }

    public double getBestMoveProbability() {
        return isBestMoveProbability;
    }
    public void setBestMoveProbability(double bestMoveProbability) {
        this.isBestMoveProbability = bestMoveProbability;
    }


    public void updateWinScore(double value){
        winScore += value;
    }

    public GameState getGameState() {
        return gameState;
    }
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }



    Color getPlayerColor() {
        return this.gameState.getPlayerColor();
    }
    void setPlayerColor(Color playerColor) {
        this.gameState.setPlayerColor(playerColor);
    }


    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }

    public State[] getAllPossibleStates() {
                                                                    //Board is always from white persepective
        List<Move> Moves = gameState.getAllAvailableMoves();

        State[] states = new State[State.getMovesOptions().size()];

        for(int i = 0; i< State.getMovesOptions().size(); i++){
            states[i] = new State();
            states[i].setIsActive(false);

        }

        int stateCounter=0;

        for(ChessMove m: chessMoves){

            List<MoveOption> moveOptions = AllPieceMoveOptions.getMoveOptions();
            for (int i = 0; i < moveOptions.size(); i++) {
                MoveOption moveOption = moveOptions.get(i);
                if (moveOption instanceof QueenMoveOption) {

                    if (m.getFrom().compareTo(moveOption.getPiecePos()) == 0 &&
                            (m.getTo().getX() - m.getFrom().getX()) == moveOption.getDirection().getX() &&
                            (m.getTo().getY() - m.getFrom().getY()) == moveOption.getDirection().getY()
                    ) {

                        int higher;
                        if (Math.abs(m.getTo().getX() - m.getFrom().getX()) > Math.abs(m.getTo().getY() - m.getFrom().getY())) {

                            higher = Math.abs(m.getTo().getX() - m.getFrom().getX());
                        } else {


                            higher = Math.abs(m.getTo().getY() - m.getFrom().getY());
                        }

                        if (higher == ((QueenMoveOption) moveOption).getDistanceFromPiecePos()) {
                            CreateStateFromCurrentState(states[stateCounter], m,i);
                            break;
                        }
                    }
                } else if (moveOption instanceof KnightMoveOption) {
                    if (m.getFrom().compareTo(moveOption.getPiecePos()) == 0 && m.getChessPiece().getType() == Type.Knight) {
                        if ((m.getTo().getX() - m.getFrom().getX()) == moveOption.getDirection().getX() &&
                                (m.getTo().getY() - m.getFrom().getY()) == moveOption.getDirection().getY()) {
                            CreateStateFromCurrentState(states[stateCounter], m,i);
                            break;
                        }
                    }
                } else if (moveOption instanceof PawnPromotionMoveOption) {
                    if (m.getFrom().compareTo(moveOption.getPiecePos()) == 0 && (m instanceof NormalPromotionChessMove || m instanceof TakePromotionChessMove) && m.getChessPiece().getType() == Type.Pawn) {
                        if ((m.getTo().getX() - m.getFrom().getX()) == moveOption.getDirection().getX() &&
                                (m.getTo().getY() - m.getFrom().getY()) == moveOption.getDirection().getY()) {
                            CreateStateFromCurrentState(states[stateCounter], m,i);
                            break;
                        }
                    }

                } else {
                    states[stateCounter].setBestMoveProbability(0);
                }

            }
            stateCounter++;
        }


;
        return states;
    }

    public double getIsBestMoveProbability() {
        return isBestMoveProbability;
    }

    public void setIsBestMoveProbability(double isBestMoveProbability) {
        this.isBestMoveProbability = isBestMoveProbability;
    }

    public int getIdMove() {
        return idMove;
    }

    public void setIdMove(int idMove) {
        this.idMove = idMove;
    }

    private void CreateStateFromCurrentState(State newState, ChessMove m, int idMove) {
        newState.setIsActive(true);
        newState.setIdMove(idMove);
        newState.setChessMove(m);
        newState.setGameState(new BoardState(this.boardState,m));
    }

    void incrementVisit() {
        this.visitCount++;
    }



    void togglePlayer() {
        if(boardState.getPlayerColor()== Color.White){
            boardState.setPlayerColor(Color.Black);
        }else{
            boardState.setPlayerColor(Color.White);
        }
    }
}