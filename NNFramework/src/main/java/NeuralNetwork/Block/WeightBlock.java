package NeuralNetwork.Block;

import NeuralNetwork.Block.ActivationFunctions.ActivationFunction;
import NeuralNetwork.Block.Operations.BlockOperation;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


public abstract class WeightBlock extends Block{
    @Getter @Setter
    protected Dim3Struct weights;

    @Getter @Setter
    protected Dim3Struct weightErrors;

    @Getter @Setter
    protected Dim3Struct outputNeurons;


    protected Dim3Struct neuronErrors;

    protected List<BlockOperation> preNeuronOperations;

    protected List<BlockOperation> postNeuronOperations;

    protected ActivationFunction activationFunction;

    public WeightBlock(Dim3Struct neurons, Dim3Struct weights){
        this.weights = weights;
        this.neurons = neurons;
    }

    public Dim3Struct getWeightErrors(){
        return weightErrors;
    }
    public Dim3Struct getNeuronErrors(){
        return neuronErrors;
    }



    public Dim3Struct calculate(Dim3Struct Input){
        outputNeurons = Input.Copy();

        for(BlockOperation operation: preNeuronOperations){
             outputNeurons = operation.doOp(outputNeurons);
        }

        outputNeurons = blockCalculation(outputNeurons);

        neurons = outputNeurons.Copy();

        for(BlockOperation operation: postNeuronOperations){
            outputNeurons = operation.doOp(outputNeurons);
        }

        outputNeurons.perValueOperation( (input)-> activationFunction.getOutput(input));

        return outputNeurons;
    }

//TODO check
    public void calculateErrors(Dim3Struct inputDeltas){

        neuronErrors = calculateNeuronErrors(inputDeltas);

        for(int i= postNeuronOperations.size()-1;i>0;i--){
            neuronErrors = postNeuronOperations.get(i).calculateDeltas(neuronErrors);
        }

        weightErrors = calculateWeightErrors(neuronErrors);

        for(int i= preNeuronOperations.size()-1;i>0;i--){
            weightErrors = preNeuronOperations.get(i).calculateDeltas(weightErrors);
        }


    }

    protected void addToPreNeuronOperations(BlockOperation blockOperation){
        preNeuronOperations.add(blockOperation);
    }

    protected void addToPostNeuronOperations(BlockOperation blockOperation){
        postNeuronOperations.add(blockOperation);
    }

    protected abstract Dim3Struct calculateWeightErrors(Dim3Struct inputDeltas);
    protected abstract Dim3Struct calculateNeuronErrors(Dim3Struct inputDeltas);
    protected abstract Dim3Struct blockCalculation(Dim3Struct Input);

    public Dim3Struct.Dims getOutputDims(){
        if(postNeuronOperations.size()>0){
            return postNeuronOperations.get(postNeuronOperations.size()-1).getOutputDims();
        }else {
            return neurons.getDims();
        }
    }

}
