package MontoCarlo;

import GameBoard.Common.Interfaces.Move;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class State {



    private int visitCount;

    @Getter @Setter private Boolean isActive;
    @Getter @Setter private double winScore;
    @Getter @Setter private Move move;
    private double isBestMoveProbability;
    private GameState gameState;


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

        List<Move> availableMoves = gameState.getAllAvailableMoves();

        State[] states = new State[availableMoves.size()];

        for(State state:states){
            state = new State();
            state.setIsActive(false);
        }


        for(Move move:availableMoves){
            CreateStateFromCurrentState(move);
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
        return gameState.getMoveID();
    }

    private void CreateStateFromCurrentState( Move m) {
        State newState = new State();
        newState.setIsActive(true);
        newState.setMove(m);
        newState.setGameState(this.gameState.createNewState(m));
    }

    void incrementVisit() {
        this.visitCount++;
    }




}