package NeuralNetwork.Block.Output;

import NeuralNetwork.Block.WeightBlock;
import NeuralNetwork.Block.WeightUpdateRule;
import NeuralNetwork.Operations.BlockOperation;
import NeuralNetwork.Utils.Dim3Struct;

public class MultiOutputBlock<Output> implements OutputBlock<Output> {
    @Override
    public double calculateLossFunc(Output expected) {
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
    public Dim3Struct getWeights() {
        return null;
    }

    @Override
    public void setWeights(Dim3Struct weights) {

    }

    @Override
    public void calculateErrors(WeightBlock nextBlock ,WeightBlock previousBlock) {

    }

    @Override
    public void resetErrors() {

    }

    @Override
    public void addToPostNeuronOperations(BlockOperation operation) {

    }

    @Override
    public Dim3Struct getNeuronErrors() {
        return null;
    }

    @Override
    public void verifyBlock() {

    }

    @Override
    public Output calculate(Dim3Struct input) {
        return null;
    }

    @Override
    public void setUp() {

    }

    @Override
    public Output getOutput() {
        return null;
    }
}
