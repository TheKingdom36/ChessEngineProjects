package NeuralNetwork.Block.Operations;

import NeuralNetwork.Block.Dim3Struct;
import NeuralNetwork.Block.functions.Softmax;

public class SoftmaxOp extends BlockOperation{

    public SoftmaxOp(){

    }



    @Override
    protected Dim3Struct blockOpCal(Dim3Struct input) {
        double[] softmaxResult = Softmax.calculate(input.toArray());

        for(int i=0 ; i<softmaxResult.length;i++){
            input.getValues()[i][1][1] = softmaxResult[i];
        }

        this.neurons = input.Copy();

        return input;
    }


    @Override
    public Dim3Struct blockOpCalDeltas(Dim3Struct deltas) {
//TODO implement
        return null;
    }
}
