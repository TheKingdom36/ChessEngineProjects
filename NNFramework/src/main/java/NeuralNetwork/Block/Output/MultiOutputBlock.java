package NeuralNetwork.Block.Output;

import NeuralNetwork.Block.WeightBlock;
import NeuralNetwork.Block.WeightUpdateRule;
import NeuralNetwork.Operations.BlockOperation;
import NeuralNetwork.Utils.Dim3Struct;

import java.util.List;

public class MultiOutputBlock implements OutputBlock<List<Dim3Struct>> {


    public MultiOutputBlock(List<OutputBlock> blocks){

    }

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
    public List<Dim3Struct> getWeights() {
        return null;
    }

    @Override
    public void setWeights(List<Dim3Struct> weights) {

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
    public List<double[]> calculate(Dim3Struct input) {
        return null;
    }

    @Override
    public void setUp() {

    }

    @Override
    public List<double[]> getOutput() {
        return null;
    }
}
