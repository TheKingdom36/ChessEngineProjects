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
}