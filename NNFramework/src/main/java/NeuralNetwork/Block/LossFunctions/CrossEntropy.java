package NeuralNetwork.Block.LossFunctions;

import NeuralNetwork.Block.Dim3Struct;
import NeuralNetwork.Block.LossFunction;

public class CrossEntropy implements LossFunction {
    @Override
    public double calculate(double[] actualValues, double[] expectedValues) {
        double loss =0;

        for(int i=0; i<actualValues.length;i++){
            loss += expectedValues[i]*Math.log(actualValues[i]) + (1-expectedValues[i])* Math.log(1-actualValues[i]);
        }


        return (-1)*loss;


    }

    @Override
    public double[] calculateDerivative(double[] actualValues, double[] expectedValues) {
        double[] derivatives = new double[actualValues.length];
        for(int i=0; i<actualValues.length;i++) {
            derivatives[i] = expectedValues[i]/actualValues[i] + (1- expectedValues[i])/(1-actualValues[i]);
            derivatives[i] = (-1)*derivatives[i];
        }
        return derivatives;
    }
}
