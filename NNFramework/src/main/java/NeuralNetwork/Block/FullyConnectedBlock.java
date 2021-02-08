package NeuralNetwork.Block;


import NeuralNetwork.Block.ActivationFunctions.ActivationFunction;
import NeuralNetwork.Exceptions.DimensionMismatch;

public class FullyConnectedBlock extends WeightBlock{

    private int numInputNeurons;



    @Override
    protected void GenerateBlockWeights(Dim3Struct.Dims inputDims) {
        weights = new Dim3Struct(neurons.totalNumOfValues(),inputDims.getWidth()*inputDims.getLength()*inputDims.getDepth(),1);
    }

    @Override
    protected Dim3Struct calculateWeightErrors(Dim3Struct Deltas) {
//TODO Check

        this.weightErrors = new Dim3Struct(weights.getDims());

        for(int weightErrorWidth=0;weightErrorWidth<weightErrors.getWidth();weightErrorWidth++) {
            for(int weightErrorLen=0;weightErrorLen<weightErrors.getLength();weightErrorLen++) {
                weightErrors.getValues()[weightErrorWidth][weightErrorLen][0] = Deltas.getValues()[weightErrorWidth][0][0] * inputNeurons.getValues()[weightErrorLen][0][0];
            }
        }

        return neuronErrors;

    }

    @Override
    protected Dim3Struct calculateNeuronErrors(Dim3Struct inputDeltas,Dim3Struct nextWeights) {


        this.neuronErrors = new Dim3Struct(outputNeurons.getDims());


            for(int neuronCount=0;neuronCount<neuronErrors.getWidth();neuronCount++) {
                for (int inputDeltaCount = 0; inputDeltaCount < inputDeltas.getWidth(); inputDeltaCount++) {
                    neuronErrors.getValues()[neuronCount][0][0] = inputDeltas.getValues()[inputDeltaCount][0][0]*nextWeights.getValues()[inputDeltaCount][neuronCount][0];
                }
            }



            return neuronErrors;
    }

    public FullyConnectedBlock(int numInputNeurons, int numBlockNeurons, ActivationFunction actFunc){
        super(new Dim3Struct(numBlockNeurons,1,1),actFunc);
        this.numInputNeurons = numInputNeurons;
        this.neurons = new Dim3Struct(numBlockNeurons,1,1);
    }

    public FullyConnectedBlock(Dim3Struct weights, int numBlockNeurons, ActivationFunction actFunc){
        super(new Dim3Struct(numBlockNeurons,1,1),weights,actFunc);
        this.neurons = new Dim3Struct(numBlockNeurons,1,1);

    }

    @Override
    protected Dim3Struct blockCalculation(Dim3Struct inputNeurons) {

        if(neurons.getWidth() != weights.getWidth() || inputNeurons.getWidth() != weights.getLength()){

            String message = "Incompatible dimensions: " +
                    "Block neurons dims: " + neurons.getWidth() + " "+ neurons.getLength()
                    + " Weights dims: " + weights.getWidth() + " " + weights.getLength()
                    + " Input dims: " + inputNeurons.getWidth() + " " + inputNeurons.getLength();

            throw new DimensionMismatch(message);
        }else if(inputNeurons.getLength() != 1 || inputNeurons.getDepth() != 1){
            throw new RuntimeException("Input must be of dimensions X 1 1");
        }

        int blockNeuronsCount=0;
        for (int wghtWidth =0; wghtWidth <weights.getWidth(); wghtWidth++) {
            for (int wghtLength = 0; wghtLength < weights.getLength(); wghtLength++) {

               // System.out.println(wghtLength+" "+ inputNeurons.getValues()[wghtLength][0][0]  +" "+weights.getValues()[wghtWidth][wghtLength][0]);
                neurons.getValues()[blockNeuronsCount][0][0] += inputNeurons.getValues()[wghtLength][0][0] * weights.getValues()[wghtWidth][wghtLength][0];
               // System.out.println(neurons.getValues()[blockNeuronsCount][0][0]);
            }
            blockNeuronsCount++;
        }

        return neurons;

    }

}



