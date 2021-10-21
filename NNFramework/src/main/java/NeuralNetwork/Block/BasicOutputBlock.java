package NeuralNetwork.Block;

import NeuralNetwork.LossFunctions.LossFunction;
import NeuralNetwork.Operations.BlockOperation;
import NeuralNetwork.Exceptions.DimensionMismatch;
import NeuralNetwork.Operations.Operation;
import NeuralNetwork.Utils.Dim3Struct;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class BasicOutputBlock implements OutputBlock<double[]> {

    private double[] expectedArray;
    int numOfBlockNeurons;
    @Getter
    Dim3Struct outputNeurons;
    ArrayList<Operation> operations;
    private LossFunction lossFunction;
    @Getter @Setter
    Dim3Struct weights;
    protected List<BlockOperation> postNeuronOperations;
    @Getter
    Dim3Struct neurons;
    @Getter
    Dim3Struct weightErrors;
    @Getter
    Dim3Struct neuronErrors;

    Dim3Struct.Dims inputNeuronsDims;

    public BasicOutputBlock(int numBlockNeurons, int numInputNeurons, LossFunction lossFunction){
        inputNeuronsDims = new Dim3Struct.Dims(numInputNeurons,1,1) ;
        this.numOfBlockNeurons = numBlockNeurons;
        this.lossFunction = lossFunction;
        postNeuronOperations = new ArrayList<>();
    }


    @Override
    public void VerifyBlock() {

    }


    public void generateFeatureBlWeights(Dim3Struct.Dims inputDims) {
        weights = new Dim3Struct(neurons.totalNumOfValues(),inputDims.getWidth()*inputDims.getLength()*inputDims.getDepth(),1);
    }


    @Override
    public void setUp(){



        if(neurons ==null ){
            neurons = new Dim3Struct(numOfBlockNeurons,1,1);
        }
        if(weights == null){
                weights = new Dim3Struct(neurons.totalNumOfValues() ,inputNeuronsDims.getWidth() * inputNeuronsDims.getLength() * inputNeuronsDims.getDepth() ,1);
        }

        VerifyBlock();
    }

    @Override
    public Dim3Struct calculate(Dim3Struct Input){

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
                for(int k=0;k<weights.getDepth();k++){


                    this.weights.getValues()[i][j][k] = rule.calculate(weights.getValues()[i][j][k],weightErrors.getValues()[i][j][k]);

                }
            }
        }

    }

    @Override
    public void setWeights(Object weights) {
        this.weights = (Dim3Struct)weights;
    }

    @Override
    public void calculateErrors(WeightBlock nextBlock ,WeightBlock previousBlock) {

    }

    public double calculateLossFunc(double[] expectedArray){
        double[] actualArray = outputNeurons.toArray();

        this.expectedArray = expectedArray;
        double lossValue = 0;

        lossValue = lossFunction.calculate(actualArray,expectedArray);

        return lossValue;
    }

    @Override
    public double[] getOutput() {
        return outputNeurons.toArray();
    }

    private Dim3Struct blockCalculation(Dim3Struct inputNeurons) {
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
    public void clearWeightErrors() {

        weightErrors.clear();
    }


    @Override
    public void resetErrors() {
        neuronErrors.clear();
    }

    @Override
    public void addToPostNeuronOperations(BlockOperation operation) {

    }

    @Override
    public void calculateErrors(WeightBlock previousBlock){


        neuronErrors = calculateNeuronErrors();

        for(int i= postNeuronOperations.size()-1;i>0;i--){
            neuronErrors = postNeuronOperations.get(i).calculateDeltas(neuronErrors);
        }

        weightErrors = calculateWeightErrors(neuronErrors,(Dim3Struct) previousBlock.getOutputNeurons());


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
