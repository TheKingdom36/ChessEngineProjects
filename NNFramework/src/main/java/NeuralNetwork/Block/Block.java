package NeuralNetwork.Block;

import NeuralNetwork.Utils.Dim3Struct;

public interface Block<Output> {
    /**
     * Used to verify that the network dimensions are correctly set
     *
     */
    void verifyBlock();

    Output calculate(Dim3Struct input);

    void setUp();

    Output getOutput();

}
