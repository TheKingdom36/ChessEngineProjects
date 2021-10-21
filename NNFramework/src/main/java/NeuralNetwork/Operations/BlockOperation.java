package NeuralNetwork.Operations;

import NeuralNetwork.Utils.Dim3Struct;


public abstract class BlockOperation implements Operation<Dim3Struct> {

    protected BlockOperation(){

    }

    public abstract Dim3Struct doOp(Dim3Struct input);
    public abstract Dim3Struct calculateDeltas(Dim3Struct inputDeltas);

}
