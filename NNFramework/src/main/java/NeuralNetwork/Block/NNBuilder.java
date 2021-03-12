package NeuralNetwork.Block;

import NeuralNetwork.Block.ActivationFunctions.ActivationFunction;
import NeuralNetwork.Block.Operations.BlockOperation;
import NeuralNetwork.Block.Operations.FlattenOp;

public class NNBuilder {

    NeuralNetwork<LearningRule> neuralNetwork;

    WeightBlock lastBlock;
    boolean inputProvided=false;

    public NNBuilder(){
        neuralNetwork = new NeuralNetwork<>();
    }

    public NNBuilder addInputBlock(Dim3Struct.Dims inputSize){
        neuralNetwork.setInputBlock(new InputBlock(inputSize));
        inputProvided=true;
        return this;
    }
    public NNBuilder addInputBlock(double[] input){
        return addInputBlock(new Dim3Struct.Dims(input.length,1,1));

    }

    public NNBuilder provideLearningRule(LearningRule rule){
        neuralNetwork.setLearningRule(rule);
        return this;
    }


    public NNBuilder addOutputLayer(int numOfOutputNeurons,LossFunction lossFunction){
        lastBlock.setUp();
        OutputBlock block = new OutputBlock(numOfOutputNeurons,lastBlock.getOutputNeurons().totalNumOfValues(),lossFunction);

        if(lastBlock instanceof ConvolutionalBlock){
            block.addToPreNeuronOperations(new FlattenOp());
        }

        neuralNetwork.setOutputBlock(block);
        lastBlock=  block;
        return this;
    }

    public NNBuilder addFullyConnectedBlock(int numOfNeurons, ActivationFunction function) {
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



    public NNBuilder addWeights(Dim3Struct weights){
        lastBlock.setWeights(weights);
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

    public NNBuilder setWeights(Dim3Struct weights){
        lastBlock.setWeights(weights);
        return this;
    }

    public NeuralNetwork build() {
        if(neuralNetwork.getLearningRule() == null){
            neuralNetwork.setLearningRule(new SGD());
        }

        neuralNetwork.setUp();

        return neuralNetwork;
    }


}
