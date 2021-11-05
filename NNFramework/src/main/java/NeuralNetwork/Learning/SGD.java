package NeuralNetwork.Learning;


import NeuralNetwork.Block.PolicyNeuralNetwork;
import NeuralNetwork.Block.ValuePolicyNetwork;
import NeuralNetwork.Utils.*;

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
    public void doLearningEpoch(NetworkDataSet trainingSet) {


        NetworkRow sample;
        Iterator<NetworkRow> iterator = trainingSet.iterator();
        int count =0;
        while (iterator.hasNext()) {

            sample = iterator.next();


            //go through network
            (NeuralNetwork).evaluate((sample).getInput());



            //System.out.println(neuralNetwork.Blocks.get(0).getOutputNeurons());
            //grab loss
            (NeuralNetwork).loss((sample).getExpectedOutput());

           //System.out.println("Loss per sample: " + neuralNetwork.loss(sample.getExpectedOutput()));

            //calculate change in weights
            NeuralNetwork.calculateWeightErrors();


            //update weights using SGD method
            NeuralNetwork.updateWeights((double weightValue,double weightDelta)->(weightValue - this.learningRate*weightDelta));

            count++;
            NeuralNetwork.resetErrors();
        }
    }
}
