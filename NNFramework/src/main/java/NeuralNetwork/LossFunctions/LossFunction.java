package NeuralNetwork.LossFunctions;

import NeuralNetwork.Block.NeuralNetwork;

public interface LossFunction {
    double calculate(double[] actualValues,double[] expectedValues);

    double[] calculateDerivative(double[] actualValues,double[] expectedValues);
}
