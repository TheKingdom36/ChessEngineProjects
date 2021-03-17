package NeuralNetwork.Block.NNBuilders;

import NeuralNetwork.Block.*;
import NeuralNetwork.Block.ActivationFunctions.ActivationFunction;
import NeuralNetwork.Block.Operations.BlockOperation;
import NeuralNetwork.Block.Operations.FlattenOp;

public class NNBuilder<Type extends NNBuilder<Type>> {

    protected NeuralNetwork<LearningRule> neuralNetwork;

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
        BasicOutputBlock block = new BasicOutputBlock(numOfOutputNeurons,lastBlock.getOutputNeurons().totalNumOfValues(),lossFunction);

        if(lastBlock instanceof ConvolutionalBlock){
            block.addToPreNeuronOperations(new FlattenOp());
        }

        neuralNetwork.setBasicOutputBlock(block);
        lastBlock=  block;
        return (Type)this;
    }

    public Type addFullyConnectedBlock(int numOfNeurons, ActivationFunction function) {
        FullyConnectedBlock block;


        if (lastBlock == null && inputProvided == true) {
            //only input has been provided
            block = new FullyConnectedBlock(neuralNetwork.getInputBlock().getNeurons().totalNumOfValues(), numOfNeurons, function);
        } else {
            lastBlock.setUp();
            block = new FullyConnectedBlock(lastBlock.getOutputNeurons().totalNumOfValues(), numOfNeurons, function);
        }

        lastBlock = block;
        neuralNetwork.addBlock(block);

        return (Type)this;
    }



    public Type addWeights(Dim3Struct weights){
        lastBlock.setWeights(weights);
        return (Type)this;
    }

    public Type withPreOperation(BlockOperation block){
        lastBlock.addToPreNeuronOperations(block);
        return (Type)this;
    }

    public Type withPostOperation(BlockOperation block){
        lastBlock.addToPostNeuronOperations(block);
        return (Type)this;
    }

    public Type setWeights(Dim3Struct weights){
        lastBlock.setWeights(weights);
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


}
