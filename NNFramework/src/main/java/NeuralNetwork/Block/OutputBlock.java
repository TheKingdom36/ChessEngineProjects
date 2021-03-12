package NeuralNetwork.Block;

import NeuralNetwork.Block.Operations.BlockOperation;
import NeuralNetwork.Exceptions.DimensionMismatch;

import java.util.Arrays;

public class OutputBlock extends WeightBlock<Dim3Struct> {
    LossFunction lossFunction;
    private double[] expectedArray;
    public OutputBlock(int numBlockNeurons, int numInputNeurons, LossFunction lossFunction){
        super(new Dim3Struct.Dims(numBlockNeurons,1,1),new Dim3Struct.Dims(numInputNeurons,1,1),new Dim3Struct(numInputNeurons,numBlockNeurons,1),null);
        this.lossFunction = lossFunction;
    }

    public Dim3Struct calculate(Dim3Struct Input){

        outputNeurons = Input.Copy();
        inputNeurons = Input.Copy();

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
    protected void generateBlockWeights(Dim3Struct.Dims inputDims) {
        weights = new Dim3Struct(neurons.totalNumOfValues(),inputDims.getWidth()*inputDims.getLength()*inputDims.getDepth(),1);
    }

    @Override
    public void updateWeights(WeightUpdateRule rule) {

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

    }

    public void calculateErrors(Dim3Struct inputDeltas,Dim3Struct nextWeights){


        neuronErrors = calculateNeuronErrors(inputDeltas,nextWeights);

        for(int i= postNeuronOperations.size()-1;i>0;i--){
            neuronErrors = postNeuronOperations.get(i).calculateDeltas(neuronErrors);
        }

        weightErrors = calculateWeightErrors(neuronErrors);

        for(int i= preNeuronOperations.size()-1;i>0;i--){
            weightErrors = preNeuronOperations.get(i).calculateDeltas(weightErrors);
        }
    }


    @Override
    protected Dim3Struct calculateWeightErrors(Dim3Struct Deltas) {

        this.weightErrors = new Dim3Struct(weights.getDims());



        for(int weightErrorWidth=0;weightErrorWidth<weightErrors.getWidth();weightErrorWidth++) {
            for(int weightErrorLen=0;weightErrorLen<weightErrors.getLength();weightErrorLen++) {
            //    System.out.println(weightErrorWidth +" "+weightErrorLen + " " + Deltas.getValues()[weightErrorWidth][0][0] * inputNeurons.getValues()[weightErrorLen][0][0]);
                weightErrors.getValues()[weightErrorWidth][weightErrorLen][0] = Deltas.getValues()[weightErrorWidth][0][0] * inputNeurons.getValues()[weightErrorLen][0][0];
            }
        }

        //Get transpose

        return weightErrors;
    }


    @Override
    protected Dim3Struct calculateNeuronErrors(Dim3Struct inputDeltas,Dim3Struct nextWeights) {
            neuronErrors = new Dim3Struct(neurons.getDims());
            neuronErrors.populate(lossFunction.calculateDerivative(outputNeurons.toArray(),expectedArray));

        return neuronErrors;
    }
}
