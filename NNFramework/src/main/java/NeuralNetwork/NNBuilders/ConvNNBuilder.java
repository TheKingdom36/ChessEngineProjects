package NeuralNetwork.NNBuilders;

import NeuralNetwork.Block.*;
import NeuralNetwork.ActivationFunctions.ActivationFunction;
import NeuralNetwork.Operations.FlattenOp;
import NeuralNetwork.Utils.Dim4Struct;

public class ConvNNBuilder extends NNBuilder<ConvNNBuilder> implements IConvNNBuilder {

    @Override
    public ConvNNBuilder addConvBlock(int stride ,int padding ,int kernelWidth ,int kernelLength ,int numOfKernels ,ActivationFunction func){

        ConvolutionalBlock block;


        if (lastBlock == null && inputProvided == true) {
            //only input has been provided
            block = new ConvolutionalBlock(neuralNetwork.getInputBlock().getOutput().getDims() ,stride,padding,kernelWidth,kernelLength,numOfKernels,func);

        } else {
            lastBlock.setUp();
            block = new ConvolutionalBlock(((lastBlock.getOutput())).getDims(),stride,padding,kernelWidth,kernelLength,numOfKernels,func);

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

            block = new FullyConnectedBlock((lastBlock.getOutput()).totalNumOfValues(), numOfNeurons, function);
        }

        if (lastBlock instanceof ConvolutionalBlock) {
            lastBlock.addToPostCalculationOperations(new FlattenOp());

        }

        lastBlock = block;
        neuralNetwork.addBlock(block);

        return this;
    }

    @Override
    public ConvNNBuilder addWeights(Dim4Struct weights){
        lastBlock.setWeights(weights);

        return this;
    }




}
