package Block;

import NeuralNetwork.Block.*;
import NeuralNetwork.Block.ActivationFunctions.ReLU;
import NeuralNetwork.Block.LossFunctions.MSE;
import NeuralNetwork.Block.Mnist.MnistDataReader;
import NeuralNetwork.Block.Mnist.MnistMatrix;
import NeuralNetwork.UtilityMethods;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class TestNNBuilder {
    @Test
    public void CreateOneLayerFFWithNNBuilder(){
        NNBuilder builder = new NNBuilder();


        Dim3Struct weights = new Dim3Struct(3,4,1);
        double[] weightValues = {0.1,0.2,0.4,0.5,0.8,0.9,0.7,0.8,0.1,0.34,0.4,0.4};
        UtilityMethods.PopulateDimStruct(weights,weightValues);

        Dim3Struct outputWeights = new Dim3Struct(3,3,1);
        double[] outputWeightsValues = {0.1,.2,0.4,0.5,0.8,0.9,0.7,0.8,0.2};
        UtilityMethods.PopulateDimStruct(outputWeights, outputWeightsValues);

        NeuralNetwork network = builder.addInputBlock(new Dim3Struct.Dims(4,1,1))
                .addFullyConnectedBlock(3,new ReLU()).addWeights(weights)
                .addOutputLayer(3,new MSE()).addWeights(outputWeights)
                .build();

        SGD sgd = new SGD();
        sgd.setMaxIterations(2000);
        network.setLearningRule(sgd);


        DataSet set = new DataSet();
        set.setSampleInputSize(4);
        set.setSampleExpectedOutputSize(3);

        double[] inputArray = {0.1,0.1,0.1,0.1};
        double[] expectedArray = {1,1,1};
        set.add(inputArray,expectedArray);

        network.learn(set);

        assertEquals(0,1);
    }

    @Test
    public void CreateMnistFFWithNNBuilder() throws IOException {
        //TODO adjust weights to read in
        NNBuilder builder = new NNBuilder();
        DataSet set = new DataSet();
        MnistDataReader dataReader = new MnistDataReader();
        MnistMatrix[] mnistMatrix = new MnistDataReader().readData("C:/Users/danielmurphy/IntelljProjects/ChessEngineProjects/NNFramework/src/test/java/Block/data/train-images.idx3-ubyte", "C:/Users/danielmurphy/IntelljProjects/ChessEngineProjects/NNFramework/src/test/java/Block/data/train-labels.idx1-ubyte");


        Dim3Struct weights = new Dim3Struct(3,mnistMatrix[0].getNumberOfRows()*mnistMatrix[1].getNumberOfColumns(),1);
        double[] weightValues ={};
        //UtilityMethods.PopulateDimStruct(weights,weightValues);

        Dim3Struct outputWeights = new Dim3Struct(3,10,1);
        double[] outputWeightsValues = {};
        //UtilityMethods.PopulateDimStruct(outputWeights, outputWeightsValues);

        NeuralNetwork network = builder.addInputBlock(new Dim3Struct.Dims(mnistMatrix[0].getNumberOfRows()*mnistMatrix[1].getNumberOfColumns(),1,1))
                .addFullyConnectedBlock(3,new ReLU()).addWeights(weights)
                .addOutputLayer(10,new MSE()).addWeights(outputWeights)
                .build();

        SGD sgd = new SGD();
        sgd.setMaxIterations(2000);
        network.setLearningRule(sgd);



        set.setSampleInputSize(mnistMatrix[0].getNumberOfRows()*mnistMatrix[1].getNumberOfColumns());
        set.setSampleExpectedOutputSize(10);


        for (int i = 0; i < mnistMatrix.length/20; i++) {
            MnistMatrix mat = mnistMatrix[i];
            double[] inputArray = mat.toArray();
            int output = mat.getLabel();
            double[] outputArray = new double[10];
            outputArray[output] = 1;
            set.add(inputArray, outputArray);
        }

        network.learn(set);

        assertEquals(0,1);
    }
}
