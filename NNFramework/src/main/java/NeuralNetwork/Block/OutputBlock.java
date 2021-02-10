package NeuralNetwork.Block;

import NeuralNetwork.Block.Operations.BlockOperation;
import NeuralNetwork.Exceptions.DimensionMismatch;

import java.util.Arrays;

public class OutputBlock extends WeightBlock {
    LossFunction lossFunction;
    private double[] expectedArray;
    public OutputBlock(int numBlockNeurons, int numInputNeurons, LossFunction lossFunction){
        super(new Dim3Struct(numBlockNeurons,1,1),new Dim3Struct(numInputNeurons,numBlockNeurons,1),null);
        this.lossFunction = lossFunction;
    }

    public Dim3Struct calculate(Dim3Struct Input){

        outputNeurons = Input.Copy();
        inputNeurons = Input.Copy();
        //System.out.println(outputNeurons.toString());


        for(BlockOperation operation: preNeuronOperations){
            outputNeurons = operation.doOp(outputNeurons);
        }
        //System.out.println(outputNeurons.toString());
        outputNeurons = blockCalculation(outputNeurons).Copy();


        //System.out.println(neurons.toString());
        for(BlockOperation operation: postNeuronOperations){
            outputNeurons = operation.doOp(outputNeurons);
        }

        //System.out.println(outputNeurons.toString());

        return outputNeurons;
    }

    @Override
    protected void GenerateBlockWeights(Dim3Struct.Dims inputDims) {
        weights = new Dim3Struct(neurons.totalNumOfValues(),inputDims.getWidth()*inputDims.getLength()*inputDims.getDepth(),1);
    }


    public double calculateLossFunc(double[] expectedArray){
        double[] actualArray = neurons.toArray();
        this.expectedArray = expectedArray;
        double lossValue = 0;
        for(int i=0;i<actualArray.length;i = i+(neurons.getWidth()* neurons.getLength())){
            lossValue = lossFunction.calculate(Arrays.copyOfRange(actualArray,i,i+(neurons.getWidth()* neurons.getLength())-1),Arrays.copyOfRange(expectedArray,i,i+(neurons.getWidth()* neurons.getLength())-1));
        }
        return lossValue;
    }

    @Override
    protected Dim3Struct blockCalculation(Dim3Struct inputNeurons) {
        if(neurons.getWidth() != weights.getLength() || inputNeurons.getWidth() != weights.getWidth())
        {

            String message = "Incompatible dimensions: " +
                    "Output neurons dims: " + neurons.getWidth()
                    + " Weights dims: " + weights.getWidth() + " " + weights.getLength()
                    + " Input dims: " + inputNeurons.getWidth() + " " + inputNeurons.getLength();

            throw new DimensionMismatch(message);
        }else if(inputNeurons.getLength() != 1 || inputNeurons.getDepth() != 1){
            throw new RuntimeException("Input must be of dimensions X 1 1");
        }


        for(int wghtLength=0;wghtLength<weights.getLength();wghtLength++){
            for(int wghtWidth= weights.getWidth()-1; wghtWidth >=0;wghtWidth--){

                //System.out.println(wghtLength+" "+ blockNeurons.getValues()[wghtLength][0][0] +" "+ inputNeurons.getValues()[wghtWidth][0][0]+" "+weights.getValues()[wghtWidth][wghtLength][0]);

                neurons.getValues()[wghtLength][0][0] += inputNeurons.getValues()[weights.getWidth() - 1 - wghtWidth][0][0]*weights.getValues()[wghtWidth][wghtLength][0];

            }
        }
        return neurons;


    }


    @Override
    protected Dim3Struct calculateWeightErrors(Dim3Struct Deltas) {

        this.weightErrors = new Dim3Struct(weights.getDims());

        for(int weightErrorWidth=0;weightErrorWidth<weightErrors.getWidth();weightErrorWidth++) {
            for(int weightErrorLen=0;weightErrorLen<weightErrors.getLength();weightErrorLen++) {
                weightErrors.getValues()[weightErrorWidth][weightErrorLen][0] = Deltas.getValues()[weightErrorWidth][0][0] * inputNeurons.getValues()[weightErrorLen][0][0];
            }
        }

        return weightErrors;
    }

    @Override
    protected Dim3Struct calculateNeuronErrors(Dim3Struct inputDeltas,Dim3Struct nextWeights) {
            neuronErrors = new Dim3Struct(neurons.getDims());
            neuronErrors.populate(lossFunction.calculateDerivative(outputNeurons.toArray(),expectedArray));
            //System.out.println("Neuron Errors" + neuronErrors.toString());

        return neuronErrors;
    }
}
