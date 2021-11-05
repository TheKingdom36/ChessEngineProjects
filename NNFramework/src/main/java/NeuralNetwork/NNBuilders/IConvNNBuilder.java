package NeuralNetwork.NNBuilders;

import NeuralNetwork.ActivationFunctions.ActivationFunction;
import NeuralNetwork.Block.BasicNetwork;
import NeuralNetwork.Utils.Dim3Struct;

import java.util.ArrayList;

public interface IConvNNBuilder {
    IConvNNBuilder addConvBlock(int stride ,int padding ,int kernelWidth ,int kernelLength ,int numOfKernels ,ActivationFunction func);

    IConvNNBuilder addFullyConnectedBlock(int numOfNeurons ,ActivationFunction function);

    IConvNNBuilder addWeights(Dim3Struct weights);

    IConvNNBuilder addWeights(ArrayList<Dim3Struct> weights);
}
