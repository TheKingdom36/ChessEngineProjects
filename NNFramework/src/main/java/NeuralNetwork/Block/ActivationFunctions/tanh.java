package NeuralNetwork.Block.ActivationFunctions;

public class tanh implements ActivationFunction {

    @Override
    public double getOutput(double Input) {
        return (Math.exp(Input)-Math.exp(-Input))/(Math.exp(Input)+Math.exp(-Input));
    }

    @Override
    public double getDerivative(double value) {
        return 0;
    }


}
