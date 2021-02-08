package NeuralNetwork.Block;

public class ConvolutionalBlock extends WeightBlock{
    public ConvolutionalBlock(Dim3Struct neurons, Dim3Struct weights) {
        super(neurons, weights,null);
    }

    @Override
    protected void GenerateBlockWeights(Dim3Struct.Dims inputDims) {

    }

    @Override
    protected Dim3Struct calculateWeightErrors(Dim3Struct inputDeltas) {
        return null;
    }

    @Override
    protected Dim3Struct calculateNeuronErrors(Dim3Struct inputDeltas,Dim3Struct nextWeights) {
        return null;
    }

    @Override
    protected Dim3Struct blockCalculation(Dim3Struct Input) {
        return null;
    }
}
