package NeuralNetwork.Block;

public abstract class OutputBlock<WeightStruct> extends WeightBlock<WeightStruct> {

    LossFunction lossFunction;

    public OutputBlock( Dim3Struct.Dims inputDims){
        super(inputDims);
    }

    public OutputBlock( Dim3Struct.Dims inputDims,  LossFunction func){
        super(inputDims);

        this.lossFunction = func;
    }

    public OutputBlock(){

    }


    /**
     * Used to verify that the network dimensions are correctly set
     *
     */
    public void VerifyBlock(){

        calculate(new Dim3Struct(inputNeuronsDims));
    }


    abstract void generateFeatureBlWeights(Dim3Struct.Dims inputDims);
    abstract void calculateErrors(WeightBlock previousBlock);
    abstract Dim3Struct blockCalculation(Dim3Struct inputNeurons);
    abstract void clearWeightErrors();

}
