package NeuralNetwork.Block;

import NeuralNetwork.Operations.BlockOperation;
import NeuralNetwork.Utils.Dim3Struct;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public interface WeightBlock<WeightsStruct,Output> extends Block<Output> {


    void clearWeightErrors();

    void updateWeights(WeightUpdateRule rule);

    WeightsStruct getWeights();

    void setWeights(WeightsStruct weights);

    void resetErrors();

    void addToPostNeuronOperations(BlockOperation operation);

    Dim3Struct getNeuronErrors();

}
