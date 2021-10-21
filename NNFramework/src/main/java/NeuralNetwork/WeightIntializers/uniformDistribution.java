package NeuralNetwork.WeightIntializers;

public class uniformDistribution {

    public static double[] generateValues(int numOfValues,double max,double min){


        double[] values = new double[numOfValues];

        for(int i=0; i<values.length;i++){
            values[i] = (Math.random() * ((max - min))) + min;
        }

        return values;
    }
}
