package NeuralNetwork.Block;


import NeuralNetwork.Block.ActivationFunctions.ActivationFunction;
import NeuralNetwork.Block.Operations.BlockOperation;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public abstract class NeuronBlock extends Block{

    @Getter @Setter
    protected Dim3Struct blockNeurons;
    @Getter @Setter
    protected Dim3Struct errors;

    protected List<BlockOperation> preNeuronOperations;

    protected List<BlockOperation> postNeuronOperations;

    protected ActivationFunction activationFunction;

    protected NeuronBlock(){
        preNeuronOperations = new ArrayList<>();
        postNeuronOperations = new ArrayList<>();
    }

    public Dim3Struct getNeuronErrors(){
        return errors;
    }


    public void calculate(Dim3Struct Input){
        Dim3Struct neuronsInput = Input.Copy();

        for(BlockOperation operation: preNeuronOperations){
            operation.doOp(neuronsInput);
        }

        blockCalculation(neuronsInput);

        for(BlockOperation operation: postNeuronOperations){
            operation.doOp(neuronsInput);
        }

        blockNeurons.perValueOperation( (input)-> activationFunction.getOutput(input));

    }

    protected void addToPreNeuronOperations(BlockOperation blockOperation){
        preNeuronOperations.add(blockOperation);
    }

    protected void addToPostNeuronOperations(BlockOperation blockOperation){
        postNeuronOperations.add(blockOperation);
    }

    protected abstract void calculateNeuronErrors();

    protected abstract void blockCalculation(Dim3Struct Input);
}
