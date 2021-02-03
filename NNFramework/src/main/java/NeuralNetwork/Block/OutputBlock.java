package NeuralNetwork.Block;

import NeuralNetwork.Exceptions.DimensionMismatch;

import java.util.Arrays;

public class OutputBlock extends WeightBlock {
    LossFunction lossFunction;

    public OutputBlock(int numBlockNeurons, int numInputNeurons, LossFunction lossFunction){
        super(new Dim3Struct(numBlockNeurons,1,1),new Dim3Struct(numInputNeurons,numBlockNeurons,1));
        this.lossFunction = lossFunction;
    }


    public double calculateLossFunc(double[] expectedArray){
        double[] actualArray = neurons.toArray();
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
    protected Dim3Struct calculateWeightErrors(Dim3Struct nextBlockNeuronErrors) {
        return null;
    }

    @Override
    protected Dim3Struct calculateNeuronErrors(Dim3Struct inputDeltas) {
        return null;
    }
}
