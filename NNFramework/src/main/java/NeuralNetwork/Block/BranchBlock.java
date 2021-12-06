package NeuralNetwork.Block;

import NeuralNetwork.ActivationFunctions.ActivationFunction;
import NeuralNetwork.ActivationFunctions.None;
import NeuralNetwork.LossFunctions.LossFunction;
import NeuralNetwork.Operations.BlockOperation;
import NeuralNetwork.Operations.FlattenOp;
import NeuralNetwork.Utils.Dim4Struct;
import NeuralNetwork.WeightIntializers.Uniform;
import NeuralNetwork.WeightIntializers.WeightInitializer;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class BranchBlock extends FeatureBlock {
    @Getter
    private ArrayList<FeatureBlock> topBlockPath;
    @Getter
    private ArrayList<FeatureBlock> bottomBlockPath;

    private CombineOperation combineOperation;


    public BranchBlock(){
        this.topBlockPath = new ArrayList<>();
        this.bottomBlockPath = new ArrayList<>();
    }

    public BranchBlock(ArrayList<FeatureBlock> topBlockPath, ArrayList<FeatureBlock> bottomBlockPath,Dim4Struct.Dims inputDims){
        this.topBlockPath = topBlockPath;
        this.bottomBlockPath = bottomBlockPath;
    }

    public void addToTopPath(FeatureBlock block){
        topBlockPath.add(block);
    }

    public void addToBottomPath(FeatureBlock block){
        bottomBlockPath.add(block);
    }

    @Override
    public void setUp() {



        verifyBlock();
    }

    public Dim4Struct.Dims numOfOutputsTopPath(){
        return topBlockPath.get(topBlockPath.size()-1).getOutput().getDims();
    }

    public Dim4Struct.Dims numOfOutputsBottomPath(){
        return bottomBlockPath.get(bottomBlockPath.size()-1).getOutput().getDims();
    }

    @Override
    public Dim4Struct calculate(Dim4Struct input) {


        topBlockPath.get(0).calculate(input);
        for (int i = 1; i< topBlockPath.size() ; i++) {
            topBlockPath.get(i).calculate(topBlockPath.get(i-1).getOutput());
        }


        bottomBlockPath.get(0).calculate(input);
        for (int i = 1; i< bottomBlockPath.size() ; i++) {
            bottomBlockPath.get(i).calculate(bottomBlockPath.get(i-1).getOutput());
        }

        Dim4Struct topOutput = topBlockPath.get(topBlockPath.size()-1).getOutput();
        Dim4Struct bottomOutput = bottomBlockPath.get(bottomBlockPath.size()-1).getOutput();

        return combineOperation(topOutput,bottomOutput);
    }

    @Override
    void calculateErrors(Dim4Struct previousBlockOutput, Dim4Struct nextBlockNeuronErrors, Dim4Struct nextBlockWeights) {

    }

    @Override
    void calculateErrorsWhenOutput(Dim4Struct previousBlockOutput, List<double[]> expectedArray, LossFunction lossFunction) {

    }

    @Override
    public void resetErrors() {

    }

    @Override
    public void addToPostCalculationOperations(BlockOperation operation) {

    }

    @Override
    public Dim4Struct getOutput() {
        return null;
    }

    @Override
    public void setWeights(Dim4Struct weights) {

    }

    @Override
    public void setWeightInitializer(WeightInitializer weightInitializer) {

    }

    @Override
    void setWeightErrors(Dim4Struct weightErrors) {

    }

    @Override
    void setOutputNeurons(Dim4Struct outputNeurons) {

    }


    @Override
    void setNeuronErrors(Dim4Struct neuronErrors) {

    }

    @Override
    void setInputNeuronsDims(Dim4Struct.Dims inputNeuronsDims) {

    }

    @Override
    public void clearWeightErrors() {

    }

    @Override
    public void updateWeights(WeightUpdateRule rule) {

    }

    @Override
    public Dim4Struct getWeights() {
        return null;
    }

    @Override
    WeightInitializer getWeightInitializer() {
        return null;
    }

    @Override
    Dim4Struct getWeightErrors() {
        return null;
    }

    @Override
    Dim4Struct getOutputNeurons() {
        return null;
    }

    @Override
    Dim4Struct getNeurons() {
        return null;
    }

    @Override
    public Dim4Struct getNeuronErrors() {
        return null;
    }

    @Override
    Dim4Struct.Dims getInputNeuronsDims() {
        return null;
    }


    public static class builder{

        BranchBlock branchBlock;

        //lastBlock and lastInitializer are for storing the last created top and bottom block to ensure the block is set up correctly before starting the next block
        FeatureBlock lastTopBlock;
        FeatureBlock lastBottomBlock;

        FeatureBlock leadBlock;
        PATH currentPath;

        public builder(Dim4Struct.Dims inputNeuronsDims){

            branchBlock = new BranchBlock();

            lastTopBlock = new OperationBlock(inputNeuronsDims);
            setUpBlock(lastTopBlock);
            branchBlock.addToTopPath(lastTopBlock);

            lastBottomBlock = new OperationBlock(inputNeuronsDims);
            setUpBlock(lastBottomBlock);
            branchBlock.addToBottomPath(lastBottomBlock);

        }

        public builder addFCToPath(int numOfNeurons, ActivationFunction function, PATH path){

            currentPath= path;
            FullyConnectedBlock block;

            if(path==PATH.TOP){

                if (!(lastTopBlock instanceof FullyConnectedBlock)) {
                    lastTopBlock.addToPostCalculationOperations(new FlattenOp());
                }

                if(lastTopBlock!=null) {


                    setUpBlock(lastTopBlock);

                    block = new FullyConnectedBlock((lastTopBlock.getOutput()).totalNumOfValues(), numOfNeurons, function);
                }else{
                    block = new FullyConnectedBlock((leadBlock.getOutput()).totalNumOfValues(), numOfNeurons, function);
                }
                lastTopBlock = block;

                branchBlock.addToTopPath(block);
            }else{

                if (!(lastBottomBlock instanceof FullyConnectedBlock)) {
                    lastBottomBlock.addToPostCalculationOperations(new FlattenOp());
                }

                setUpBlock(lastBottomBlock);

                block = new FullyConnectedBlock(( lastBottomBlock.getOutput()).totalNumOfValues(), numOfNeurons, function);

                lastBottomBlock = block;

                branchBlock.addToBottomPath(block);
            }
            return this;
        }

        public builder addConvToPath(int stride ,int padding ,int kernelWidth ,int kernelLength ,int numOfKernels ,ActivationFunction func,PATH path){

            currentPath= path;
            ConvolutionalBlock block;

            if(path==PATH.TOP){
                setUpBlock(lastTopBlock);

                block = new ConvolutionalBlock(((lastTopBlock.getOutput())).getDims(),stride,padding,kernelWidth,kernelLength,numOfKernels,func);

                lastTopBlock = block;

                branchBlock.addToTopPath(block);
            }else{
                setUpBlock(lastBottomBlock);

                block = new ConvolutionalBlock(((lastBottomBlock.getOutput())).getDims(),stride,padding,kernelWidth,kernelLength,numOfKernels,func);

                lastBottomBlock = block;

                branchBlock.addToBottomPath(block);
            }

            return this;
        }

        public builder withPostOperation(BlockOperation operation){

            if(currentPath == PATH.TOP){
                lastTopBlock.addToPostCalculationOperations(operation);
            }else{
                lastBottomBlock.addToPostCalculationOperations(operation);

            }

            return this;
        }

        public builder withWeights(Dim4Struct struct){

            if(currentPath == PATH.TOP){
                lastTopBlock.setWeights(struct);
            }else{
                lastBottomBlock.setWeights(struct);
            }

            return this;
        }


        public builder withWeightInitializer(WeightInitializer initializer){

            if(currentPath == PATH.TOP){
                lastTopBlock.setWeightInitializer(initializer);
            }else{
                lastBottomBlock.setWeightInitializer(initializer);
            }

            return this;
        }

        public builder withCombineOperation


        public BranchBlock build(){

            setUpBlock(lastBottomBlock);
            setUpBlock(lastTopBlock);

            return branchBlock;
        }

        public void setUpBlock(FeatureBlock block){

            if(block.getWeights() == null && block.getWeightInitializer()==null) {
                block.setWeightInitializer(new Uniform(-0.1,0.1));
            }

            block.setUp();

        }

        enum PATH {TOP,BOTTOM};
    }
}
