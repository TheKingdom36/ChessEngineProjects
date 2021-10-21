package MontoCarlo;

import MontoCarlo.tree.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UCT {

    public static double uctValue(int totalVisit, double nodeWinScore, int nodeVisit,double probOFTakingAction,Boolean isActive) {
        if (nodeVisit == 0) {
            return Integer.MAX_VALUE;
        }


        if(isActive == true){
            return nodeWinScore  + 2 *probOFTakingAction* Math.sqrt(Math.log(totalVisit) /1+ (double) nodeVisit);
        }else{
            return -10000;
        }
    }

    static Node findNodeWithHighestUCT(Node node) {
        int parentVisit = node.getState().getVisitCount();

        List<Node> activeNodes = new ArrayList<>();

        for(Object child: node.getChildArray()){
            if(((Node)child).getState().getIsActive()==true){
                activeNodes.add((Node)child);
            }
        }

        return Collections.max(
                activeNodes,
                Comparator.comparing(c -> uctValue(parentVisit, c.getState().getWinScore(), c.getState().getVisitCount(),c.getState().getBestActionProbabilities(),c.getState().getIsActive())));
    }
}
