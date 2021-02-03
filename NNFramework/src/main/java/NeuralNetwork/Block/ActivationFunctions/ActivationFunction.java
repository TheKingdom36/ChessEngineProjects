package NeuralNetwork.Block.ActivationFunctions;

public interface ActivationFunction {
    double getOutput(double value);

    double getDerivative(double value);
}
