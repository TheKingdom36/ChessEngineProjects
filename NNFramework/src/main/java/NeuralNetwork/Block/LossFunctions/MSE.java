package NeuralNetwork.Block.LossFunctions;

import NeuralNetwork.Block.LossFunction;

public class MSE implements LossFunction {


    @Override
    public double calculate(double[] actualValues, double[] expectedValues) {
        double endValue = 0;

        for(int i=0;i<actualValues.length;i++){
             endValue += Math.pow(expectedValues[i]-actualValues[i],2);
        }

        return endValue;

    }

    @Override
    public double[] calculateDerivative(double[] actualValues, double[] expectedValues) {
        double[] derivativeValues  = new double[actualValues.length];

        for(int i=0; i<actualValues.length;i++){
            derivativeValues[i] = -1*(expectedValues[i]-actualValues[i]);
        }

        return derivativeValues;
    }
}
