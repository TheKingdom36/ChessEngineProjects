package NeuralNetwork.Block;

import NeuralNetwork.Utils.Dim3Struct;

public interface OutputBlock<Output> extends WeightBlock{
    double calculateLossFunc(double[] expected);

    Output getOutput();

    void calculateErrors(WeightBlock previousBlock);
}
