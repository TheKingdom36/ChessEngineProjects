package NeuralNetwork.Block;

import lombok.Getter;
import lombok.Setter;

public abstract class Block {

    @Getter
    @Setter
    protected Dim3Struct neurons;

    public abstract void calculateErrors(Dim3Struct InputDeltas,Dim3Struct nextWeights);
    public abstract Dim3Struct calculate(Dim3Struct InputNeurons);
}
