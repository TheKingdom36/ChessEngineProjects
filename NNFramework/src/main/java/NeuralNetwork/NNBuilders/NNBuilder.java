package NeuralNetwork.NNBuilders;

import NeuralNetwork.Block.*;
import NeuralNetwork.ActivationFunctions.ActivationFunction;
import NeuralNetwork.Block.Output.BasicOutputBlock;
import NeuralNetwork.Block.Output.MultiOutputBlock;
import NeuralNetwork.Block.Output.OutputBlock;
import NeuralNetwork.Learning.LearningRule;
import NeuralNetwork.Learning.SGD;
import NeuralNetwork.LossFunctions.LossFunction;
import NeuralNetwork.Operations.BlockOperation;
import NeuralNetwork.Utils.Dim3Struct;

import java.util.ArrayList;
import java.util.List;

public class NNBuilder<Type extends INNBuilder> implements INNBuilder {

    protected BasicNetwork neuralNetwork;

    protected WeightBlock lastBlock;

    private List<OutputBlock> outputBlocks;
    boolean inputProvided=false;


    public NNBuilder(){
        outputBlocks = new ArrayList<>();
    }


    @Override
    public Type addInputBlock(Dim3Struct.Dims inputSize){
        neuralNetwork.setInputBlock(new InputBlock(inputSize));
        inputProvided=true;
        return (Type)this;
    }
    @Override
    public Type addInputBlock(double[] input){
        return addInputBlock(new Dim3Struct.Dims(input.length,1,1));

    }

    @Override
    public Type provideLearningRule(LearningRule rule){
        neuralNetwork.setLearningRule(rule);
        return (Type)this;
    }


    @Override
    public Type addOutputBlock(OutputBlock block){
        lastBlock.setUp();

        outputBlocks.add(block);

        return (Type)this;
    }


    @Override
    public Type addOutputBlock(int numOfOutputNeurons, LossFunction lossFunction){
        lastBlock.setUp();

        BasicOutputBlock block = new BasicOutputBlock(numOfOutputNeurons,((Dim3Struct)lastBlock.getOutput()).totalNumOfValues(),lossFunction);

        outputBlocks.add(block);

        return (Type)this;
    }



    @Override
    public Type addFullyConnectedBlock(int numOfNeurons ,ActivationFunction function) {
        FullyConnectedBlock block;


        if (lastBlock == null && inputProvided == true) {
            //only input has been provided
            block = new FullyConnectedBlock(neuralNetwork.getInputBlock().getOutput().totalNumOfValues(), numOfNeurons, function);
        } else {
            lastBlock.setUp();
            block = new FullyConnectedBlock(((Dim3Struct)lastBlock.getOutput()).totalNumOfValues(), numOfNeurons, function);
        }

        lastBlock = block;
        neuralNetwork.addBlock(block);

        return (Type)this;
    }



    @Override
    public Type addWeights(Dim3Struct weights){
        lastBlock.setWeights(weights);
        return (Type)this;
    }


    @Override
    public Type withPostOperation(BlockOperation block){
        lastBlock.addToPostNeuronOperations(block);
        return (Type)this;
    }

    @Override
    public Type generateWeights(){


        return (Type)this;
    }




    @Override
    public BasicNetwork build() {
        if(neuralNetwork.getLearningRule() == null){
            neuralNetwork.setLearningRule(new SGD());
        }

        outputBlocks.forEach(o->o.setUp());

        if(outputBlocks.size()>1){
            neuralNetwork.setOutputBlock(new MultiOutputBlock(outputBlocks));
        }else {
            neuralNetwork.setOutputBlock(outputBlocks.get(0));
        }

        lastBlock.setUp();
        neuralNetwork.setUp();

        return neuralNetwork;
    }





    public enum WeightInitializers {uniform,xavier}
}
