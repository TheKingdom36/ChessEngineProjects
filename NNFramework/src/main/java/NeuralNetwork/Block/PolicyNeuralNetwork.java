package NeuralNetwork.Block;



import NeuralNetwork.Block.Output.BasicOutputBlock;
import NeuralNetwork.Block.Output.OutputBlock;
import NeuralNetwork.Learning.LearningRule;
import NeuralNetwork.Utils.Dim3Struct;

public class PolicyNeuralNetwork<LRule extends LearningRule> extends BasicNetwork<LRule, double[]> {


    @Override
    public void setUp() {

    }

    @Override
    public void setOutputBlock(OutputBlock outputBlock) {

    }

}

