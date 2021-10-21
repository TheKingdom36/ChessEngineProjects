package NeuralNetwork.Block;



import NeuralNetwork.Learning.LearningRule;
import NeuralNetwork.Utils.DataSet;
import NeuralNetwork.Utils.Dim3Struct;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class NeuralNetwork<LRule extends LearningRule,Output> {

    @Getter @Setter
    InputBlock inputBlock;
    @Getter @Setter
    OutputBlock<Output> basicOutputBlock;
    @Getter
    LRule learningRule;

    ArrayList<FeatureBlock> Blocks = new ArrayList<>();

    public Output evaluate(Dim3Struct input) {


        inputBlock.setOutputNeurons(input);

        Blocks.get(0).calculate(inputBlock.getOutputNeurons());

        for (int i=1 ;i< Blocks.size() ;i++) {
            Blocks.get(i).calculate(Blocks.get(i-1).getOutputNeurons());
        }

        basicOutputBlock.calculate(Blocks.get(Blocks.size()-1).getOutputNeurons());

        return basicOutputBlock.getOutput();
    }

    public double loss(double[] expected){
        return basicOutputBlock.calculateLossFunc(expected);
    }

    public void addBlock(FeatureBlock block){
        Blocks.add(block);
    }

    public void learn(DataSet dataSet){
        if (dataSet == null) {
            throw new IllegalArgumentException("Training set is null!");
        }

        learningRule.learn(dataSet);
    }

    public Output evaluate(double[] inputs){
        Dim3Struct dim3Struct = new Dim3Struct(inputBlock.getOutputNeurons().getDims());
        dim3Struct.populate(inputs);
        return evaluate(dim3Struct);
    }

    /**
     * Sets learning algorithm for this network
     *
     * @param learningRule learning algorithm for this network
     */
    public void setLearningRule(LRule learningRule) {
        if (learningRule == null) {
            throw new IllegalArgumentException("Learning rule can't be null!");
        }

        learningRule.setNeuralNetwork(this);
        this.learningRule = learningRule;
    }


    public void calculateWeightErrors() {

      basicOutputBlock.calculateErrors(Blocks.get(Blocks.size()-1));


      if(Blocks.size() == 1){
          Blocks.get(Blocks.size()-1).calculateErrors(inputBlock, (WeightBlock)basicOutputBlock);
      }else{
            Blocks.get(Blocks.size() - 1).calculateErrors(Blocks.get(Blocks.size() -2 ), basicOutputBlock);


          for(int i=Blocks.size()-2;i>=1;i--){
              Blocks.get(i).calculateErrors(Blocks.get(i-1),Blocks.get(i+1));
          }

          Blocks.get(0).calculateErrors(inputBlock,Blocks.get(1) );

      }
    }

    public void updateWeights(WeightUpdateRule rule) {
        basicOutputBlock.updateWeights(rule);

        for(int i=0;i<Blocks.size();i++){
            Blocks.get(i).updateWeights(rule);
        }

    }

    public void resetErrors() {
        basicOutputBlock.resetErrors();
        for(int i=0;i<Blocks.size();i++){
            Blocks.get(i).resetErrors();
        }
    }

    public void setUp() {

    }
}

