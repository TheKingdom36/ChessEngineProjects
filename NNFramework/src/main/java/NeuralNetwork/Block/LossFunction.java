package NeuralNetwork.Block;

public interface LossFunction {
    double calculate(double[] actualValues,double[] expectedValues);
}
