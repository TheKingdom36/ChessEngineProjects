package NeuralNetwork.Block;

public class MaxIterationsStop implements StopCondition {

    IterativeLearning learningRule;

    public MaxIterationsStop(IterativeLearning iterativeLearning) {
        learningRule = iterativeLearning;
    }

    @Override
    public boolean isReached() {
        if(learningRule.getCurrentIteration() >= learningRule.getMaxIterations()){
            return true;
        }else{
            return false;
        }
    }
}
