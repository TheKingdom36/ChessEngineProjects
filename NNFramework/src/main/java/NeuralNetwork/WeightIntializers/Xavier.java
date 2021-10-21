package NeuralNetwork.WeightIntializers;

public class  Xavier {

    public static double[] generateValues(int numValuesToGenerate,int numOfNeuronsLayerI, int numOfNeuronsLayerJ){
        double min =-1 * Math.sqrt(6)/Math.sqrt(numOfNeuronsLayerI+numOfNeuronsLayerJ);
        double max =Math.sqrt(6)/Math.sqrt(numOfNeuronsLayerI+numOfNeuronsLayerJ) ;

        double[] values = new double[numValuesToGenerate];

        for(int i=0; i<values.length;i++){
             values[i] = (Math.random() * ((max - min))) + min;
        }

        return values;

    }

}
