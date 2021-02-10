package NeuralNetwork.Block;



import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class NeuralNetwork<L extends LearningRule> {

    @Getter @Setter
    Block inputBlock;
    @Getter @Setter
    OutputBlock outputBlock;
    @Getter
    L learningRule;

    ArrayList<WeightBlock> Blocks = new ArrayList<>();

    public double[] evaluate(Dim3Struct input) {


        inputBlock.setNeurons(input);

        Blocks.get(0).calculate(inputBlock.getNeurons());

        for (int i=1 ;i< Blocks.size() ;i++) {
            Blocks.get(i).calculate(Blocks.get(i-1).getNeurons());
        }

        outputBlock.calculate(Blocks.get(Blocks.size()-1).getNeurons());

        return outputBlock.getNeurons().toArray();
    }

    public double loss(double[] expected){
        return outputBlock.calculateLossFunc(expected);
    }

    public void addBlock(WeightBlock block){
        Blocks.add(block);
    }

    public void learn(DataSet trainingSet ){
        if (trainingSet == null) {
            throw new IllegalArgumentException("Training set is null!");
        }

        learningRule.learn(trainingSet);
    }

    public double[] evaluate(double[] inputs){
        Dim3Struct dim3Struct = new Dim3Struct(inputBlock.getNeurons().getDims());
        dim3Struct.populate(inputs);
        return evaluate(dim3Struct);
    }

    /**
     * Sets learning algorithm for this network
     *
     * @param learningRule learning algorithm for this network
     */
    public void setLearningRule(L learningRule) {
        if (learningRule == null) {
            throw new IllegalArgumentException("Learning rule can't be null!");
        }

        learningRule.setNeuralNetwork(this);
        this.learningRule = learningRule;
    }


    public void calculateWeightErrors() {
//TODO give the next layer Weights
        outputBlock.calculateErrors(null,null);

        Blocks.get(Blocks.size()-1).calculateErrors(outputBlock.getWeightErrors(),outputBlock.getWeights());

        for(int i=Blocks.size()-2;i>1;i--){
            Blocks.get(i).calculateErrors(((WeightBlock)Blocks.get(i+1)).getWeightErrors(),((WeightBlock)Blocks.get(i+1)).getWeights());
        }

    }

    public void updateWeights(WeightUpdateRule rule) {
        outputBlock.UpdateWeights(rule);

        for(int i=0;i<Blocks.size();i++){
            Blocks.get(i).UpdateWeights(rule);
        }

    }

    public void resetErrors() {
        outputBlock.resetErrors();
        for(int i=0;i<Blocks.size();i++){
            Blocks.get(i).resetErrors();
        }
    }

    public void setUp() {

    }
}