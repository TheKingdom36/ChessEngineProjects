package NeuralNetwork.Block;

import NeuralNetwork.Block.Operations.BlockOperation;
import NeuralNetwork.Exceptions.DimensionMismatch;

public class BasicOutputBlock extends OutputBlock<Dim3Struct> {

    private double[] expectedArray;

    public BasicOutputBlock(int numBlockNeurons, int numInputNeurons, LossFunction lossFunction){
        super(new Dim3Struct.Dims(numBlockNeurons,1,1),new Dim3Struct.Dims(numInputNeurons,1,1),new Dim3Struct(numInputNeurons,numBlockNeurons,1),null);
        this.lossFunction = lossFunction;
    }


    @Override
    protected void generateBlockWeights() {
        weights = new Dim3Struct(neurons.totalNumOfValues(),inputNeuronsDims.getWidth()*inputNeuronsDims.getLength()*inputNeuronsDims.getDepth(),1);
    }

    @Override
    public void updateWeights(WeightUpdateRule rule) {

    }

    @Override
    void setUp() {

    }

    public Dim3Struct calculate(Dim3Struct Input){

        outputNeurons = Input.Copy();
        neurons.clear();

        for(BlockOperation operation: preNeuronOperations){
            outputNeurons = operation.doOp(outputNeurons);
        }
        outputNeurons = blockCalculation(outputNeurons).Copy();

        for(BlockOperation operation: postNeuronOperations){
            outputNeurons = operation.doOp(outputNeurons);
        }

        return outputNeurons;
    }

    @Override
    void resetErrors() {

    }

    public double calculateLossFunc(double[] expectedArray){
        double[] actualArray = outputNeurons.toArray();

        this.expectedArray = expectedArray;
        double lossValue = 0;

        lossValue = lossFunction.calculate(actualArray,expectedArray);

        return lossValue;
    }

    @Override
    protected Dim3Struct blockCalculation(Dim3Struct inputNeurons) {
        if(neurons.getWidth() != weights.getWidth() || inputNeurons.getWidth() != weights.getLength())
        {

            String message = "Incompatible dimensions: " +
                    "Output neurons dims: " + neurons.getWidth()
                    + " Weights dims: " + weights.getWidth() + " " + weights.getLength()
                    + " Input dims: " + inputNeurons.getWidth() + " " + inputNeurons.getLength();

            throw new DimensionMismatch(message);
        }else if(inputNeurons.getLength() != 1 || inputNeurons.getDepth() != 1){
            throw new RuntimeException("Input must be of dimensions X 1 1");
        }

        int blockNeuronsCount=0;

        for (int wghtWidth =0; wghtWidth <weights.getWidth(); wghtWidth++) {
            for (int wghtLength = 0; wghtLength < weights.getLength(); wghtLength++) {

                neurons.getValues()[blockNeuronsCount][0][0] += inputNeurons.getValues()[wghtLength][0][0]*weights.getValues()[wghtWidth][wghtLength][0];

            }
            blockNeuronsCount++;
        }
        return neurons;


    }

    @Override
    protected void clearWeightErrors() {
        //TODO
    }

    @Override
    public void calculateErrors(WeightBlock previousBlock){


        neuronErrors = calculateNeuronErrors();

        for(int i= postNeuronOperations.size()-1;i>0;i--){
            neuronErrors = postNeuronOperations.get(i).calculateDeltas(neuronErrors);
        }

        weightErrors = calculateWeightErrors(neuronErrors,previousBlock.getOutputNeurons());

        for(int i= preNeuronOperations.size()-1;i>0;i--){
            neuronErrors = preNeuronOperations.get(i).calculateDeltas(neuronErrors);
        }
    }



    private Dim3Struct calculateWeightErrors(Dim3Struct neuronErrors,Dim3Struct inputNeurons) {

        this.weightErrors = new Dim3Struct(weights.getDims());



        for(int weightErrorWidth=0;weightErrorWidth<weightErrors.getWidth();weightErrorWidth++) {
            for(int weightErrorLen=0;weightErrorLen<weightErrors.getLength();weightErrorLen++) {
            //    System.out.println(weightErrorWidth +" "+weightErrorLen + " " + Deltas.getValues()[weightErrorWidth][0][0] * inputNeurons.getValues()[weightErrorLen][0][0]);
                weightErrors.getValues()[weightErrorWidth][weightErrorLen][0] = neuronErrors.getValues()[weightErrorWidth][0][0] * inputNeurons.getValues()[weightErrorLen][0][0];
            }
        }

        //Get transpose

        return weightErrors;
    }



    private Dim3Struct calculateNeuronErrors() {

        neuronErrors = new Dim3Struct(neurons.getDims());
        neuronErrors.populate(lossFunction.calculateDerivative(outputNeurons.toArray(),expectedArray));


        return neuronErrors;
    }
}