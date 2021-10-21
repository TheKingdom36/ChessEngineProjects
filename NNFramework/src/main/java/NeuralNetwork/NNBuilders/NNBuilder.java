package NeuralNetwork.NNBuilders;

import NeuralNetwork.Block.*;
import NeuralNetwork.ActivationFunctions.ActivationFunction;
import NeuralNetwork.Learning.LearningRule;
import NeuralNetwork.Learning.SGD;
import NeuralNetwork.LossFunctions.LossFunction;
import NeuralNetwork.Operations.BlockOperation;
import NeuralNetwork.Utils.Dim3Struct;

public class NNBuilder<Type extends NNBuilder<Type>> {

    protected NeuralNetwork<LearningRule,double[]> neuralNetwork;

    protected WeightBlock lastBlock;
    boolean inputProvided=false;

    public NNBuilder(){
        neuralNetwork = new NeuralNetwork<>();
    }



    public Type addInputBlock(Dim3Struct.Dims inputSize){
        neuralNetwork.setInputBlock(new InputBlock(inputSize));
        inputProvided=true;
        return (Type)this;
    }
    public Type addInputBlock(double[] input){
        return addInputBlock(new Dim3Struct.Dims(input.length,1,1));

    }

    public Type provideLearningRule(LearningRule rule){
        neuralNetwork.setLearningRule(rule);
        return (Type)this;
    }


    public Type addOutputLayer(int numOfOutputNeurons,LossFunction lossFunction){
        lastBlock.setUp();
        BasicOutputBlock block = new BasicOutputBlock(numOfOutputNeurons,((Dim3Struct)lastBlock.getOutputNeurons()).totalNumOfValues(),lossFunction);


        neuralNetwork.setBasicOutputBlock(block);
        lastBlock=  block;
        return (Type)this;
    }

    public Type addFullyConnectedBlock(int numOfNeurons, ActivationFunction function) {
        FullyConnectedBlock block;


        if (lastBlock == null && inputProvided == true) {
            //only input has been provided
            block = new FullyConnectedBlock(neuralNetwork.getInputBlock().getOutputNeurons().totalNumOfValues(), numOfNeurons, function);
        } else {
            lastBlock.setUp();
            block = new FullyConnectedBlock(((Dim3Struct)lastBlock.getOutputNeurons()).totalNumOfValues(), numOfNeurons, function);
        }

        lastBlock = block;
        neuralNetwork.addBlock(block);

        return (Type)this;
    }



    public Type addWeights(Dim3Struct weights){
        lastBlock.setWeights(weights);
        return (Type)this;
    }


    public Type withPostOperation(BlockOperation block){
        lastBlock.addToPostNeuronOperations(block);
        return (Type)this;
    }

    public Type generateWeights(){


        return (Type)this;
    }



    public NeuralNetwork build() {
        if(neuralNetwork.getLearningRule() == null){
            neuralNetwork.setLearningRule(new SGD());
        }
        lastBlock.setUp();
        neuralNetwork.setUp();

        return neuralNetwork;
    }


    public enum WeightIntialiazers {uniform,xavier}
}
