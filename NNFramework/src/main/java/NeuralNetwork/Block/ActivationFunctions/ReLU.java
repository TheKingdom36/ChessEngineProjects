package NeuralNetwork.Block.ActivationFunctions;

public class ReLU implements ActivationFunction {


    @Override
    public double getOutput(double value) {
        if(value>0){
            return value;
        }else{
            return 0;
        }
    }

    @Override
    public double getDerivative(double value) {
        if(value>0){
            return 1;
        }else {
            return 0;
        }
    }
}
