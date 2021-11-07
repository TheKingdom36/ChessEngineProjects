package NeuralNetwork.Block;


import NeuralNetwork.ActivationFunctions.ActivationFunction;
import NeuralNetwork.Exceptions.DimensionMismatch;
import NeuralNetwork.Utils.Dim3Struct;

public class FullyConnectedBlock extends FeatureBlock<Dim3Struct> {

    int numBlockNeurons;
    public FullyConnectedBlock(int numInputNeurons, int numBlockNeurons, ActivationFunction actFunc){
        super(new Dim3Struct.Dims(numInputNeurons,1,1),actFunc);
        this.numBlockNeurons = numBlockNeurons;
    }


    @Override
    public void setUp(){



        if(neurons ==null ){
            neurons = new Dim3Struct(numBlockNeurons,1,1);
        }
        if(weights == null){
                weights = new Dim3Struct(neurons.totalNumOfValues() ,inputNeuronsDims.getWidth() * inputNeuronsDims.getLength() * inputNeuronsDims.getDepth() ,1);
        }

        verifyBlock();
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
    protected Dim3Struct calculateWeightErrors(Dim3Struct neuronErrors,Dim3Struct inputNeurons) {



        if(weightErrors==null) {
            this.weightErrors = new Dim3Struct(weights.getDims());
        }

        for(int weightErrorWidth=0;weightErrorWidth<weightErrors.getWidth();weightErrorWidth++) {
            for(int weightErrorLen=0;weightErrorLen<weightErrors.getLength();weightErrorLen++) {
                weightErrors.getValues()[weightErrorWidth][weightErrorLen][0] = neuronErrors.getValues()[weightErrorWidth][0][0] * inputNeurons.getValues()[weightErrorLen][0][0];
            }
        }
        return weightErrors;

    }


    @Override
    protected Dim3Struct calculateNeuronErrors(Dim3Struct nextNeuronErrors,Object nextWeights) {

        if(!(nextWeights instanceof Dim3Struct)){
            //connected to con layer just take the errors
            //TODO
            return null;
        }else{

            if(nextNeuronErrors == null){
                throw new RuntimeException("The parameter can not be null");
            }

            if(neuronErrors==null) {
                this.neuronErrors = new Dim3Struct(neurons.getDims());
            }
            for(int neuronCount=0;neuronCount<neuronErrors.getWidth();neuronCount++) {
                for (int inputDeltaCount = 0; inputDeltaCount < nextNeuronErrors.getWidth(); inputDeltaCount++) {
                    neuronErrors.getValues()[neuronCount][0][0] += nextNeuronErrors.getValues()[inputDeltaCount][0][0]*((Dim3Struct)nextWeights).getValues()[inputDeltaCount][neuronCount][0];
                }
            }

            return neuronErrors;
        }
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
    public void clearWeightErrors() {
        weightErrors.clear();
    }



}



