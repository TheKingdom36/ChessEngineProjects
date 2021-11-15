package NeuralNetwork.Networks;

import NeuralNetwork.ActivationFunctions.ActivationFunction;
import NeuralNetwork.Block.*;
import NeuralNetwork.Block.Output.BasicOutputBlock;
import NeuralNetwork.Block.Output.OutputBlock;
import NeuralNetwork.Learning.LearningRule;
import NeuralNetwork.Learning.SGD;
import NeuralNetwork.LossFunctions.LossFunction;
import NeuralNetwork.Operations.BlockOperation;
import NeuralNetwork.Operations.FlattenOp;
import NeuralNetwork.Utils.Dim4Struct;

import java.util.ArrayList;
import java.util.List;

public class ConvNetwork extends BasicNetwork<LearningRule> {
    @Override
    public void setUp() {

    }

    public static class builder{
        protected ConvNetwork neuralNetwork;

        protected WeightBlock lastBlock;
        protected WeightBlock lastFeatureBlock;

        boolean inputProvided=false;

        private List<FeatureBlock> blocks;
        private List<OutputBlock> outputBlocks;

        public builder(){
            neuralNetwork = new ConvNetwork();
            outputBlocks = new ArrayList<>();
            blocks = new ArrayList<>();
        }


        public builder addInputBlock(Dim4Struct.Dims inputSize){
            neuralNetwork.setInputBlock(new InputBlock(inputSize));
            inputProvided=true;
            return this;
        }

        public builder addInputBlock(double[] input){
            return addInputBlock(new Dim4Struct.Dims(1,1,input.length,1));

        }


        public builder provideLearningRule(LearningRule rule){
            neuralNetwork.setLearningRule(rule);
            return this;
        }


        public builder addOutputBlock(int numOfOutputNeurons ,LossFunction lossFunction){

            if(outputBlocks.size()==0){
                lastFeatureBlock.setUp();
            }

            BasicOutputBlock block = new BasicOutputBlock(numOfOutputNeurons,( lastFeatureBlock.getOutput()).totalNumOfValues(),lossFunction);
            outputBlocks.add(block);

            lastBlock = block;

            return this;
        }


        public builder withPostOperation(BlockOperation block){

            if(lastBlock==null){
                neuralNetwork.getInputBlock().addToPostCalculationOperations(block);
                return this;
            }

            lastBlock.addToPostCalculationOperations(block);
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
                block = new ConvolutionalBlock(((lastBlock.getOutput())).getDims(),stride,padding,kernelWidth,kernelLength,numOfKernels,func);

            }

            lastBlock = block;
            lastFeatureBlock = block;

            blocks.add(block);


            return this;
        }


        public builder addFullyConnectedBlock(int numOfNeurons ,ActivationFunction function) {
            FullyConnectedBlock block;


            if (lastBlock == null && inputProvided == true) {
                //only input has been provided
                block = new FullyConnectedBlock(neuralNetwork.getInputBlock().getOutput().totalNumOfValues(), numOfNeurons, function);
            } else {
                lastBlock.setUp();
                block = new FullyConnectedBlock(( lastBlock.getOutput()).totalNumOfValues(), numOfNeurons, function);
            }

            if (lastBlock instanceof ConvolutionalBlock) {
                lastBlock.addToPostCalculationOperations(new FlattenOp());

            }

            lastBlock = block;
            lastFeatureBlock = block;
            blocks.add(block);

            return this;
        }


        public builder addWeights(Dim4Struct weights){

            lastBlock.setWeights(weights);

            return this;
        }




        public ConvNetwork build() {
            if(neuralNetwork.getLearningRule() == null){
                neuralNetwork.setLearningRule(new SGD());
            }

            outputBlocks.forEach(o->o.setUp());

            if(outputBlocks.size()>1){

            }else {
                neuralNetwork.setOutputBlock(outputBlocks.get(0));
            }

            neuralNetwork.addBlocks(blocks);
            neuralNetwork.setUp();

            return neuralNetwork;
        }
    }
}
