package NeuralNetwork.Block.Output;

import NeuralNetwork.Block.*;
import NeuralNetwork.LossFunctions.LossFunction;
import NeuralNetwork.Operations.BlockOperation;
import NeuralNetwork.Exceptions.DimensionMismatch;
import NeuralNetwork.Operations.Operation;
import NeuralNetwork.Utils.Dim3Struct;
import NeuralNetwork.Utils.Dim4Struct;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class BasicOutputBlock implements OutputBlock {

    private double[] expectedArray;
    private int numOfBlockNeurons;
    @Getter
    private Dim4Struct outputNeurons;
    ArrayList<Operation> operations;
    private LossFunction lossFunction;
    @Getter @Setter
    Dim4Struct weights;
    protected List<BlockOperation> postNeuronOperations;
    @Getter
    private Dim4Struct neurons;
    @Getter
    private Dim4Struct weightErrors;
    @Getter
    private Dim4Struct neuronErrors;

    List<FeatureBlock> blocks;

    Dim4Struct.Dims inputNeuronsDims;

    public BasicOutputBlock(int numBlockNeurons, int numInputNeurons, LossFunction lossFunction){
        inputNeuronsDims = new Dim4Struct.Dims(1,1,numInputNeurons,1) ;
        this.numOfBlockNeurons = numBlockNeurons;
        this.lossFunction = lossFunction;
        postNeuronOperations = new ArrayList<>();
    }


    @Override
    public void verifyBlock() {

    }


    public void generateFeatureBlWeights(Dim4Struct.Dims inputDims) {
        weights = new Dim4Struct(1,1,neurons.totalNumOfValues(),inputDims.getWidth()*inputDims.getLength()*inputDims.getChannel());
    }


    @Override
    public void setUp(){



        if(neurons ==null ){
            neurons = new Dim4Struct(1,1,numOfBlockNeurons,1);
        }
        if(weights == null){
                weights = new Dim4Struct(1,1,neurons.totalNumOfValues() ,inputNeuronsDims.getWidth() * inputNeuronsDims.getLength() * inputNeuronsDims.getChannel());
        }

        verifyBlock();
    }

    @Override
    public Dim4Struct calculate(Dim4Struct Input){

        outputNeurons = Input.Copy();
        neurons.clear();

        outputNeurons = blockCalculation(outputNeurons);

        neurons = outputNeurons.Copy();

        for(BlockOperation operation: postNeuronOperations){
            outputNeurons = operation.doOp(outputNeurons);
        }


        return outputNeurons;
    }


    @Override
    public void updateWeights(WeightUpdateRule rule) {


        for(int i=0;i<weights.getWidth();i++){
            for(int j=0;j<weights.getLength();j++){
                for(int k=0;k<weights.getChannels();k++){


                    this.weights.getValues()[0][k][i][j] = rule.calculate(weights.getValues()[0][k][i][j],weightErrors.getValues()[0][k][i][j]);

                }
            }
        }

    }

    @Override
    public void setWeights(Dim4Struct weights) {
        this.weights = weights;
    }


    public double calculateLossFunc(List<double[]> expectedArray){
        double[] actualArray = outputNeurons.toArray();

        this.expectedArray = expectedArray.get(0);
        double lossValue = 0;

        lossValue = lossFunction.calculate(actualArray,expectedArray.get(0));

        return lossValue;
    }

    @Override
    public Dim4Struct getOutput() {

        return outputNeurons;
    }

    private Dim4Struct blockCalculation(Dim4Struct inputNeurons) {
        if(neurons.getWidth() != weights.getWidth() || inputNeurons.getWidth() != weights.getLength())
        {

            String message = "Incompatible dimensions: " +
                    "Output neurons dims: " + neurons.getWidth()
                    + " Weights dims: " + weights.getWidth() + " " + weights.getLength()
                    + " Input dims: " + inputNeurons.getWidth() + " " + inputNeurons.getLength();

            throw new DimensionMismatch(message);
        }else if(inputNeurons.getLength() != 1 || inputNeurons.getChannels() != 1){
            throw new RuntimeException("Input must be of dimensions X 1 1");
        }

        int blockNeuronsCount=0;

        for (int wghtWidth =0; wghtWidth <weights.getWidth(); wghtWidth++) {
            for (int wghtLength = 0; wghtLength < weights.getLength(); wghtLength++) {

                neurons.getValues()[0][0][blockNeuronsCount][0] += inputNeurons.getValues()[0][0][wghtLength][0]*weights.getValues()[0][0][wghtWidth][wghtLength];

            }
            blockNeuronsCount++;
        }
        return neurons;


    }

    @Override
    public void clearWeightErrors() {

        weightErrors.clear();
    }


    @Override
    public void resetErrors() {
        neuronErrors.clear();
    }

    @Override
    public void addToPostCalculationOperations(BlockOperation operation) {

    }

    @Override
    public void calculateErrors(WeightBlock previousBlock){


        neuronErrors = calculateNeuronErrors();

        for(int i= postNeuronOperations.size()-1;i>0;i--){
            neuronErrors = postNeuronOperations.get(i).calculateDeltas(neuronErrors);
        }

        weightErrors = calculateWeightErrors(neuronErrors,previousBlock.getOutput());


    }


    private Dim4Struct calculateWeightErrors(Dim4Struct neuronErrors,Dim4Struct inputNeurons) {

        this.weightErrors = new Dim4Struct(weights.getDims());

        for(int weightErrorWidth=0;weightErrorWidth<weightErrors.getWidth();weightErrorWidth++) {
            for(int weightErrorLen=0;weightErrorLen<weightErrors.getLength();weightErrorLen++) {
            //    System.out.println(weightErrorWidth +" "+weightErrorLen + " " + Deltas.getValues()[weightErrorWidth][0][0] * inputNeurons.getValues()[weightErrorLen][0][0]);
         //       weightErrors.getValues()[weightErrorWidth][weightErrorLen][0] = neuronErrors.getValues()[weightErrorWidth][0][0] * inputNeurons.getValues()[weightErrorLen][0][0];
                weightErrors.getValues()[0][0][weightErrorWidth][weightErrorLen] = neuronErrors.getValues()[0][0][weightErrorWidth][0] * inputNeurons.getValues()[0][0][weightErrorLen][0];

             //   weightErrors.setValue(neuronErrors.getValues()[weightErrorWidth][0][0] * inputNeurons.getValues()[weightErrorLen][0][0],weightErrorWidth,weightErrorLen,0);
            }
        }

        //Get transpose

        return weightErrors;
    }



    private Dim4Struct calculateNeuronErrors() {

        neuronErrors = new Dim4Struct(neurons.getDims());
        neuronErrors.populate(lossFunction.calculateDerivative(outputNeurons.toArray(),expectedArray));


        return neuronErrors;
    }



}
