package NeuralNetwork.Block;


import NeuralNetwork.ActivationFunctions.ActivationFunction;
import NeuralNetwork.Exceptions.DimensionMismatch;
import NeuralNetwork.Utils.Dim4Struct;
import lombok.Getter;

public class FullyConnectedBlock extends SingleFeatureBlock {

    @Getter
    private int numBlockNeurons;

    public FullyConnectedBlock(int numInputNeurons, int numBlockNeurons, ActivationFunction actFunc){
        super(new Dim4Struct.Dims(1,1,numInputNeurons,1),actFunc);
        this.numBlockNeurons = numBlockNeurons;
    }


    @Override
    public void setUp(){



        if(neurons ==null ){
            neurons = new Dim4Struct(1,1,numBlockNeurons,1);
        }
        if(weights == null){

            Dim4Struct.Dims weightDims = new Dim4Struct.Dims(1,1,neurons.totalNumOfValues() ,inputNeuronsDims.getWidth() * inputNeuronsDims.getLength() * inputNeuronsDims.getChannel());
            weights = weightInitializer.generate(weightDims);

        }



        verifyBlock();
    }


    @Override
    public void updateWeights(WeightUpdateRule rule) {
        for(int i=0;i<weights.getWidth();i++){
            for(int j=0;j<weights.getLength();j++){
                for(int k=0;k<weights.getChannels();k++){
                    for(int t=0;t<weights.getNum();t++){

                    this.weights.getValues()[t][k][i][j] = rule.calculate(weights.getValues()[t][k][i][j],weightErrors.getValues()[t][k][i][j]);

                }
            }
        }
    }
    }

    @Override
    protected Dim4Struct calculateWeightErrors(Dim4Struct neuronErrors,Dim4Struct inputNeurons) {



        if(weightErrors==null) {
            this.weightErrors = new Dim4Struct(weights.getDims());
        }

        for(int weightErrorWidth=0;weightErrorWidth<weightErrors.getWidth();weightErrorWidth++) {
            for(int weightErrorLen=0;weightErrorLen<weightErrors.getLength();weightErrorLen++) {
                weightErrors.getValues()[0][0][weightErrorWidth][weightErrorLen] = neuronErrors.getValues()[0][0][weightErrorWidth][0] * inputNeurons.getValues()[0][0][weightErrorLen][0];
            }
        }
        return weightErrors;

    }


    @Override
    protected Dim4Struct calculateNeuronErrors(Dim4Struct nextNeuronErrors,Dim4Struct nextWeights) {



            if(nextNeuronErrors == null){
                throw new RuntimeException("The parameter can not be null");
            }

            if(neuronErrors==null) {
                this.neuronErrors = new Dim4Struct(neurons.getDims());
            }
            for(int neuronCount=0;neuronCount<neuronErrors.getWidth();neuronCount++) {
                for (int inputDeltaCount = 0; inputDeltaCount < nextNeuronErrors.getWidth(); inputDeltaCount++) {
                    neuronErrors.getValues()[0][0][neuronCount][0] += nextNeuronErrors.getValues()[0][0][inputDeltaCount][0]*(nextWeights).getValues()[0][0][inputDeltaCount][neuronCount];
                }
            }

            return neuronErrors;

    }




    @Override
    protected Dim4Struct blockCalculation(Dim4Struct inNeurons) {

        if(neurons.getWidth() != weights.getWidth() || inNeurons.getWidth() != weights.getLength()){

            String message = "Incompatible dimensions: " +
                    "Block neurons dims: " + neurons.getWidth() + " "+ neurons.getLength()
                    + " Weights dims: " + weights.getWidth() + " " + weights.getLength()
                    + " Input dims: " + inNeurons.getWidth() + " " + inNeurons.getLength();

            throw new DimensionMismatch(message);
        }else if(inNeurons.getLength() != 1 || inNeurons.getChannels() != 1){
            throw new RuntimeException("Input must be of dimensions X 1 1");
        }

        int blockNeuronsCount=0;


        for (int wghtWidth =0; wghtWidth <weights.getWidth(); wghtWidth++) {
            for (int wghtLength = 0; wghtLength < weights.getLength(); wghtLength++) {

                neurons.getValues()[0][0][blockNeuronsCount][0] += inNeurons.getValues()[0][0][wghtLength][0]* weights.getValues()[0][0][wghtWidth][wghtLength];
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



