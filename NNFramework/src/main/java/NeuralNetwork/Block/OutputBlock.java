package NeuralNetwork.Block;

import NeuralNetwork.Block.ActivationFunctions.ActivationFunction;

public abstract class OutputBlock<WeightStruct> extends WeightBlock<WeightStruct> {

    LossFunction lossFunction;

    public OutputBlock(Dim3Struct.Dims neuronDims, Dim3Struct.Dims inputDims, WeightStruct weights){
        super(neuronDims,inputDims,weights);
    }

    public OutputBlock(Dim3Struct.Dims neuronDims, Dim3Struct.Dims inputDims, WeightStruct weights, LossFunction func){
        super(neuronDims,inputDims,weights);

        this.lossFunction = func;
    }

    public OutputBlock(){

    }



    abstract void calculateErrors(WeightBlock previousBlock);
    abstract Dim3Struct blockCalculation(Dim3Struct inputNeurons);
    abstract void clearWeightErrors();

}
