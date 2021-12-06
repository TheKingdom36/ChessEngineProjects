package NeuralNetwork.Block;

import NeuralNetwork.LossFunctions.LossFunction;
import NeuralNetwork.Utils.Dim4Struct;

import java.util.List;

public interface OutputBlockHandler<FBlock extends FeatureBlock> {

    List<double[]> getOutput();

    void calculateWeightsErrors(Dim4Struct struct,List<double[]> expectedArrays);

    double calculateLoss(List<LossFunction> functions,List<double[]> expectedArrays);


}
