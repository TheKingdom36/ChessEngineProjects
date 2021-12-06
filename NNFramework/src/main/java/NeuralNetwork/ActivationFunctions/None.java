package NeuralNetwork.ActivationFunctions;

public class None implements ActivationFunction {
    @Override
    public double getOutput(double value) {
        return value;
    }

    @Override
    public double getDerivative(double value) {
        return 1;
    }
}
