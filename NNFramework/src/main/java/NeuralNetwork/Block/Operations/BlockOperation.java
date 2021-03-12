package NeuralNetwork.Block.Operations;

import NeuralNetwork.Block.Dim3Struct;
import NeuralNetwork.Block.NeuralNetwork;
import lombok.Getter;
import lombok.Setter;


public abstract class BlockOperation implements Operation<Dim3Struct> {

    @Setter @Getter
    Dim3Struct outputNeurons;

    protected BlockOperation(){

    }

    public abstract Dim3Struct doOp(Dim3Struct input);
    public abstract Dim3Struct calculateDeltas(Dim3Struct inputDeltas);

}
