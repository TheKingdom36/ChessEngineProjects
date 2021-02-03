package NeuralNetwork.Block.Operations;

import NeuralNetwork.Block.Dim3Struct;
import NeuralNetwork.Block.functions.Softmax;

public class SoftmaxOp extends BlockOperation{

    public SoftmaxOp(Dim3Struct.Dims outputDims){
        super(outputDims);
    }


    public Dim3Struct doOp(Dim3Struct Input){

        double[] softmaxResult = Softmax.calculate(Input.toArray());

        for(int i=0 ; i<softmaxResult.length;i++){
            Input.getValues()[i][1][1] = softmaxResult[i];
        }

        this.neurons = Input.Copy();

        return Input;
    }


    @Override
    public Dim3Struct calculateDeltas(Dim3Struct deltas) {

        return null;
    }
}
