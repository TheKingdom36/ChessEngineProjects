package NeuralNetwork.Block.Output;

import NeuralNetwork.Block.WeightBlock;
import NeuralNetwork.Utils.Dim3Struct;

import java.util.List;

public interface OutputBlock<WeightsStruct> extends WeightBlock<WeightsStruct,List<double[]>> {
    double calculateLossFunc(List<double[]> expected);

    void calculateErrors(WeightBlock previousBlock);



}
