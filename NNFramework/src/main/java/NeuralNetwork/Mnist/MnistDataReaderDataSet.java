package NeuralNetwork.Mnist;

import NeuralNetwork.Utils.*;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MnistDataReaderDataSet {
    public NetworkRow[] readData(String dataFilePath,String labelFilePath) throws IOException {

        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(dataFilePath)));
        int magicNumber = dataInputStream.readInt();
        int numberOfItems = dataInputStream.readInt();
        int nRows = dataInputStream.readInt();
        int nCols = dataInputStream.readInt();

        System.out.println("magic number is " + magicNumber);
        System.out.println("number of items is " + numberOfItems);
        System.out.println("number of rows is: " + nRows);
        System.out.println("number of cols is: " + nCols);

        DataInputStream labelInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(labelFilePath)));
        int labelMagicNumber = labelInputStream.readInt();
        int numberOfLabels = labelInputStream.readInt();

        System.out.println("labels magic number is: " + labelMagicNumber);
        System.out.println("number of labels is: " + numberOfLabels);

        NetworkDataSet set = new NetworkDataSet();

        set.setSampleInputSize(28*28);
        set.setNumberOfOutputs(1);


        assert numberOfItems == numberOfLabels;

       NetworkRow[] samples = new NetworkRow[60000];

        double[] inputArray;
        double[] outputArray;
        NetworkRow sample;
        for(int i = 0; i < numberOfItems; i++) {
            sample = new NetworkRow();


            inputArray = new double[28*28];

            int output = labelInputStream.readUnsignedByte();
            outputArray = new double[10];
            outputArray[output] = 1;

            for (int r = 0; r < nRows; r++) {
                for (int c = 0; c < nCols; c++) {
                    inputArray[nRows*r + c ] = dataInputStream.readUnsignedByte();
                    inputArray[nRows*r + c ]/=255;
                }
            }

            sample.addExpectedOutput(outputArray);
            Dim4Struct struct = new Dim4Struct(1,1,28,28);
            struct.populate(inputArray);
            sample.setInput(struct);
            samples[i]= sample;
        }

        dataInputStream.close();
        labelInputStream.close();


        System.out.println("Finished Loading data");
        return samples;
    }
}
