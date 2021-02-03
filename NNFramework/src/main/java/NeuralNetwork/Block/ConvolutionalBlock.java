package NeuralNetwork.Block;

public class ConvolutionalBlock extends WeightBlock{
    public ConvolutionalBlock(Dim3Struct neurons, Dim3Struct weights) {
        super(neurons, weights);
    }

    @Override
    protected Dim3Struct calculateWeightErrors(Dim3Struct inputDeltas) {
        return null;
    }

    @Override
    protected Dim3Struct calculateNeuronErrors(Dim3Struct inputDeltas) {
        return null;
    }

    @Override
    protected Dim3Struct blockCalculation(Dim3Struct Input) {
        return null;
    }
}
