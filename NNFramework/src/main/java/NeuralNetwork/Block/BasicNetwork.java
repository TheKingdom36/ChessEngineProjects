package NeuralNetwork.Block;

import NeuralNetwork.Block.Output.OutputBlock;
import NeuralNetwork.Learning.LearningRule;
import NeuralNetwork.Utils.Dim4Struct;
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
    public void addBlocks(List<FeatureBlock> blocks){
        this.blocks.addAll(blocks);
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
            blocks.get(blocks.size()-1).calculateErrors(inputBlock.getOutputNeurons(), outputBlock.getNeuronErrors(),outputBlock.getWeights());
        }else{
            blocks.get(blocks.size() - 1).calculateErrors(blocks.get(blocks.size() -2 ).getOutput(),outputBlock.getNeuronErrors(),outputBlock.getWeights());

            for(int i = blocks.size()-2; i>=1; i--){
                blocks.get(i).calculateErrors(blocks.get(i-1).getOutput(),blocks.get(i+1).getNeuronErrors(),blocks.get(i+1).getWeights());
            }

            blocks.get(0).calculateErrors(inputBlock.getOutputNeurons(),blocks.get(1).getNeuronErrors(),blocks.get(1).getWeights() );

        }
    }

    @Override
    public List<double[]> evaluate(Dim4Struct input) {






        blocks.get(0).calculate(inputBlock.calculate(input));

        for (int i = 1; i< blocks.size() ; i++) {
            blocks.get(i).calculate(blocks.get(i-1).getOutput());
        }

        outputBlock.calculate(blocks.get(blocks.size()-1).getOutput());

        ArrayList<double[]> output = new ArrayList<>();

        //This probably wont work, becuase shape of outputs will be different need a function to produce
        //actaully can be jagged array so should be fine, just need to be careful when definsing array

        //after a branch block put a Flatton operation if not the output layer


        double[] outArr = new double[outputBlock.getOutput().getValues()[0][0].length];
        for(int i=0;i<outputBlock.getOutput().getValues()[0][0].length;i++){
            outArr[i] = outputBlock.getOutput().getValues()[0][0][i][0];
        }

        output.add(outArr);

        return  output;
    }

    @Override
    public List<double[]> evaluate(double[] inputs) {
        Dim4Struct dim4Struct = new Dim4Struct(inputBlock.getOutput().getDims());
        dim4Struct.populate(inputs);
        return evaluate(dim4Struct);
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
