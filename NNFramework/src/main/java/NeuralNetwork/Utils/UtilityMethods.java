package NeuralNetwork.Utils;

import NeuralNetwork.Mnist.MnistDataReader;
import NeuralNetwork.Mnist.MnistMatrix;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UtilityMethods {

    public static void PopulateDimStruct(Dim4Struct struct, double[] values){
        int inter=0;
        for(int t=0;t<struct.getNum();t++){

            for(int k=0;k<struct.getChannels();k++){
            for (int i=0; i<struct.getWidth();i++){
                for(int j=0;j<struct.getLength();j++){
                    struct.getValues()[t][k][i][j] = values[inter];
                    inter++;
                }
            }
        }
    }
    }

    public static NetworkDataSet LoadMINSTDataSet() throws IOException {
        MnistDataReader dataReader = new MnistDataReader();
        MnistMatrix[] mnistMatrix = new MnistDataReader().readData("C:/Users/danielmurphy/IntelljProjects/ChessEngineProjects/NNFramework/src/test/java/Block/data/train-images.idx3-ubyte", "C:/Users/danielmurphy/IntelljProjects/ChessEngineProjects/NNFramework/src/test/java/Block/data/train-labels.idx1-ubyte");
        NetworkDataSet set  = new NetworkDataSet();

        set.setSampleInputSize(mnistMatrix[0].getNumberOfRows()*mnistMatrix[1].getNumberOfColumns());


        for (int i = 0; i < mnistMatrix.length/4; i++) {
            MnistMatrix mat = mnistMatrix[i];
            double[] inputArray = mat.toArray();
            int output = mat.getLabel();
            double[] outputArray = new double[10];
            outputArray[output] = 1;
            NetworkRow newRow = new NetworkRow();

            Dim4Struct struct = new Dim4Struct(1,1,28,28);
            struct.populate(inputArray);
            newRow.setInput(struct);

            List<double[]> outputs = new ArrayList<>();
            outputs.add(outputArray);
            newRow.setExpectedOutput(outputs);

            set.add(newRow);
        }

        return set;
    }
}
