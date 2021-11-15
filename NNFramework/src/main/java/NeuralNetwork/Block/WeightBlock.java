package NeuralNetwork.Block;

import NeuralNetwork.Operations.BlockOperation;
import NeuralNetwork.Utils.Dim3Struct;
import NeuralNetwork.Utils.Dim4Struct;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public interface WeightBlock extends Block{


    void clearWeightErrors();

    void updateWeights(WeightUpdateRule rule);

    Dim4Struct getWeights();

    void setWeights(Dim4Struct weights);

    void resetErrors();

    Dim4Struct getNeuronErrors();

}
