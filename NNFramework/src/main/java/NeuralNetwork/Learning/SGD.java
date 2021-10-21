package NeuralNetwork.Learning;


import NeuralNetwork.Block.NeuralNetwork;
import NeuralNetwork.Learning.IterativeLearning;
import NeuralNetwork.Utils.DataSet;
import NeuralNetwork.Utils.DataSetRow;

import java.util.Iterator;

public class SGD extends IterativeLearning {


    public SGD(){

    }

    @Override
    public void beforeEpoch(){

    }

    @Override
    public void afterEpoch(){

    }

    @Override
    public void onStop(){

    }



    @Override
    public void doLearningEpoch(DataSet trainingSet) {


        DataSetRow sample;
        Iterator<DataSetRow> iterator = trainingSet.iterator();
        int count =0;
        while (iterator.hasNext()) {

            sample = iterator.next();


            //go through network
            double[] neuralNetworkOutput = (double[]) neuralNetwork.evaluate(sample.getInput());


            //System.out.println(neuralNetwork.Blocks.get(0).getOutputNeurons());
            //grab loss
            neuralNetwork.loss(sample.getExpectedOutput());

           //System.out.println("Loss per sample: " + neuralNetwork.loss(sample.getExpectedOutput()));

            //calculate change in weights
            neuralNetwork.calculateWeightErrors();


            //update weights using SGD method
            neuralNetwork.updateWeights((double weightValue, double weightDelta)->(weightValue - this.learningRate*weightDelta));

            count++;
            neuralNetwork.resetErrors();
        }
    }
}
