package NeuralNetwork.Block;

import NeuralNetwork.Block.ActivationFunctions.ActivationFunction;
import NeuralNetwork.Block.Operations.BlockOperation;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.FastByteArrayOutputStream;

import java.util.ArrayList;
import java.util.List;


public abstract class WeightBlock<WeightsStruct> extends Block{
    @Getter @Setter
    protected WeightsStruct weights;

    protected Dim3Struct inputNeurons;

    @Getter @Setter
    protected WeightsStruct weightErrors;

    @Getter @Setter
    public Dim3Struct outputNeurons;


    protected Dim3Struct neuronErrors;

    protected List<BlockOperation> preNeuronOperations;

    protected List<BlockOperation> postNeuronOperations;

    protected ActivationFunction activationFunction;

    public WeightBlock(){
        preNeuronOperations = new ArrayList<>();
        postNeuronOperations = new ArrayList<>();
    }

    public WeightBlock(Dim3Struct.Dims neuronDims,Dim3Struct.Dims inputDims,WeightsStruct weights,ActivationFunction func){
        preNeuronOperations = new ArrayList<>();
        postNeuronOperations = new ArrayList<>();
        this.neurons = new Dim3Struct(neuronDims);
        this.inputNeurons = new Dim3Struct(inputDims);
        this.weights = weights;
        this.activationFunction = func;
    }

    public WeightBlock(ActivationFunction func){
        preNeuronOperations = new ArrayList<>();
        postNeuronOperations = new ArrayList<>();
        this.activationFunction = func;
    }


    public WeightsStruct getWeightErrors(){
        return weightErrors;
    }

    public Dim3Struct getNeuronErrors(){
        return neuronErrors;
    }



    public Dim3Struct calculate(Dim3Struct Input){

        if(weights == null){
            generateWeights(Input.getDims());
        }

        neurons.clear();
        inputNeurons = Input.Copy();
        
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

    public void setUp(){
        if(weights == null){
            generateWeights(this.inputNeurons.getDims());
        }
        setupBlockDimensions(this.inputNeurons.getDims());
    }

    protected abstract void generateWeights(Dim3Struct.Dims inputDims);

    public abstract void updateWeights(WeightUpdateRule rule);

    protected abstract WeightsStruct calculateWeightErrors(Dim3Struct inputDeltas);

    protected abstract Dim3Struct calculateNeuronErrors(Dim3Struct inputDeltas,Dim3Struct nextWeights);

    protected abstract Dim3Struct blockCalculation(Dim3Struct Input);

    protected abstract void clearWeightErrors();

    public void calculateErrors(Dim3Struct inputDeltas,Dim3Struct nextWeights){

        neuronErrors = calculateNeuronErrors(inputDeltas,nextWeights);

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

        weightErrors = calculateWeightErrors(neuronErrors);

        for(int i= preNeuronOperations.size()-1;i>0;i--){
            neuronErrors = preNeuronOperations.get(i).calculateDeltas(neuronErrors);
        }

    }



    public void addToPreNeuronOperations(BlockOperation blockOperation){
        preNeuronOperations.add(blockOperation);
    }

    public void addToPostNeuronOperations(BlockOperation blockOperation){
        postNeuronOperations.add(blockOperation);
    }





    public void resetErrors() {

        clearWeightErrors();
        neurons.clear();
        neuronErrors.clear();
        outputNeurons.clear();
        inputNeurons.clear();
    }

    /**
     * Used to set up the dimensions of the inner neurons
     * @param input
     */
    public void setupBlockDimensions(Dim3Struct.Dims input){

        calculate(new Dim3Struct(input));
    }
}
