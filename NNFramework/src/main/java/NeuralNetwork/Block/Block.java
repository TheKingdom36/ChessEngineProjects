package NeuralNetwork.Block;

import NeuralNetwork.Operations.BlockOperation;
import NeuralNetwork.Utils.Dim3Struct;
import NeuralNetwork.Utils.Dim4Struct;

import java.util.List;

public interface Block {
    /**
     * Used to verify that the network dimensions are correctly set
     *
     */
    void verifyBlock();

    Dim4Struct calculate(Dim4Struct input);

    void setUp();

    Dim4Struct getOutput();






}
