package NeuralNetwork.Block;

import NeuralNetwork.Block.Output.ValuePolicyOutputBlock;
import NeuralNetwork.Learning.LearningRule;
import NeuralNetwork.Utils.Dim3Struct;
import NeuralNetwork.Utils.ValuePolicyPair;

public class ValuePolicyNetwork<LRule extends LearningRule> extends BasicNetwork<LRule,ValuePolicyPair> {


    @Override
    public void setUp() {

    }
}
