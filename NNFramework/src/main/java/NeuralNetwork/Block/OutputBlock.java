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

    @Override
    public void setUp(){
        if(neurons ==null ){
            setupNeurons();
        }
        if(weights == null){
            setupWeights();
        }


        VerifyBlock();
    }

    @Override
    protected void setupWeights() {
        if(preNeuronOperations.size() >0){
            preNeuronOperations.get(0).doOp(new Dim3Struct(this.inputNeuronsDims));
            for (int i=1 ; i<preNeuronOperations.size();i++){
                preNeuronOperations.get(i).doOp(preNeuronOperations.get(i-1).getOutputNeurons());
            }

            generateFeatureBlWeights(preNeuronOperations.get(preNeuronOperations.size()-1).getOutputNeurons().getDims());

        }else {
            generateFeatureBlWeights(this.inputNeuronsDims);
        }

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
