package NeuralNetwork.Block;

import NeuralNetwork.Block.ActivationFunctions.ActivationFunction;
import NeuralNetwork.Block.Operations.BlockOperation;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


public abstract class WeightBlock extends Block{
    @Getter @Setter
    protected Dim3Struct weights;

    protected Dim3Struct inputNeurons;

    @Getter @Setter
    protected Dim3Struct weightErrors;

    @Getter @Setter
    protected Dim3Struct outputNeurons;


    protected Dim3Struct neuronErrors;

    protected List<BlockOperation> preNeuronOperations;

    protected List<BlockOperation> postNeuronOperations;

    protected ActivationFunction activationFunction;

    public WeightBlock(){
        preNeuronOperations = new ArrayList<>();
        postNeuronOperations = new ArrayList<>();
    }

    public WeightBlock(Dim3Struct neurons, Dim3Struct weights,ActivationFunction func){
        preNeuronOperations = new ArrayList<>();
        postNeuronOperations = new ArrayList<>();
        this.weights = weights;
        this.neurons = neurons;
        this.activationFunction = func;
    }

    public WeightBlock(Dim3Struct neurons,ActivationFunction func){
        preNeuronOperations = new ArrayList<>();
        postNeuronOperations = new ArrayList<>();
        this.neurons = neurons;
        this.activationFunction = func;
    }


    public Dim3Struct getWeightErrors(){
        return weightErrors;
    }
    public Dim3Struct getNeuronErrors(){
        return neuronErrors;
    }



    public Dim3Struct calculate(Dim3Struct Input){

        if(weights== null){
            GenerateWeights(Input.getDims());
        }

        inputNeurons = Input.Copy();
        
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

    public void GenerateWeights(Dim3Struct.Dims inputDims) {
        if(preNeuronOperations.size() >0){
            preNeuronOperations.get(0).doOp(new Dim3Struct(inputDims));
            for (int i=1 ; i<preNeuronOperations.size();i++){
                preNeuronOperations.get(i).doOp(preNeuronOperations.get(i-1).getNeurons());
            }

            GenerateBlockWeights(preNeuronOperations.get(preNeuronOperations.size()-1).getNeurons().getDims());

        }else {
            GenerateBlockWeights(inputDims);

        }




    }

    protected abstract void GenerateBlockWeights(Dim3Struct.Dims inputDims);

    //TODO check
    public void calculateErrors(Dim3Struct inputDeltas,Dim3Struct nextWeights){

        neuronErrors = calculateNeuronErrors(inputDeltas,nextWeights);

        for(int i= postNeuronOperations.size()-1;i>0;i--){
            neuronErrors = postNeuronOperations.get(i).blockOpCalDeltas(neuronErrors);
        }

        weightErrors = calculateWeightErrors(neuronErrors);

        for(int i= preNeuronOperations.size()-1;i>0;i--){
            weightErrors = preNeuronOperations.get(i).blockOpCalDeltas(weightErrors);
        }


    }

    protected void addToPreNeuronOperations(BlockOperation blockOperation){
        preNeuronOperations.add(blockOperation);
    }

    protected void addToPostNeuronOperations(BlockOperation blockOperation){
        postNeuronOperations.add(blockOperation);
    }


    protected abstract Dim3Struct calculateWeightErrors(Dim3Struct inputDeltas);
    protected abstract Dim3Struct calculateNeuronErrors(Dim3Struct inputDeltas,Dim3Struct nextWeights);
    protected abstract Dim3Struct blockCalculation(Dim3Struct Input);



}
