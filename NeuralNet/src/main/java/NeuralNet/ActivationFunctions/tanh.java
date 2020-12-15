package NeuralNet.ActivationFunctions;

public class tanh implements ActivationFunction {

    @Override
    public double getOutput(double Input) {
        return (Math.exp(Input)-Math.exp(-Input))/(Math.exp(Input)+Math.exp(-Input));
    }
}
