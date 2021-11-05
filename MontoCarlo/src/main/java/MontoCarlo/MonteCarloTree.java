package MontoCarlo;



import GameBoard.Common.Interfaces.Move;
import MontoCarlo.tree.Node;
import MontoCarlo.tree.Tree;



public class MonteCarloTree {

    /**
     * predictor used to evaluate game states
     */
    PolicyPredictor predictor;

    public MonteCarloTree(PolicyPredictor policyPredictor) {
        predictor = policyPredictor;
    }

    /**
     * Returns the best found move from a given boardState
     * @param gameState board state to be evaluated
     * @param searchTime length of time the monte carlo search MonteCarlo.tree should search
     * @return
     */
    public Move findNextMove(GameState gameState, long searchTime) throws InstantiationException, IllegalAccessException {

        long start = System.currentTimeMillis();
        long end = start + searchTime;

        Node<GameState> rootNode = new Node<GameState>();
        ((Node) rootNode).setState(gameState);
        rootNode.setIsActive(true);

        Tree tree = new Tree(rootNode);

        //Perform 4 step mcst process until time is exceeded
        MCSTProcess(gameState.getCurrentPlayerID(), end, rootNode);
        //child of root which has highest value is the winner
        Node<GameState> winnerNode = rootNode.getChildWithMaxScore();

        tree.setRoot(winnerNode);
            return winnerNode.getState().getMove();
        }

    /**
     * Returns a TrainingOutput with the best determined move from the current BoardState
     * @param gameState board state to be evaluated
     * @param searchTime length of time the monte carlo search MonteCarlo.tree should search
     * @return MonteCarloTrainingOutput which holds move object and generated policy by monte carlo search MonteCarlo.tree
     */
    public MCTrainingOutput findNextMoveTraining(GameState gameState,long searchTime) throws InstantiationException, IllegalAccessException {

        long start = System.currentTimeMillis();
        long end = start + searchTime;


        //set Up root of MonteCarlo.tree

        Node<GameState> rootNode = new Node<GameState>();
        ((Node) rootNode).setState(gameState);
        rootNode.setIsActive(true);

        Tree tree = new Tree(rootNode);



        //Perform 4 step mcst process until time is exceeded
        MCSTProcess(gameState.getCurrentPlayerID(), end, rootNode);

        //child of root which has highest value is the winner
        Node<GameState> winnerNode = rootNode.getChildWithMaxScore();

        tree.setRoot(winnerNode);

        MCTrainingOutput output;

        output = new MCTrainingOutput();

        output.setNextMove(winnerNode.getState().getMove());


        //final calculation of uct values to be returned as the generated policy
        double[] genPolicy = new double[predictor.getNumOfOutputStates()];
        double genPolicyTotal=0;

        for(Node<GameState> child: rootNode.getChildArray()){
            genPolicy[child.getState().getMoveID()] = UCT.uctValue(child.getState().getVisitCount(),child.getState().getWinScore(),child.getParent().getState().getVisitCount(),child.getState().getBestActionProbabilities(),child.getIsActive());
            genPolicyTotal += genPolicy[child.getState().getMoveID()];
        }

        for(Node<GameState> child: rootNode.getChildArray()){
            genPolicy[child.getState().getMoveID()] =genPolicy[child.getState().getMoveID()]/genPolicyTotal;

        }
        output.setPolicy(genPolicy);
        output.setBoard(gameState.getBoard());
        output.setPlayerID(gameState.getCurrentPlayerID());

       return output;


    }

    private void MCSTProcess(int playerID, long endTime, Node rootNode) {
        PolicyOutput policyOutput;


        while (System.currentTimeMillis() < endTime) {
            // Phase 1 - Selection

            Node promisingNode = selectPromisingNode(rootNode);


            // Phase 2 - Expansion
            expandNode(promisingNode);

            // Phase 3 - Neural Network evaluation
            policyOutput = predictor.evaluate(promisingNode.getState());
            AssignProbabilities(promisingNode, policyOutput.getProbabilities());

            // Phase 4 - Update
            backPropagation(promisingNode, playerID, policyOutput);
        }
    }

    private void AssignProbabilities(Node<GameState> node, double[] probabilities) {
        for(Node<GameState> child:node.getChildArray()){
            child.getState().setBestActionProbabilities(probabilities[child.getState().getMoveID()]);
        }
    }

    private Node selectPromisingNode(Node rootNode) {
        Node node = rootNode;


        while (node.getChildArray().size() != 0) {
            node = UCT.findNodeWithHighestUCT(node);
        }

        return node;
    }

    private void expandNode(Node node) {

           GameState[] possibleNodeStates = node.getState().getAllPossibleStates();

           Node newNode;

           for(GameState gameState : possibleNodeStates){


                   newNode = new Node(gameState);
                   newNode.setParent(node);
                   node.getChildArray().add(newNode);

           }

    }

    private void backPropagation(Node nodeToExplore, int playerID, PolicyOutput policyOutput) {
        Node<GameState> tempNode = nodeToExplore;
        while (tempNode != null) {

            tempNode.getState().incrementVisit();
            if (tempNode.getState().getCurrentPlayerID()== playerID)
                tempNode.getState().updateWinScore(policyOutput.getWinScore());
            tempNode = tempNode.getParent();
        }
    }

}