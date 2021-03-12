package NeuralNetwork.Block.ActivationFunctions;

public class Sigmoid implements ActivationFunction{
    @Override
    public double getOutput(double value) {
        return (1/(1+Math.exp(-value)));
    }

    @Override
    public double getDerivative(double value) {
        return (Math.exp(value)/Math.pow(1+Math.exp(value),2));
    }
}
