package NeuralNetwork.Mnist;

import NeuralNetwork.Utils.DataSet;
import NeuralNetwork.Utils.DataSetRow;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class MnistDataReaderDataSet {
    public DataSetRow[] readData(String dataFilePath,String labelFilePath) throws IOException {

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

        DataSet set = new DataSet();

        set.setSampleInputSize(28*28);
        set.setSampleExpectedOutputSize(10);


        assert numberOfItems == numberOfLabels;

       DataSetRow[] samples = new DataSetRow[30000];
        for(int i = 0; i < numberOfItems/2; i++) {
            DataSetRow sample = new DataSetRow();


            double[] inputArray = new double[28*28];

            int output = labelInputStream.readUnsignedByte();
            double[] outputArray = new double[10];
            outputArray[output] = 1;

            for (int r = 0; r < nRows; r++) {
                for (int c = 0; c < nCols; c++) {
                    inputArray[nRows*r + c ] = dataInputStream.readUnsignedByte();

                    inputArray[nRows*r + c ]/=255;
                }
            }

            sample.setExpectedOutput(outputArray);
            sample.setInput(inputArray);
            samples[i]= sample;
        }

        dataInputStream.close();
        labelInputStream.close();


        return samples;
    }
}
