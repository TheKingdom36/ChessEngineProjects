package NeuralNetwork;

import NeuralNetwork.Block.Dim3Struct;

public class UtilityMethods {

    public static void PopulateDimStruct(Dim3Struct struct,double[] values){
        int inter=0;
        for(int k=0;k<struct.getDepth();k++){
            for (int i=0; i<struct.getWidth();i++){
                for(int j=0;j<struct.getLength();j++){
                    struct.getValues()[i][j][k] = values[inter];
                    inter++;
                }
            }
        }
    }
}
