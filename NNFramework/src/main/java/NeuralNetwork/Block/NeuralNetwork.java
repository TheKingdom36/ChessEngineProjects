package NeuralNetwork.Block;



import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class NeuralNetwork<L extends LearningRule> {

    @Getter @Setter
    InputBlock inputBlock;
    @Getter @Setter
    BasicOutputBlock basicOutputBlock;
    @Getter
    L learningRule;

    ArrayList<FeatureBlock> Blocks = new ArrayList<>();

    public double[] evaluate(Dim3Struct input) {


        inputBlock.setNeurons(input);

        Blocks.get(0).calculate(inputBlock.getNeurons());

        for (int i=1 ;i< Blocks.size() ;i++) {
            Blocks.get(i).calculate(Blocks.get(i-1).getOutputNeurons());
        }

        basicOutputBlock.calculate(Blocks.get(Blocks.size()-1).getOutputNeurons());

        return basicOutputBlock.getOutputNeurons().toArray();
    }

    public double loss(double[] expected){
        return basicOutputBlock.calculateLossFunc(expected);
    }

    public void addBlock(FeatureBlock block){
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
      basicOutputBlock.calculateErrors(Blocks.get(Blocks.size()-1));


      if(Blocks.size() == 1){
          Blocks.get(Blocks.size()-1).calculateErrors(inputBlock, (WeightBlock)basicOutputBlock);
      }else{
            Blocks.get(Blocks.size() - 1).calculateErrors(Blocks.get(Blocks.size() -2 ), basicOutputBlock);


          for(int i=Blocks.size()-2;i>=0;i--){
              Blocks.get(i).calculateErrors(Blocks.get(i-1),Blocks.get(i+1));
          }
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

