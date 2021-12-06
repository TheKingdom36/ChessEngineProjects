package NeuralNetwork.Block;

import NeuralNetwork.LossFunctions.LossFunction;
import NeuralNetwork.Operations.BlockOperation;
import NeuralNetwork.Utils.Dim4Struct;

import java.util.List;

public abstract class FeatureBlock implements WeightBlock {

    public abstract Dim4Struct calculate(Dim4Struct Input);

    abstract void calculateErrors(Dim4Struct previousBlockOutput, Dim4Struct nextBlockNeuronErrors, Dim4Struct nextBlockWeights);

    abstract void calculateErrorsWhenOutput(Dim4Struct previousBlockOutput, List<double[]> expectedArray, LossFunction lossFunction);

    @Override
    public abstract void resetErrors();

    public abstract void addToPostCalculationOperations(BlockOperation operation);
    @Override
    public void verifyBlock(){
        calculate(new Dim4Struct(getInputNeuronsDims()));
    };

    @Override
    public abstract Dim4Struct getOutput();

    public abstract void setWeights(Dim4Struct weights);

    public abstract void setWeightInitializer(NeuralNetwork.WeightIntializers.WeightInitializer weightInitializer);

    abstract void setWeightErrors(Dim4Struct weightErrors);

    abstract void setOutputNeurons(Dim4Struct outputNeurons);

    abstract void setNeurons(Dim4Struct neurons);

    abstract void setNeuronErrors(Dim4Struct neuronErrors);

    abstract void setInputNeuronsDims(Dim4Struct.Dims inputNeuronsDims);

    public abstract Dim4Struct getWeights();
    abstract NeuralNetwork.WeightIntializers.WeightInitializer getWeightInitializer();
    abstract Dim4Struct getWeightErrors();
    abstract Dim4Struct getOutputNeurons();
    abstract Dim4Struct getNeurons();
    public abstract Dim4Struct getNeuronErrors();
    abstract Dim4Struct.Dims getInputNeuronsDims();
}
