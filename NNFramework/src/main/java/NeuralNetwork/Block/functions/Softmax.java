package NeuralNetwork.Block.functions;

public class Softmax{


    public static double[] calculate(double[] values){
        double[] exponentValues = new double[values.length];
        double[] outputValues = new double[values.length];
        double exponentSum=0;
        for(int i=0;i<values.length;i++){
            exponentValues[i] = Math.exp(values[i]);
        }
        for(int i=0;i<values.length;i++) {
            exponentSum += exponentValues[i];
        }
        for(int i=0;i<outputValues.length;i++){
            outputValues[i] = exponentValues[i]/exponentSum;
        }

        return outputValues;
    }

    /**
     *
     * @param values Values used to calculate the derivtive
     * @param i the positon of the y from dy/dx in the values array
     * @param j the positon of the x from dy/dx in the values array
     * @return the dervative dy/dx
     */
    public static double calculateDerivative(double[] values,int i,int j){

        if(i==j){

            return (values[i]*(1 - values[i]));
        }else {
            return (-1 * values[i] * values[j]);
        }
    }

}
