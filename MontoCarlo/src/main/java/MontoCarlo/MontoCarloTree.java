package MontoCarlo;


import ChessBoard.Common.Interfaces.Move;
import MontoCarlo.interfaces.IMontoCarloTree;
import MontoCarlo.tree.Node;
import MontoCarlo.tree.Tree;
import NeuralNet.Interfaces.INeuralNetwork;
import NeuralNet.Models.NNOutput;


public class MontoCarloTree implements IMontoCarloTree {

    /**
     * Network used to evaluate board states
     */
    INeuralNetwork nn;

    public MontoCarloTree(INeuralNetwork neuralNetwork) {
        if(State.getMovesOptions() == null){
            State.setMovesOptions(AllPieceMoveOptions.getMoveOptions());
        }

        nn = neuralNetwork;
    }


    public MontoCarloTree() {
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
        rootNode.getState().setPlayerColor(gameState.getPlayerColor());

        //Perform 4 step mcst process until time is exceeded
        MCSTProcess(gameState, gameState.getPlayerColor(), end, rootNode);
        //child of root which has highest value is the winner
        Node winnerNode = rootNode.getChildWithMaxScore();

        tree.setRoot(winnerNode);

        if(gameState.getPlayerColor() == Color.White){
            return winnerNode.getState().getChessMove();

        }else {
            return ConvertMoveToBlack.Convert(winnerNode.getState().getChessMove());

        }


    }

    /**
     * Returns a TrainingOutput with the best determined move fro mthe current BoardState
     * @param boardState board state to be evlauated
     * @param searchTime length of time the monto carlo search MontoCarlo.tree should search
     * @return MontoCarloTrainingOutput which holds move object and genrated policy by monto carlo search MontoCarlo.tree
     */
    public MontoCarloTrainingOutput findNextMoveTraining(BoardState boardState, long searchTime) {

        long start = System.currentTimeMillis();
        long end = start + searchTime;

        if(boardState.getPlayerColor() == Color.Black) {
            //input is always in perspective of the white player, moves are flipped for black player when outputing move
            boardState.setBoard(ConvertBoardToWhite.Convert(boardState.getBoard()));
        }

        //set Up root of MontoCarlo.tree
        Tree tree = new Tree();
        Node rootNode = tree.getRoot();
        rootNode.setState(new State(boardState));
        rootNode.getState().setIsActive(true);
        rootNode.getState().setPlayerColor(boardState.getPlayerColor());


        //Perform 4 step mcst process until time is exceeded
        MCSTProcess(boardState, boardState.getPlayerColor(), end, rootNode);

        //child of root which has highest value is the winner
        Node winnerNode = rootNode.getChildWithMaxScore();

        tree.setRoot(winnerNode);

        MontoCarloTrainingOutput output;

        if(boardState.getPlayerColor() == Color.White){
            output = new MontoCarloTrainingOutput(new TrainingSample(),winnerNode.getState().getChessMove());
        }else {
            output = new MontoCarloTrainingOutput(new TrainingSample(), ConvertMoveToBlack.Convert(winnerNode.getState().getChessMove()));
        }

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
        output.getSample().setBoard(boardState.getBoard());
        output.getSample().setPlayerColor(boardState.getPlayerColor());

       return output;


    }

    private void MCSTProcess(Color playerColor, long endTime, Node rootNode) {
        NNOutput nnOutput;


        while (System.currentTimeMillis() < endTime) {


            // Phase 1 - Selection

            Node promisingNode = selectPromisingNode(rootNode);


            // Phase 2 - Expansion
            expandNode(promisingNode);

            // Phase 3 - Neural Network evaluation
                nnOutput = nn.evaluate(promisingNode.getState().getGameState().ConvertToNeuralNetInput()).get(0);

         /*   System.out.println();
            for (int i = 0; i < nnOutput.getProbabilities().length; i++) {
                System.out.print(i+": "+nnOutput.getProbabilities()[i]+" ");
            }*/


            AssignProbabilities(promisingNode, nnOutput.getProbabilities());


            // Phase 4 - Update

            backPropogation(promisingNode, playerColor, nnOutput);


        }
    }

    private void AssignProbabilities(Node node, double[] probabilities) {
        for(Node child:node.getChildArray()){
            child.getState().setBestMoveProbability(child.getState().getIdMove());
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


           for(State state: possibleStates){

               if(state.getIsActive()) {
                   Node newNode = new Node(state);
                   newNode.setParent(node);
                   node.getChildArray().add(newNode);
               }
           }




    }

    private void backPropogation(Node nodeToExplore, Color playerColor, NNOutput nnOutput) {
        Node tempNode = nodeToExplore;
        while (tempNode != null) {

            tempNode.getState().incrementVisit();
            if (tempNode.getState().getPlayerColor() == playerColor)
                tempNode.getState().updateWinScore(nnOutput.getWin_score());
            tempNode = tempNode.getParent();
        }
    }

}