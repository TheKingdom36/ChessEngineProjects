package NeuralNetwork.Block;

import NeuralNetwork.ActivationFunctions.ActivationFunction;
import NeuralNetwork.Operations.BlockOperation;
import NeuralNetwork.Utils.Dim3Struct;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


public abstract class FeatureBlock<WeightsStruct> implements WeightBlock<Dim3Struct,WeightsStruct> {


    protected ActivationFunction activationFunction;

    @Getter
    @Setter
    protected WeightsStruct weights;

    @Getter @Setter
    protected WeightsStruct weightErrors;

    @Getter @Setter
    public Dim3Struct outputNeurons;

    @Getter
    @Setter
    protected Dim3Struct neurons;

    @Getter @Setter
    protected Dim3Struct neuronErrors;
    @Getter @Setter
    protected Dim3Struct.Dims inputNeuronsDims;

    protected List<BlockOperation> postNeuronOperations;

    public FeatureBlock( Dim3Struct.Dims inputDims){

        this.inputNeuronsDims = inputDims;

    }

    public FeatureBlock(Dim3Struct.Dims inputDims, ActivationFunction func){

        this.activationFunction = func;

        postNeuronOperations = new ArrayList<>();


        this.inputNeuronsDims = inputDims;
    }

    public FeatureBlock(){

    }

    public void addToPostNeuronOperations(BlockOperation blockOperation){
        postNeuronOperations.add(blockOperation);
    };



    public Dim3Struct calculate(Dim3Struct Input){

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



    public void calculateErrors(WeightBlock previousBlock, WeightBlock nextBlock){

        neuronErrors = calculateNeuronErrors(nextBlock.getNeuronErrors() ,nextBlock.getWeights());

        for(int i=0;i<neuronErrors.getWidth();i++){
            for(int j=0;j<neuronErrors.getLength();j++){
                for (int k=0;k<neuronErrors.getDepth();k++){
                    neuronErrors.getValues()[i][j][k] = neuronErrors.getValues()[i][j][k]*activationFunction.getDerivative(outputNeurons.getValues()[i][j][k]);
                }
            }
        }

        for(int i= postNeuronOperations.size()-1;i>=0;i--){
            neuronErrors = postNeuronOperations.get(i).calculateDeltas(neuronErrors);
        }





        weightErrors = calculateWeightErrors(neuronErrors,(Dim3Struct) previousBlock.getOutputNeurons());

    }

    public void calculateErrors(InputBlock inputBlock, WeightBlock nextBlock){

        neuronErrors = calculateNeuronErrors(nextBlock.getNeuronErrors() ,nextBlock.getWeights());


        for(int i=0;i<neuronErrors.getWidth();i++){
            for(int j=0;j<neuronErrors.getLength();j++){
                for (int k=0;k<neuronErrors.getDepth();k++){
                    neuronErrors.getValues()[i][j][k] = neuronErrors.getValues()[i][j][k]*activationFunction.getDerivative(outputNeurons.getValues()[i][j][k]);
                }
            }
        }

        for(int i= postNeuronOperations.size()-1;i>=0;i--){
            neuronErrors = postNeuronOperations.get(i).calculateDeltas(neuronErrors);
        }






        weightErrors = calculateWeightErrors(neuronErrors,(Dim3Struct) inputBlock.getOutputNeurons());


    }



    @Override
    public void resetErrors() {

        clearWeightErrors();
        neurons.clear();
        neuronErrors.clear();
        outputNeurons.clear();
    }

    /**
     * Used to verify that the network dimensions are correctly set
     *
     */
    public void VerifyBlock(){

        calculate(new Dim3Struct(inputNeuronsDims));
    }


    protected abstract WeightsStruct calculateWeightErrors(Dim3Struct neuronErrors,Dim3Struct inputNeurons);

    protected abstract Dim3Struct calculateNeuronErrors(Dim3Struct nextNeuronErrors,Object nextWeights);

    protected abstract Dim3Struct blockCalculation(Dim3Struct Input);


}
