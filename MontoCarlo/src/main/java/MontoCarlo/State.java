package MontoCarlo;

import GameBoard.Common.Interfaces.Move;
import GameBoard.ChessBoard.Enums.Type;
import GameBoard.ChessBoard.Moves.ChessMove;
import GameBoard.ChessBoard.Moves.NormalPromotionChessMove;
import GameBoard.ChessBoard.Moves.TakePromotionChessMove;
import NeuralNet.Output.MoveOption;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class State {


    private static List<MoveOption> movesOptions;
    private int visitCount;

    @Getter @Setter private Boolean isActive;
    @Getter @Setter private double winScore;
    @Getter @Setter private Move move;

    private double isBestMoveProbability;
    private GameState gameState;

    private int idMove;


    public State(){
        isActive = false;
        visitCount=0;
        winScore=0;
        isActive=false;
        this.isBestMoveProbability = 0;
        this.visitCount =0;
    }

    public State(State state) {
        this.gameState = this.gameState.createNewState();
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


    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }

    public State[] getAllPossibleStates() {

        List<Move> Moves = gameState.getAllAvailableMoves();

        State[] states = new State[Moves.size()];

        for(int i = 0; i< State.getMovesOptions().size(); i++){
            states[i] = new State();
            states[i].setIsActive(false);
        }

        int stateCounter=0;

        for(Move move: Moves){

            List<MoveOption> moveOptions = AllPieceMoveOptions.getMoveOptions();
            for (int i = 0; i < moveOptions.size(); i++) {
                MoveOption moveOption = moveOptions.get(i);
                if (moveOption instanceof QueenMoveOption) {

                    if (move.getFrom().compareTo(moveOption.getPiecePos()) == 0 &&
                            (move.getTo().getX() - move.getFrom().getX()) == moveOption.getDirection().getX() &&
                            (move.getTo().getY() - move.getFrom().getY()) == moveOption.getDirection().getY()
                    ) {

                        int higher;
                        if (Math.abs(move.getTo().getX() - move.getFrom().getX()) > Math.abs(move.getTo().getY() - move.getFrom().getY())) {

                            higher = Math.abs(move.getTo().getX() - move.getFrom().getX());
                        } else {


                            higher = Math.abs(move.getTo().getY() - move.getFrom().getY());
                        }

                        if (higher == ((QueenMoveOption) moveOption).getDistanceFromPiecePos()) {
                            CreateStateFromCurrentState(states[stateCounter], move,i);
                            break;
                        }
                    }
                } else if (moveOption instanceof KnightMoveOption) {
                    if (move.getFrom().compareTo(moveOption.getPiecePos()) == 0 && move.getChessPiece().getType() == Type.Knight) {
                        if ((move.getTo().getX() - move.getFrom().getX()) == moveOption.getDirection().getX() &&
                                (move.getTo().getY() - move.getFrom().getY()) == moveOption.getDirection().getY()) {
                            CreateStateFromCurrentState(states[stateCounter], move,i);
                            break;
                        }
                    }
                } else if (moveOption instanceof PawnPromotionMoveOption) {
                    if (move.getFrom().compareTo(moveOption.getPiecePos()) == 0 && (move instanceof NormalPromotionChessMove || move instanceof TakePromotionChessMove) && move.getChessPiece().getType() == Type.Pawn) {
                        if ((move.getTo().getX() - move.getFrom().getX()) == moveOption.getDirection().getX() &&
                                (move.getTo().getY() - move.getFrom().getY()) == moveOption.getDirection().getY()) {
                            CreateStateFromCurrentState(states[stateCounter], move,i);
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
        newState.setMove(m);
        newState.setGameState(this.gameState.createNewState(m));
    }

    void incrementVisit() {
        this.visitCount++;
    }




}