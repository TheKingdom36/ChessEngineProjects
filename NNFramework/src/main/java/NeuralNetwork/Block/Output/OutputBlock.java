package NeuralNetwork.Block.Output;

import NeuralNetwork.Block.WeightBlock;
import NeuralNetwork.Utils.Dim3Struct;
import NeuralNetwork.Utils.Dim4Struct;

import java.util.List;

public interface OutputBlock extends WeightBlock {
    double calculateLossFunc(List<double[]> expected);

    void calculateErrors(WeightBlock previousBlock);



}
