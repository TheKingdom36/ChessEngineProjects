package NeuralNetwork.Block;


import NeuralNetwork.Learning.LearningRule;
import NeuralNetwork.LossFunctions.LossFunction;
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
    LRule learningRule;

    //Multiple loss functions for multiple paths
    @Setter
    List<LossFunction> lossFunctions;

    //TODO
    //@Getter @Setter
    //OutputBlockHandler outputBlockHandler;

    OutputBlockHandler outputBlockHandler;

    @Setter @Getter
    boolean isBranchOutput;

    ArrayList<FeatureBlock> blocks = new ArrayList<>();

    public void setUp(){
        if(blocks.get(blocks.size()-1).getOutput().getNum()==1){
            outputBlockHandler = new SingleOutputHandler((SingleFeatureBlock) blocks.get(blocks.size()-1));
        }else{
            outputBlockHandler = new BranchOutputBlockHandler((BranchBlock) blocks.get(blocks.size()-1));
        }
    }

    public double loss(List<double[]> expected){

        return outputBlockHandler.calculateLoss(lossFunctions,expected);

       // return outputBlock.calculateLossFunc(expected);
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

        for(int i = 0; i< blocks.size(); i++){
            blocks.get(i).updateWeights(rule);
        }

    }

    @Override
    public void resetErrors() {

        for(int i = 0; i< blocks.size(); i++){
            blocks.get(i).resetErrors();
        }
    }

    @Override
    public void calculateWeightErrors(List<double[]> expected) {


        if(blocks.size() == 1){
            blocks.get(blocks.size()-1).calculateErrorsWhenOutput(inputBlock.getOutput(),expected,lossFunction);

            outputBlockHandler.calculateWeightsErrors(inputBlock.getOutput(),expected,lossFunctions);
        }else{

            blocks.get(blocks.size()-1).calculateErrorsWhenOutput(blocks.get(blocks.size()-2).getOutput(),expected,lossFunction);

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


        ArrayList<double[]> output = new ArrayList<>();

        //This probably wont work, becuase shape of outputs will be different need a function to produce
        //actaully can be jagged array so should be fine, just need to be careful when definsing array

        //after a branch block put a Flatton operation if not the output layer

        double[] outArr = new double[blocks.get(blocks.size()-1).getOutput().getValues()[0][0].length];

        for(int i=0;i< blocks.get(blocks.size()-1).getOutput().getValues()[0][0].length;i++){
            outArr[i] = blocks.get(blocks.size()-1).getOutput().getValues()[0][0][i][0];
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



}
