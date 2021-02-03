package NeuralNetwork.Block;


import java.util.ArrayList;

public class SGD extends IterativeLearning {

    private ArrayList<Double> lossPerSample;

    @Override
    public void beforeEpoch(){

    }

    @Override
    public void afterEpoch(){

    }


    @Override
    public void doLearningEpoch(DataSet trainingSet) {


        DataSetSample sample;
        while (trainingSet.iterator().hasNext()) {
            sample = trainingSet.iterator().next();

            //go through network
            neuralNetwork.evaluate(sample.getInput());

            //grab loss
            lossPerSample.add(neuralNetwork.loss(sample.getExpectedOutput()));

            //calculate change in weights
            neuralNetwork.calculateWeightErrors();

            //update weights using SGD method
            neuralNetwork.updateWeights((double weightValue, double weightDelta)->(weightValue + this.learningRate*weightDelta));

            //set weight and neuron errors to zero
            neuralNetwork.resetWeightErrors();
        }

    }
}
