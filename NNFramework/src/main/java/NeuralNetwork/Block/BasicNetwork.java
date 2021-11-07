package NeuralNetwork.Block;

import NeuralNetwork.Block.Output.OutputBlock;
import NeuralNetwork.Learning.LearningRule;
import NeuralNetwork.Utils.Dim3Struct;
import NeuralNetwork.Utils.NetworkDataSet;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public abstract class BasicNetwork<LRule extends LearningRule> implements INeuralNetwork<LRule> {
    @Getter
    @Setter
    InputBlock inputBlock;
    @Getter
    OutputBlock outputBlock;
    @Getter
    LRule learningRule;


    ArrayList<FeatureBlock> blocks = new ArrayList<>();



    public double loss(List<double[]> expected){

        return outputBlock.calculateLossFunc(expected);
    }

    @Override
    public void addBlock(FeatureBlock block){
        blocks.add(block);
    }

    @Override
    public void learn(NetworkDataSet dataSet){
        if (dataSet == null) {
            throw new IllegalArgumentException("Training set is null!");
        }

        learningRule.learn(dataSet);
    }

    /**
     * Sets learning algorithm for this network
     *
     * @param learningRule learning algorithm for this network
     */
    @Override
    public void setLearningRule(LRule learningRule) {
        if (learningRule == null) {
            throw new IllegalArgumentException("Learning rule can't be null!");
        }

        learningRule.setNeuralNetwork(this);
        this.learningRule = learningRule;
    }

    @Override
    public void updateWeights(WeightUpdateRule rule) {
        outputBlock.updateWeights(rule);

        for(int i = 0; i< blocks.size(); i++){
            blocks.get(i).updateWeights(rule);
        }

    }

    @Override
    public void resetErrors() {
        outputBlock.resetErrors();
        for(int i = 0; i< blocks.size(); i++){
            blocks.get(i).resetErrors();
        }
    }

    @Override
    public void calculateWeightErrors() {

        outputBlock.calculateErrors(blocks.get(blocks.size()-1));

        if(blocks.size() == 1){
            blocks.get(blocks.size()-1).calculateErrors(inputBlock, outputBlock);
        }else{
            blocks.get(blocks.size() - 1).calculateErrors(blocks.get(blocks.size() -2 ),outputBlock);


            for(int i = blocks.size()-2; i>=1; i--){
                blocks.get(i).calculateErrors(blocks.get(i-1),blocks.get(i+1));
            }

            blocks.get(0).calculateErrors(inputBlock,blocks.get(1) );

        }
    }

    @Override
    public List<double[]> evaluate(Dim3Struct input) {


        inputBlock.setOutputNeurons(input);

        blocks.get(0).calculate(inputBlock.getOutput());

        for (int i = 1; i< blocks.size() ; i++) {
            blocks.get(i).calculate(blocks.get(i-1).getOutput());
        }

        outputBlock.calculate(blocks.get(blocks.size()-1).getOutput());

        return (List<double[]>) outputBlock.getOutput();
    }

    @Override
    public List<double[]> evaluate(double[] inputs) {
        Dim3Struct dim3Struct = new Dim3Struct(inputBlock.getOutput().getDims());
        dim3Struct.populate(inputs);
        return evaluate(dim3Struct);
    }

    @Override
    public OutputBlock getOutputBlock(){
        return outputBlock;
    }

    @Override
    public void setOutputBlock(OutputBlock outputBlock) {
        this.outputBlock = outputBlock;
    }

}
