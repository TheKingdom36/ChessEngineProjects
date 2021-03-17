package NeuralNetwork.Block;


import java.util.ArrayList;
import java.util.Iterator;

public class SGD extends IterativeLearning {

    private ArrayList<Double> lossPerSample;
    private double lossPerEpoch=0.0;

    public SGD(){
        lossPerSample = new ArrayList<>();
    }

    @Override
    public void beforeEpoch(){
lossPerSample.clear();
    }

    @Override
    public void afterEpoch(){

        System.out.println("Final Loss end of epoch: " + lossPerEpoch);
        lossPerEpoch=0;
    }
    @Override
    public void onStop(){


    }



    @Override
    public void doLearningEpoch(DataSet trainingSet) {


        DataSetSample sample;
        Iterator<DataSetSample> iterator = trainingSet.iterator();
        int count =0;
        while (iterator.hasNext()) {

            sample = iterator.next();


            //go through network
            double[] neuralNetworkOutput = neuralNetwork.evaluate(sample.getInput());


            //grab loss
            double temp = neuralNetwork.loss(sample.getExpectedOutput());
            lossPerSample.add(temp);
            lossPerEpoch += temp;


           //System.out.println("Loss per sample: " + neuralNetwork.loss(sample.getExpectedOutput()));

            //calculate change in weights
            neuralNetwork.calculateWeightErrors();
            //update weights using SGD method
            neuralNetwork.updateWeights((double weightValue, double weightDelta)->(weightValue - this.learningRate*weightDelta));
//            System.out.println(neuralNetwork.getOutputBlock().getWeightErrors().toString());

            //System.out.println("Weight error: "+ this.neuralNetwork.getOutputBlock().getWeightErrors());
            //System.out.println("weight error FC: " + this.neuralNetwork.Blocks.get(0).getWeightErrors());

            //set weight and neuron errors to zero
            //System.out.println(count);
            count++;
            neuralNetwork.resetErrors();
        }
    }
}
