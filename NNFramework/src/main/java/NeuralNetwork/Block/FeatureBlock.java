package NeuralNetwork.Block;

import NeuralNetwork.Block.ActivationFunctions.ActivationFunction;
import NeuralNetwork.Block.Operations.BlockOperation;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


public abstract class FeatureBlock<WeightsStruct> extends WeightBlock<WeightsStruct> {


    protected ActivationFunction activationFunction;

    public FeatureBlock(Dim3Struct.Dims neuronDims, Dim3Struct.Dims inputDims, WeightsStruct weights){
        super(neuronDims,inputDims,weights);
    }

    public FeatureBlock(Dim3Struct.Dims neuronDims, Dim3Struct.Dims inputDims, WeightsStruct weights, ActivationFunction func){
        super(neuronDims,inputDims,weights);

        this.activationFunction = func;
    }

    public FeatureBlock(){

    }




    public Dim3Struct calculate(Dim3Struct Input){

        if(weights == null){
            generateWeights(Input.getDims());
        }

        neurons.clear();
        
        outputNeurons = Input.Copy();

        for(BlockOperation operation: preNeuronOperations){
             outputNeurons = operation.doOp(outputNeurons);
        }

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
    public void setUp(){
        if(weights == null){
            generateWeights(this.inputNeuronsDims);
        }
        setupBlockDimensions(this.inputNeuronsDims);
    }

    protected void generateWeights(Dim3Struct.Dims inputDims) {
        if(preNeuronOperations.size() >0){
            preNeuronOperations.get(0).doOp(new Dim3Struct(inputDims));
            for (int i=1 ; i<preNeuronOperations.size();i++){
                preNeuronOperations.get(i).doOp(preNeuronOperations.get(i-1).getOutputNeurons());
            }

            generateBlockWeights(preNeuronOperations.get(preNeuronOperations.size()-1).getOutputNeurons().getDims());

        }else {
            generateBlockWeights(inputDims);
        }

    }

    public void calculateErrors(WeightBlock previousBlock, WeightBlock nextBlock){

        neuronErrors = calculateNeuronErrors(nextBlock.getNeuronErrors(),nextBlock.getWeights());

        for(int i=0;i<neuronErrors.getWidth();i++){
            for(int j=0;j<neuronErrors.getLength();j++){
                for (int k=0;k<neuronErrors.getDepth();k++){
                    neuronErrors.getValues()[i][j][k] = neuronErrors.getValues()[i][j][k]*activationFunction.getDerivative(outputNeurons.getValues()[i][j][k]);
                }
            }
        }

        for(int i= postNeuronOperations.size()-1;i>0;i--){
            neuronErrors = postNeuronOperations.get(i).calculateDeltas(neuronErrors);
        }

        weightErrors = calculateWeightErrors(neuronErrors,previousBlock.getOutputNeurons());

        for(int i= preNeuronOperations.size()-1;i>0;i--){
            neuronErrors = preNeuronOperations.get(i).calculateDeltas(neuronErrors);
        }

    }


    public void calculateErrors(InputBlock previousBlock, WeightBlock nextBlock){

        neuronErrors = calculateNeuronErrors(nextBlock.getNeuronErrors(),nextBlock.getWeights());

        for(int i=0;i<neuronErrors.getWidth();i++){
            for(int j=0;j<neuronErrors.getLength();j++){
                for (int k=0;k<neuronErrors.getDepth();k++){
                    neuronErrors.getValues()[i][j][k] = neuronErrors.getValues()[i][j][k]*activationFunction.getDerivative(outputNeurons.getValues()[i][j][k]);
                }
            }
        }

        for(int i= postNeuronOperations.size()-1;i>0;i--){
            neuronErrors = postNeuronOperations.get(i).calculateDeltas(neuronErrors);
        }

        weightErrors = calculateWeightErrors(neuronErrors,previousBlock.getNeurons());

        for(int i= preNeuronOperations.size()-1;i>0;i--){
            neuronErrors = preNeuronOperations.get(i).calculateDeltas(neuronErrors);
        }

    }

    @Override
    public void resetErrors() {

        clearWeightErrors();
        neurons.clear();
        neuronErrors.clear();
        outputNeurons.clear();
    }

    /**
     * Used to set up the dimensions of the inner neurons
     * @param input
     */
    public void setupBlockDimensions(Dim3Struct.Dims input){

        calculate(new Dim3Struct(input));
    }


    protected abstract void generateBlockWeights(Dim3Struct.Dims inputDims);

    protected abstract WeightsStruct calculateWeightErrors(Dim3Struct neuronErrors,Dim3Struct inputNeurons);

    protected abstract Dim3Struct calculateNeuronErrors(Dim3Struct nextNeuronErrors,Object nextWeights);

    protected abstract Dim3Struct blockCalculation(Dim3Struct Input);

    protected abstract void clearWeightErrors();
}
