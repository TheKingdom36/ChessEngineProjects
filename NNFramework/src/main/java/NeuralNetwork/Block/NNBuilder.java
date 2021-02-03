package NeuralNetwork.Block;

import NeuralNetwork.Block.ActivationFunctions.ActivationFunction;
import NeuralNetwork.Block.Operations.BlockOperation;
import NeuralNetwork.Block.Operations.FlattenOp;

public class NNBuilder {

    NeuralNetwork<LearningRule> neuralNetwork;

    WeightBlock lastBlock;
    boolean inputProvided=false;

    public void addInput(Dim3Struct input){
        neuralNetwork.setInput(input);
        inputProvided=true;
    }
    public void addInput(double[] input){
        neuralNetwork.setInput(new Dim3Struct(input.length,1,1));
        inputProvided = true;
    }


    public void addOutputLayer(int numOfOutputNeurons,LossFunction lossFunction){



        OutputBlock block = new OutputBlock(numOfOutputNeurons,lastBlock.getNeurons().totalNumOfValues(),lossFunction);

        if(lastBlock instanceof ConvolutionalBlock){
            block.addToPreNeuronOperations(new FlattenOp(lastBlock.getNeurons().totalNumOfValues()));
        }

        neuralNetwork.setOutputBlock(block);
        lastBlock=  block;
    }

    public NNBuilder addFullyConnectedBlock(ActivationFunction function) {
//TODO change to deal with different block types
        FullyConnectedBlock block = new FullyConnectedBlock(numberOfInput,lastBlock.getNeurons().totalNumOfValues(),function);


        if(lastBlock == null && inputProvided == true){
            //only input has been provided
            if(){

            }
        }else if(lastBlock instanceof ConvolutionalBlock){
            block.addToPreNeuronOperations(new FlattenOp(lastBlock.totalNumOfValues()));
        }

        lastBlock = block;
        neuralNetwork.addBlock(block);

        return this;
    }

    public NNBuilder withPreOperation(BlockOperation block){
        lastBlock.addToPreNeuronOperations(block);
        return this;
    }

    public NNBuilder withPostOperation(BlockOperation block){
        lastBlock.addToPostNeuronOperations(block);
        return this;
    }

    public NeuralNetwork getNeuralNetwork() {
        return null;
    }


}
