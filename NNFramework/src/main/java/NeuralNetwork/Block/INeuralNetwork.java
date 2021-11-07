package NeuralNetwork.Block;

import NeuralNetwork.Block.Output.NetworkOutput;
import NeuralNetwork.Block.Output.OutputBlock;
import NeuralNetwork.Learning.LearningRule;
import NeuralNetwork.Utils.DataSet;
import NeuralNetwork.Utils.Dim3Struct;
import NeuralNetwork.Utils.NetworkDataSet;

import java.util.List;

public interface INeuralNetwork<LRule extends LearningRule> extends BlockExtendable<FeatureBlock> {
    List<double[]> evaluate(Dim3Struct input);

    List<double[]> evaluate(double[] inputs);

    double loss(List<double[]> expected);

    void addBlock(FeatureBlock block);

    void learn(NetworkDataSet dataSet);

    void setLearningRule(LRule learningRule);

    void calculateWeightErrors();

    void updateWeights(WeightUpdateRule rule);

    void resetErrors();

    void setUp();

    void setInputBlock(InputBlock inputBlock);

    void setOutputBlock(OutputBlock outputBlock);

    InputBlock getInputBlock();

    OutputBlock getOutputBlock();

    LRule getLearningRule();

}
