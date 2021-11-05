package NeuralNetwork.Networks;

import NeuralNetwork.ActivationFunctions.ActivationFunction;
import NeuralNetwork.Block.*;
import NeuralNetwork.Block.Output.BasicOutputBlock;
import NeuralNetwork.Block.Output.OutputBlock;
import NeuralNetwork.Block.Output.ValuePolicyOutputBlock;
import NeuralNetwork.Learning.LearningRule;
import NeuralNetwork.Learning.SGD;
import NeuralNetwork.LossFunctions.LossFunction;
import NeuralNetwork.NNBuilders.ConvNNBuilder;
import NeuralNetwork.Operations.BlockOperation;
import NeuralNetwork.Operations.FlattenOp;
import NeuralNetwork.Utils.Dim3Struct;

import java.util.ArrayList;
import java.util.List;

public class ConvNetwork extends BasicNetwork<LearningRule, List<double[]>> {
    @Override
    public void setUp() {

    }

    public static class builder{
        protected ConvNetwork neuralNetwork;

        protected WeightBlock lastBlock;
        boolean inputProvided=false;

        boolean outputBlockSet;

        public builder(){

            neuralNetwork = new ConvNetwork();
            outputBlockSet = false;
        }


        public builder addInputBlock(Dim3Struct.Dims inputSize){
            neuralNetwork.setInputBlock(new InputBlock(inputSize));
            inputProvided=true;
            return this;
        }

        public builder addInputBlock(double[] input){
            return addInputBlock(new Dim3Struct.Dims(input.length,1,1));

        }


        public builder provideLearningRule(LearningRule rule){
            neuralNetwork.setLearningRule(rule);
            return this;
        }


        public builder addOutputBlock(int numOfOutputNeurons ,LossFunction lossFunction){
            lastBlock.setUp();
            BasicOutputBlock block = new BasicOutputBlock(numOfOutputNeurons,((Dim3Struct)lastBlock.getOutput()).totalNumOfValues(),lossFunction);

            neuralNetwork.setOutputBlock(block);
            lastBlock=  block;

            outputBlockSet = true;
            return this;
        }


        public builder withPostOperation(BlockOperation block){
            lastBlock.addToPostNeuronOperations(block);
            return this;
        }

        public builder generateWeights(){


            return this;
        }

        public builder addConvBlock(int stride ,int padding ,int kernelWidth ,int kernelLength ,int numOfKernels ,ActivationFunction func){

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


        public builder addFullyConnectedBlock(int numOfNeurons ,ActivationFunction function) {
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


        public builder addWeights(Dim3Struct weights){
            if(lastBlock instanceof FullyConnectedBlock || lastBlock instanceof OutputBlock){
                lastBlock.setWeights(weights);
            }else if(lastBlock instanceof ConvolutionalBlock){
                throw new RuntimeException("A convoutional Block requires a list of Dim3struct");
            }

            return this;
        }



        public builder addWeights(ArrayList<Dim3Struct> weights){
            if(lastBlock instanceof FullyConnectedBlock ){
                throw new RuntimeException("A fully connected Block requires a single Dim3struct");
            }else if(lastBlock instanceof ConvolutionalBlock){
                lastBlock.setWeights(weights);
            }

            return this;
        }





        public ConvNetwork build() {
            if(neuralNetwork.getLearningRule() == null){
                neuralNetwork.setLearningRule(new SGD());
            }
            lastBlock.setUp();
            neuralNetwork.setUp();

            return neuralNetwork;
        }
    }
}
