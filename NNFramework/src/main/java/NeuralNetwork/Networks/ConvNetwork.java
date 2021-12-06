package NeuralNetwork.Networks;

import NeuralNetwork.ActivationFunctions.ActivationFunction;
import NeuralNetwork.Block.*;
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
        super.setUp();
    }

    public static class builder{
        protected ConvNetwork neuralNetwork;

        protected FeatureBlock lastBlock;
        protected FeatureBlock lastFeatureBlock;

        boolean inputProvided=false;

        private List<FeatureBlock> blocks;


        public builder(){
            neuralNetwork = new ConvNetwork();

            blocks = new ArrayList<>();
        }

        public builder addLossFunction(LossFunction lossFunction){
            neuralNetwork.setLossFunction(lossFunction);
            return this;
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

        public builder addOperationBlock(){
            lastBlock.setUp();

            OperationBlock block = new OperationBlock(lastBlock.getOutput().getDims());

            lastBlock = block;
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


        /*
        interface AnonBuilderFunction{
            public Branch(InputDims dims){
            }
        }
         */
        public builder addBranchBlock(BranchBlockCreator creator){

            lastBlock.setUp();

            creator.create(lastBlock.getOutput());


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

            lastBlock.setUp();

            neuralNetwork.addBlocks(blocks);
            neuralNetwork.setUp();

            return neuralNetwork;
        }
    }
}
