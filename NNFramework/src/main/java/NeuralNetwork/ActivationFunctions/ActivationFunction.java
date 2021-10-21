package NeuralNetwork.ActivationFunctions;

public interface ActivationFunction {
    double getOutput(double value);

    double getDerivative(double value);
}
