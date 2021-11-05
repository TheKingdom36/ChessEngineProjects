package NeuralNetwork.NNBuilders;

import NeuralNetwork.Block.*;
import NeuralNetwork.ActivationFunctions.ActivationFunction;
import NeuralNetwork.Block.Output.OutputBlock;
import NeuralNetwork.Operations.FlattenOp;
import NeuralNetwork.Utils.Dim3Struct;

import java.util.ArrayList;

public class ConvNNBuilder extends NNBuilder<ConvNNBuilder> implements IConvNNBuilder {

    @Override
    public ConvNNBuilder addConvBlock(int stride ,int padding ,int kernelWidth ,int kernelLength ,int numOfKernels ,ActivationFunction func){

        ConvolutionalBlock block;


        if (lastBlock == null && inputProvided == true) {
            //only input has been provided
            block = new ConvolutionalBlock(neuralNetwork.getInputBlock().getOutput().getDims() ,stride,padding,kernelWidth,kernelLength,numOfKernels,func);

        } else {
            lastBlock.setUp();
            block = new ConvolutionalBlock(((Dim3Struct)(lastBlock.getOutput())).getDims(),stride,padding,kernelWidth,kernelLength,numOfKernels,func);

        }

        lastBlock = block;
        neuralNetwork.addBlock(block);


        return this;
    }

    @Override
    public ConvNNBuilder addFullyConnectedBlock(int numOfNeurons ,ActivationFunction function) {
        FullyConnectedBlock block;

        lastBlock.setUp();
        if (lastBlock == null && inputProvided == true) {
            //only input has been provided
            block = new FullyConnectedBlock(neuralNetwork.getInputBlock().getOutput().totalNumOfValues(), numOfNeurons, function);
        } else {

            block = new FullyConnectedBlock(((Dim3Struct)lastBlock.getOutput()).totalNumOfValues(), numOfNeurons, function);
        }

        if (lastBlock instanceof ConvolutionalBlock) {
            lastBlock.addToPostNeuronOperations(new FlattenOp());

        }

        lastBlock = block;
        neuralNetwork.addBlock(block);

        return this;
    }

    @Override
    public ConvNNBuilder addWeights(Dim3Struct weights){
        if(lastBlock instanceof FullyConnectedBlock || lastBlock instanceof OutputBlock){
            lastBlock.setWeights(weights);
        }else if(lastBlock instanceof ConvolutionalBlock){
            throw new RuntimeException("A convoutional Block requires a list of Dim3struct");
        }

        return this;
    }


    @Override
    public ConvNNBuilder addWeights(ArrayList<Dim3Struct> weights){
        if(lastBlock instanceof FullyConnectedBlock ){
            throw new RuntimeException("A fully connected Block requires a single Dim3struct");
        }else if(lastBlock instanceof ConvolutionalBlock){
            lastBlock.setWeights(weights);
        }

        return this;
    }

}
