package NeuralNetwork.Block.Output;

import NeuralNetwork.Block.FeatureBlock;
import NeuralNetwork.Block.WeightBlock;
import NeuralNetwork.Block.WeightUpdateRule;
import NeuralNetwork.Exceptions.DimensionMismatch;
import NeuralNetwork.LossFunctions.LossFunction;
import NeuralNetwork.Operations.BlockOperation;
import NeuralNetwork.Operations.Operation;
import NeuralNetwork.Utils.Dim3Struct;
import NeuralNetwork.Utils.Dim4Struct;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class newOutput implements OutputBlock {

    @Override
    public double calculateLossFunc(List<double[]> expected) {
        return 0;
    }

    @Override
    public void calculateErrors(WeightBlock previousBlock) {

    }

    @Override
    public void clearWeightErrors() {

    }

    @Override
    public void updateWeights(WeightUpdateRule rule) {

    }

    @Override
    public Dim4Struct getWeights() {
        return null;
    }

    @Override
    public void setWeights(Dim4Struct weights) {

    }

    @Override
    public void resetErrors() {

    }

    @Override
    public void addToPostCalculationOperations(BlockOperation operation) {

    }

    @Override
    public Dim4Struct getNeuronErrors() {
        return null;
    }

    @Override
    public void verifyBlock() {

    }

    @Override
    public Dim4Struct calculate(Dim4Struct input) {
        return null;
    }

    @Override
    public void setUp() {

    }

    @Override
    public Dim4Struct getOutput() {
        return null;
    }
}
