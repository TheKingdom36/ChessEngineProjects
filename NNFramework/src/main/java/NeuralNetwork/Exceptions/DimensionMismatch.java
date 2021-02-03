package NeuralNetwork.Exceptions;

import NeuralNetwork.Block.Dim3Struct;

public class DimensionMismatch extends RuntimeException{

    public DimensionMismatch(String msg){
        super(msg);
    }
}
