package NeuralNetwork.Block.NNBuilders;

import NeuralNetwork.Block.ActivationFunctions.ActivationFunction;
import NeuralNetwork.Block.ConvolutionalBlock;
import NeuralNetwork.Block.Dim3Struct;
import NeuralNetwork.Block.FullyConnectedBlock;
import NeuralNetwork.Block.LossFunction;
import NeuralNetwork.Block.Operations.FlattenOp;

import java.util.ArrayList;

public class ConvNNBuilder extends NNBuilder<ConvNNBuilder> {

    public ConvNNBuilder addConvBlock(int stride,int padding,int kernelWidth ,int kernelLength,int numOfKernels,ActivationFunction func){

        ConvolutionalBlock block;

        if (lastBlock == null && inputProvided == true) {
            //only input has been provided
           // block = new ConvolutionalBlock((neuralNetwork.getInputBlock().getNeurons().totalNumOfValues(), numOfNeurons, lossFunction);
         //   new ConvolutionalBlock()
        } else {
            lastBlock.setUp();
            //block = new ConvolutionalBlock(lastBlock.getOutputNeurons().totalNumOfValues(), numOfNeurons, lossFunction);
           // numOfNeurons++;
        }

        //lastBlock = block;
        //neuralNetwork.addBlock(block);


        return this;
    }

    @Override
    public ConvNNBuilder addFullyConnectedBlock(int numOfNeurons, ActivationFunction function) {
        FullyConnectedBlock block;


        if (lastBlock == null && inputProvided == true) {
            //only input has been provided
            block = new FullyConnectedBlock(neuralNetwork.getInputBlock().getNeurons().totalNumOfValues(), numOfNeurons, function);
        } else {
            lastBlock.setUp();
            block = new FullyConnectedBlock(lastBlock.getOutputNeurons().totalNumOfValues(), numOfNeurons, function);
        }

        if (lastBlock instanceof ConvolutionalBlock) {
            block.addToPreNeuronOperations(new FlattenOp());
        }

        lastBlock = block;
        neuralNetwork.addBlock(block);

        return this;
    }

}
