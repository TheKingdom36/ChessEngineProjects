package NeuralNetwork.Block;

import NeuralNetwork.ActivationFunctions.ActivationFunction;
import NeuralNetwork.LossFunctions.LossFunction;
import NeuralNetwork.Operations.BlockOperation;
import NeuralNetwork.Utils.Dim4Struct;
import NeuralNetwork.WeightIntializers.WeightInitializer;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


public abstract class SingleFeatureBlock extends FeatureBlock {


    protected ActivationFunction activationFunction;

    @Getter
    @Setter
    protected Dim4Struct weights;

    @Getter @Setter
    protected WeightInitializer weightInitializer;

    @Getter @Setter
    protected Dim4Struct weightErrors;

    @Getter @Setter
    public Dim4Struct outputNeurons;

    protected Dim4Struct neurons;

    @Getter @Setter
    protected Dim4Struct neuronErrors;
    @Getter @Setter
    protected Dim4Struct.Dims inputNeuronsDims;

    protected List<BlockOperation> postNeuronOperations;



    public SingleFeatureBlock(Dim4Struct.Dims inputDims){

        this.inputNeuronsDims = inputDims;

    }

    public SingleFeatureBlock(Dim4Struct.Dims inputDims, ActivationFunction func){

        this.activationFunction = func;

        postNeuronOperations = new ArrayList<>();


        this.inputNeuronsDims = inputDims;
    }

    public SingleFeatureBlock(){

    }

    @Override
    public void addToPostCalculationOperations(BlockOperation blockOperation){
        postNeuronOperations.add(blockOperation);
    };



    @Override
    public Dim4Struct calculate(Dim4Struct Input){

        neurons.clear();

        outputNeurons = Input.Copy();


        //System.out.println(weights.toString());
        outputNeurons = blockCalculation(outputNeurons);
        //System.out.println(outputNeurons.toString());
        neurons = outputNeurons.Copy();

        for(BlockOperation operation: postNeuronOperations){
            outputNeurons = operation.doOp(outputNeurons);
        }

        outputNeurons.perValueOperation( (input)-> activationFunction.getOutput(input));

        return outputNeurons;


    }



    @Override
    public void calculateErrors(Dim4Struct previousBlockOutput, Dim4Struct nextBlockNeuronErrors, Dim4Struct nextBlockWeights){

        neuronErrors = calculateNeuronErrors(nextBlockNeuronErrors ,nextBlockWeights);

        for(int i=0;i<neuronErrors.getWidth();i++) {
            for (int j = 0; j < neuronErrors.getLength(); j++) {
                for (int k = 0; k < neuronErrors.getChannels(); k++) {
                    for (int t = 0; t < neuronErrors.getNum(); t++) {
                        neuronErrors.getValues()[t][k][i][j] = neuronErrors.getValues()[t][k][i][j] * activationFunction.getDerivative(outputNeurons.getValues()[t][k][i][j]);
                    }
                }
            }
        }
        for(int i= postNeuronOperations.size()-1;i>=0;i--){
            neuronErrors = postNeuronOperations.get(i).calculateDeltas(neuronErrors);
        }

        weightErrors = calculateWeightErrors(neuronErrors, previousBlockOutput);

    }

    @Override
    public void calculateErrorsWhenOutput(Dim4Struct previousBlockOutput, double[] expectedArray, LossFunction lossFunction){

        neuronErrors = new Dim4Struct(neurons.getDims());
        neuronErrors.populate(lossFunction.calculateDerivative(outputNeurons.toArray(),expectedArray));

        for(int i= postNeuronOperations.size()-1;i>0;i--){
            neuronErrors = postNeuronOperations.get(i).calculateDeltas(neuronErrors);
        }

        weightErrors = calculateWeightErrors(neuronErrors,previousBlockOutput);


    }



    @Override
    public void resetErrors() {

        clearWeightErrors();
        neurons.clear();
        neuronErrors.clear();
        outputNeurons.clear();
    }

    protected abstract Dim4Struct calculateWeightErrors(Dim4Struct neuronErrors,Dim4Struct inputNeurons);

    protected abstract Dim4Struct calculateNeuronErrors(Dim4Struct nextNeuronErrors,Dim4Struct nextWeights);

    protected abstract Dim4Struct blockCalculation(Dim4Struct Input);

    @Override
    public Dim4Struct getOutput(){
        return outputNeurons;
    }



}
