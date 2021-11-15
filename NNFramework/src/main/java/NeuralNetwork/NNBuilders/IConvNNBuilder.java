package NeuralNetwork.NNBuilders;

import NeuralNetwork.ActivationFunctions.ActivationFunction;
import NeuralNetwork.Block.BasicNetwork;
import NeuralNetwork.Utils.Dim3Struct;
import NeuralNetwork.Utils.Dim4Struct;

import java.util.ArrayList;

public interface IConvNNBuilder {
    IConvNNBuilder addConvBlock(int stride ,int padding ,int kernelWidth ,int kernelLength ,int numOfKernels ,ActivationFunction func);

    IConvNNBuilder addFullyConnectedBlock(int numOfNeurons ,ActivationFunction function);

    IConvNNBuilder addWeights(Dim4Struct weights);


}
