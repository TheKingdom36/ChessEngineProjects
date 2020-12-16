package MontoCarlo;



import GameBoard.Common.Interfaces.Move;
import MontoCarlo.interfaces.IMontoCarloTree;
import MontoCarlo.tree.Node;
import MontoCarlo.tree.Tree;
import NeuralNet.Interfaces.INeuralNetwork;
import NeuralNet.Models.NNOutput;


public class MonteCarloTree implements IMontoCarloTree {

    /**
     * Network used to evaluate board states
     */
    INeuralNetwork nn;

    public MonteCarloTree(INeuralNetwork neuralNetwork) {
        if(State.getMovesOptions() == null){
            State.setMovesOptions(AllPieceMoveOptions.getMoveOptions());
        }

        nn = neuralNetwork;

    }


    public MonteCarloTree() {
        if(State.getMovesOptions() == null){
            State.setMovesOptions(AllPieceMoveOptions.getMoveOptions());
        }

        nn = new BasicNeuralNetwork();
    }


    /**
     * Returns the best found move from a given boardState
     * @param gameState board state to be evlauated
     * @param searchTime length of time the monto carlo search MontoCarlo.tree should search
     * @return
     */
    public Move findNextMove(GameState gameState, long searchTime) {

        long start = System.currentTimeMillis();
        long end = start + searchTime;


        Tree tree = new Tree();
        Node rootNode = tree.getRoot();
        ((Node) rootNode).setState(new State(gameState));
        rootNode.getState().setIsActive(true);

        //Perform 4 step mcst process until time is exceeded
        MCSTProcess(gameState.getCurrentPlayerID(), end, rootNode);
        //child of root which has highest value is the winner
        Node winnerNode = rootNode.getChildWithMaxScore();

        tree.setRoot(winnerNode);


            return winnerNode.getState().getMove();

        }

    /**
     * Returns a TrainingOutput with the best determined move fro mthe current BoardState
     * @param gameState board state to be evlauated
     * @param searchTime length of time the monto carlo search MontoCarlo.tree should search
     * @return MontoCarloTrainingOutput which holds move object and genrated policy by monto carlo search MontoCarlo.tree
     */
    public MonteCarloTrainingOutput findNextMoveTraining(GameState gameState, long searchTime) {

        long start = System.currentTimeMillis();
        long end = start + searchTime;


        //set Up root of MontoCarlo.tree
        Tree tree = new Tree();
        Node rootNode = tree.getRoot();
        rootNode.setState(new State(gameState));
        rootNode.getState().setIsActive(true);



        //Perform 4 step mcst process until time is exceeded
        MCSTProcess(gameState.getCurrentPlayerID(), end, rootNode);

        //child of root which has highest value is the winner
        Node winnerNode = rootNode.getChildWithMaxScore();

        tree.setRoot(winnerNode);

        MonteCarloTrainingOutput output;

        output = new MonteCarloTrainingOutput(new TrainingSample(),winnerNode.getState().getMove());


        //final calculation of uct values to be returned as the generated policy
        double[] genPolicy = new double[State.getMovesOptions().size()];
        double genPolicyTotal=0;

        for(Node child: rootNode.getChildArray()){
            genPolicy[child.getState().getIdMove()] = UCT.uctValue(child.getState().getVisitCount(),child.getState().getWinScore(),child.getParent().getState().getVisitCount(),child.getState().getBestMoveProbability(),child.getState().getIsActive());
            genPolicyTotal += genPolicy[child.getState().getIdMove()];
        }

        for(Node child: rootNode.getChildArray()){
            genPolicy[child.getState().getIdMove()] =genPolicy[child.getState().getIdMove()]/genPolicyTotal;

        }
        output.getSample().setPolicy(genPolicy);
        output.getSample().setBoard(gameState.getBoard());
        output.getSample().setPlayerID(gameState.getCurrentPlayerID());

       return output;


    }

    private void MCSTProcess(String playerID, long endTime, Node rootNode) {
        NNOutput nnOutput;


        while (System.currentTimeMillis() < endTime) {


            // Phase 1 - Selection

            Node promisingNode = selectPromisingNode(rootNode);


            // Phase 2 - Expansion
            expandNode(promisingNode);

            // Phase 3 - Neural Network evaluation
            nnOutput = nn.evaluate(promisingNode.getState().getGameState().convertToNeuralNetInput()).get(0);
            AssignProbabilities(promisingNode, nnOutput.getProbabilities());


            // Phase 4 - Update

            backPropagation(promisingNode, playerID, nnOutput);


        }
    }

    private void AssignProbabilities(Node node, double[] probabilities) {
        for(Node child:node.getChildArray()){
            child.getState().setBestMoveProbability(probabilities[child.getState().getIdMove()]);
        }
    }

    private Node selectPromisingNode(Node rootNode) {
        Node node = rootNode;


        while (node.getChildArray().size() != 0) {
            node = UCT.findBestNodeWithUCT(node);
        }

        return node;
    }

    private void expandNode(Node node) {

           State[] possibleStates = node.getState().getAllPossibleStates();

           Node newNode;

           for(State state: possibleStates){

               if(state.getIsActive()) {
                   newNode = new Node(state);
                   newNode.setParent(node);
                   node.getChildArray().add(newNode);
               }
           }

    }

    private void backPropagation(Node nodeToExplore, String playerID, NNOutput nnOutput) {
        Node tempNode = nodeToExplore;
        while (tempNode != null) {

            tempNode.getState().incrementVisit();
            if (tempNode.getState().getGameState().getCurrentPlayerID().equals(playerID))
                tempNode.getState().updateWinScore(nnOutput.getWin_score());
            tempNode = tempNode.getParent();
        }
    }

}