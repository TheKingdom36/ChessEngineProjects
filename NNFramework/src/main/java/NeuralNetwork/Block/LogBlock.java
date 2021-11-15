package NeuralNetwork.Block;

import NeuralNetwork.Utils.Dim4Struct;

public class LogBlock extends FeatureBlock{
    @Override
    protected Dim4Struct calculateWeightErrors(Dim4Struct neuronErrors ,Dim4Struct inputNeurons) {
        return null;
    }

    @Override
    protected Dim4Struct calculateNeuronErrors(Dim4Struct nextNeuronErrors ,Dim4Struct nextWeights) {
        return null;
    }

    @Override
    protected Dim4Struct blockCalculation(Dim4Struct Input) {
        return null;
    }

    @Override
    public void clearWeightErrors() {

    }

    @Override
    public void updateWeights(WeightUpdateRule rule) {

    }

    @Override
    public void setUp() {

    }
}
