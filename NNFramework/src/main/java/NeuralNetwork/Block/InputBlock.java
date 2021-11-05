package NeuralNetwork.Block;

import NeuralNetwork.Utils.Dim3Struct;
import lombok.Getter;
import lombok.Setter;


public class InputBlock implements Block<Dim3Struct> {

    @Getter @Setter
Dim3Struct outputNeurons;

public InputBlock(Dim3Struct.Dims inputSize){
    outputNeurons = new Dim3Struct(inputSize);
}


    @Override
    public void verifyBlock() {

    }

    @Override
    public Dim3Struct calculate(Dim3Struct input) {
    outputNeurons = input.Copy();
        return outputNeurons;
    }

    @Override
    public void setUp() {

    }

    @Override
    public Dim3Struct getOutput() {
        return outputNeurons;
    }
}
