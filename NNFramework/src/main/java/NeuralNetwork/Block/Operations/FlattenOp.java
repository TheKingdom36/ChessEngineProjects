package NeuralNetwork.Block.Operations;

import NeuralNetwork.Block.Dim3Struct;

public class FlattenOp extends BlockOperation{
    public FlattenOp(int numberOfNeurons) {
        outputDims = new Dim3Struct.Dims(numberOfNeurons,1,1);
    }

    @Override
    public Dim3Struct calculateDeltas(Dim3Struct inputDeltas) {

        return null;
    }

    @Override
    public Dim3Struct doOp(Dim3Struct struct) {
        return null;
    }
}
