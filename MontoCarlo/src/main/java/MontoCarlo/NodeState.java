package MontoCarlo;

import GameBoard.Common.Interfaces.Move;

import MontoCarlo.tree.Node;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public abstract class NodeState {
    private int visitCount;
    private int winScore;
    @Getter @Setter private Boolean isActive;


    public NodeState(){
        isActive = false;
        visitCount=0;
        winScore=0;
        isActive=false;
        this.visitCount =0;
    }

    public NodeState(NodeState nodeState) {
        this.visitCount = nodeState.getVisitCount();
        winScore=0;
        isActive=false;
    }



    public void updateWinScore(double value){
        winScore += value;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }

    public abstract <State extends NodeState> State[] getAllPossibleStates();

    void incrementVisit() {
        this.visitCount++;
    }

    public abstract  <State extends NodeState> State deepCopy();

}