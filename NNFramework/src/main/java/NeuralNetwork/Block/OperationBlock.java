package NeuralNetwork.Block;

import NeuralNetwork.ActivationFunctions.None;
import NeuralNetwork.Utils.Dim4Struct;

public class OperationBlock extends SingleFeatureBlock {

    public OperationBlock(Dim4Struct.Dims inputNeuronDims){
        super(inputNeuronDims, new None());
    }

    @Override
    public void setUp() {

        neurons = new Dim4Struct(inputNeuronsDims);
        weights = null;
        verifyBlock();
    }

    @Override
    protected Dim4Struct calculateWeightErrors(Dim4Struct neuronErrors, Dim4Struct inputNeurons) {
        return null;
    }

    @Override
    protected Dim4Struct calculateNeuronErrors(Dim4Struct nextNeuronErrors, Dim4Struct nextWeights) {
        weights = nextWeights;
        return nextNeuronErrors;
    }

    @Override
    protected Dim4Struct blockCalculation(Dim4Struct Input) {
        return Input;
    }

    @Override
    public void clearWeightErrors() {
        //not required
    }

    @Override
    public void updateWeights(WeightUpdateRule rule) {
        //not required
    }


}
