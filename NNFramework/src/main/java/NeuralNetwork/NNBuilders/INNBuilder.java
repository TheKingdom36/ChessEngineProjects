package NeuralNetwork.NNBuilders;

import NeuralNetwork.ActivationFunctions.ActivationFunction;
import NeuralNetwork.Block.BasicNetwork;
import NeuralNetwork.Block.Output.OutputBlock;
import NeuralNetwork.Learning.LearningRule;
import NeuralNetwork.LossFunctions.LossFunction;
import NeuralNetwork.Operations.BlockOperation;
import NeuralNetwork.Utils.Dim3Struct;
import NeuralNetwork.Utils.Dim4Struct;

public interface INNBuilder{
    INNBuilder addInputBlock(Dim4Struct.Dims inputSize);

    INNBuilder addInputBlock(double[] input);

    INNBuilder provideLearningRule(LearningRule rule);

    INNBuilder addOutputBlock(OutputBlock block);

    INNBuilder addFullyConnectedBlock(int numOfNeurons ,ActivationFunction function);

    INNBuilder addWeights(Dim4Struct weights);

    INNBuilder withPostOperation(BlockOperation block);

    INNBuilder generateWeights();

    INNBuilder addOutputBlock(int numOfOutputNeurons, LossFunction lossFunction);

    BasicNetwork build();
}
