package NeuralNetwork.Block;


import NeuralNetwork.Block.ActivationFunctions.ActivationFunction;
import NeuralNetwork.Exceptions.DimensionMismatch;

public class FullyConnectedBlock extends WeightBlock<Dim3Struct>{




    @Override
    protected void generateBlockWeights(Dim3Struct.Dims inputDims) {

        weights = new Dim3Struct(neurons.totalNumOfValues(),inputDims.getWidth()*inputDims.getLength()*inputDims.getDepth(),1);
    }

    @Override
    public void updateWeights(WeightUpdateRule rule) {
        for(int i=0;i<weights.getWidth();i++){
            for(int j=0;j<weights.getLength();j++){
                for(int k=0;k<weights.getDepth();k++){


                    this.weights.getValues()[i][j][k] = rule.calculate(weights.getValues()[i][j][k],weightErrors.getValues()[i][j][k]);

                }
            }
        }
    }

    @Override
    protected Dim3Struct calculateWeightErrors(Dim3Struct Deltas) {


        this.weightErrors = new Dim3Struct(weights.getDims());


        for(int weightErrorWidth=0;weightErrorWidth<weightErrors.getWidth();weightErrorWidth++) {
            for(int weightErrorLen=0;weightErrorLen<weightErrors.getLength();weightErrorLen++) {
                weightErrors.getValues()[weightErrorWidth][weightErrorLen][0] = Deltas.getValues()[weightErrorWidth][0][0] * inputNeurons.getValues()[weightErrorLen][0][0];
            }
        }
        return weightErrors;

    }

    @Override
    protected Dim3Struct calculateNeuronErrors(Dim3Struct inputDeltas,Dim3Struct nextWeights) {

        if(inputDeltas == null){
            throw new RuntimeException("The parameter can not be null");
        }

        this.neuronErrors = new Dim3Struct(outputNeurons.getDims());

            for(int neuronCount=0;neuronCount<neuronErrors.getWidth();neuronCount++) {
                for (int inputDeltaCount = 0; inputDeltaCount < inputDeltas.getWidth(); inputDeltaCount++) {
                    neuronErrors.getValues()[neuronCount][0][0] += inputDeltas.getValues()[inputDeltaCount][0][0]*nextWeights.getValues()[inputDeltaCount][neuronCount][0];
                }
            }

            return neuronErrors;
    }

    public FullyConnectedBlock(int numInputNeurons, int numBlockNeurons, ActivationFunction actFunc){
        super(new Dim3Struct.Dims(numBlockNeurons,1,1),new Dim3Struct.Dims(numInputNeurons,1,1),new Dim3Struct(numBlockNeurons,1,1),actFunc);
    }


    @Override
    protected Dim3Struct blockCalculation(Dim3Struct inNeurons) {

        if(neurons.getWidth() != weights.getWidth() || inNeurons.getWidth() != weights.getLength()){

            String message = "Incompatible dimensions: " +
                    "Block neurons dims: " + neurons.getWidth() + " "+ neurons.getLength()
                    + " Weights dims: " + weights.getWidth() + " " + weights.getLength()
                    + " Input dims: " + inNeurons.getWidth() + " " + inNeurons.getLength();

            throw new DimensionMismatch(message);
        }else if(inNeurons.getLength() != 1 || inNeurons.getDepth() != 1){
            throw new RuntimeException("Input must be of dimensions X 1 1");
        }

        int blockNeuronsCount=0;


        for (int wghtWidth =0; wghtWidth <weights.getWidth(); wghtWidth++) {
            for (int wghtLength = 0; wghtLength < weights.getLength(); wghtLength++) {

                neurons.getValues()[blockNeuronsCount][0][0] += inNeurons.getValues()[wghtLength][0][0] * weights.getValues()[wghtWidth][wghtLength][0];
            }
            blockNeuronsCount++;
        }

        return neurons;

    }

    @Override
    protected void clearWeightErrors() {
        weightErrors.clear();
    }


}



