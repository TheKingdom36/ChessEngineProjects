package NeuralNetwork.Block;

import NeuralNetwork.Block.ActivationFunctions.ActivationFunction;
import NeuralNetwork.Block.Operations.BlockOperation;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public abstract class WeightBlock<WeightsStruct> extends Block {

    @Getter @Setter
    protected WeightsStruct weights;

    @Getter @Setter
    protected WeightsStruct weightErrors;

    @Getter @Setter
    public Dim3Struct outputNeurons;

    @Getter @Setter
    protected Dim3Struct neuronErrors;
    @Getter @Setter
    protected Dim3Struct.Dims inputNeuronsDims;

    protected List<BlockOperation> preNeuronOperations;

    protected List<BlockOperation> postNeuronOperations;

    public WeightBlock(Dim3Struct.Dims inputDims){
        preNeuronOperations = new ArrayList<>();
        postNeuronOperations = new ArrayList<>();


        this.inputNeuronsDims = inputDims;
    }

    public WeightBlock(){

    }

    abstract Dim3Struct calculate(Dim3Struct input);

    public void addToPreNeuronOperations(BlockOperation blockOperation){
        preNeuronOperations.add(blockOperation);
    };

    public void addToPostNeuronOperations(BlockOperation blockOperation){
        postNeuronOperations.add(blockOperation);
    };

    abstract void resetErrors();

    abstract void VerifyBlock();

    abstract void updateWeights(WeightUpdateRule rule);

    public abstract void setUp();
}
