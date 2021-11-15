package NeuralNetwork.Operations;

import NeuralNetwork.Utils.Dim3Struct;
import NeuralNetwork.Utils.Dim4Struct;


public abstract class BlockOperation implements Operation<Dim4Struct> {

    protected BlockOperation(){

    }

    public abstract Dim4Struct doOp(Dim4Struct input);
    public abstract Dim4Struct calculateDeltas(Dim4Struct inputDeltas);

}
