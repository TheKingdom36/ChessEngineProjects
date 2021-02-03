package NeuralNetwork.Block;


import NeuralNetwork.Block.ActivationFunctions.ActivationFunction;
import NeuralNetwork.Exceptions.DimensionMismatch;

public class FullyConnectedBlock extends WeightBlock{



    @Override
    protected Dim3Struct calculateNeuronErrors(Dim3Struct inputDeltas) {

    }

    @Override
    protected Dim3Struct calculateWeightErrors(Dim3Struct inputDeltas) {

    }

    public FullyConnectedBlock(int numInputNeurons, int numBlockNeurons, ActivationFunction actFunc){
        super(new Dim3Struct(numBlockNeurons,1,1),new Dim3Struct(numInputNeurons,numBlockNeurons,1));
        this.neurons = new Dim3Struct(numBlockNeurons,1,1);
        this.activationFunction = actFunc;
    }

    @Override
    protected Dim3Struct blockCalculation(Dim3Struct inputNeurons) {

        if(neurons.getWidth() != weights.getWidth() || inputNeurons.getWidth() != weights.getLength()){

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

                System.out.println(wghtLength+" "+ inputNeurons.getValues()[wghtLength][0][0]  +" "+weights.getValues()[wghtWidth][wghtLength][0]);
                neurons.getValues()[blockNeuronsCount][0][0] += inputNeurons.getValues()[wghtLength][0][0] * weights.getValues()[wghtWidth][wghtLength][0];
                System.out.println(neurons.getValues()[blockNeuronsCount][0][0]);
            }
            blockNeuronsCount++;
        }

        return neurons;

    }

}



