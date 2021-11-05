package NeuralNetwork.Block;

import NeuralNetwork.Block.Output.NetworkOutput;
import NeuralNetwork.Block.Output.OutputBlock;
import NeuralNetwork.Learning.LearningRule;
import NeuralNetwork.Utils.DataSet;
import NeuralNetwork.Utils.Dim3Struct;
import NeuralNetwork.Utils.NetworkDataSet;

import java.util.List;

public interface INeuralNetwork<LRule extends LearningRule,Output> {
    Output evaluate(Dim3Struct input);

    Output evaluate(double[] inputs);

    double loss(Output expected);

    void addBlock(FeatureBlock block);

    void learn(NetworkDataSet dataSet);

    void setLearningRule(LRule learningRule);

    void calculateWeightErrors();

    void updateWeights(WeightUpdateRule rule);

    void resetErrors();

    void setUp();

    void setInputBlock(InputBlock inputBlock);

    void setOutputBlock(OutputBlock<Output> outputBlock);

    InputBlock getInputBlock();

    OutputBlock getOutputBlock();

    LRule getLearningRule();

}
