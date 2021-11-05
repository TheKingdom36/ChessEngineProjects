package NeuralNetwork.Block.Output;

import NeuralNetwork.Block.WeightBlock;
import NeuralNetwork.Utils.Dim3Struct;

import java.util.List;

public interface OutputBlock extends WeightBlock<Dim3Struct,List<double[]>> {
    double calculateLossFunc(List<double[]> expected);

    void calculateErrors(WeightBlock previousBlock);

}
