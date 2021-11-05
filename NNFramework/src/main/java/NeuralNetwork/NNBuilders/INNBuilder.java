package NeuralNetwork.NNBuilders;

import NeuralNetwork.ActivationFunctions.ActivationFunction;
import NeuralNetwork.Block.BasicNetwork;
import NeuralNetwork.Block.Output.ValuePolicyOutputBlock;
import NeuralNetwork.Learning.LearningRule;
import NeuralNetwork.LossFunctions.LossFunction;
import NeuralNetwork.Operations.BlockOperation;
import NeuralNetwork.Utils.Dim3Struct;

public interface INNBuilder{
    INNBuilder addInputBlock(Dim3Struct.Dims inputSize);

    INNBuilder addInputBlock(double[] input);

    INNBuilder provideLearningRule(LearningRule rule);

    INNBuilder addPolicyOutputBlock(int numOfOutputNeurons ,LossFunction lossFunction);

    INNBuilder addValuePolicyOutputBlock(ValuePolicyOutputBlock block);

    INNBuilder addFullyConnectedBlock(int numOfNeurons ,ActivationFunction function);

    INNBuilder addWeights(Dim3Struct weights);

    INNBuilder withPostOperation(BlockOperation block);

    INNBuilder generateWeights();

    BasicNetwork build();
}
