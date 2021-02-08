package NeuralNetwork.Block;


import java.util.ArrayList;
import java.util.Iterator;

public class SGD extends IterativeLearning {

    private ArrayList<Double> lossPerSample;

    public SGD(){
        lossPerSample = new ArrayList<>();
    }

    @Override
    public void beforeEpoch(){
lossPerSample.clear();
    }

    @Override
    public void afterEpoch(){
        System.out.println("Final Loss end of epoch: " + lossPerSample.get(lossPerSample.size()-1));
    }


    @Override
    public void doLearningEpoch(DataSet trainingSet) {


        DataSetSample sample;
        Iterator<DataSetSample> iterator = trainingSet.iterator();
        while (iterator.hasNext()) {

            sample = iterator.next();

            //go through network
            neuralNetwork.evaluate(sample.getInput());

            //grab loss
            lossPerSample.add(neuralNetwork.loss(sample.getExpectedOutput()));
            System.out.println("Loss per sample: " + neuralNetwork.loss(sample.getExpectedOutput()));

            //calculate change in weights
            neuralNetwork.calculateWeightErrors();

            //update weights using SGD method
            neuralNetwork.updateWeights((double weightValue, double weightDelta)->(weightValue + this.learningRate*weightDelta));

            //set weight and neuron errors to zero
            neuralNetwork.resetWeightErrors();
        }


    }
}
