package NeuralNetwork.Block;

import NeuralNetwork.Block.ActivationFunctions.Sigmoid;
import NeuralNetwork.Block.LossFunctions.MSE;
import NeuralNetwork.Block.Mnist.MnistDataReaderDataSet;
import NeuralNetwork.Block.NNBuilders.ConvNNBuilder;
import NeuralNetwork.Block.Operations.SoftmaxOp;
import NeuralNetwork.Block.WeightIntializers.uniformDistribution;
import org.junit.Test;
import NeuralNetwork.UtilityMethods;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class ConvNNBuilderTest {


    //increase number of hidden layers
    @Test
    public void CreateMnistFFWithConvNNBuilder() throws IOException {
        ConvNNBuilder builder = new ConvNNBuilder();
        DataSet trainingSet;
        DataSet testSet;
        int numOfNeurons = 128;
        Random rand = new Random();

        Dim3Struct FCWeights = new Dim3Struct(numOfNeurons,2028,1);
        double[] we = uniformDistribution.generateValues(numOfNeurons*2028,0.1,-0.1);
        UtilityMethods.PopulateDimStruct(FCWeights,we);

        ArrayList<Dim3Struct> ConvWeights= new ArrayList<>();
        for(int i=0;i<3; i++){
            Dim3Struct dim3Struct = new Dim3Struct(3,3,1);
            double[] weightValues = uniformDistribution.generateValues(3*3*1,0.1,-0.1);
            UtilityMethods.PopulateDimStruct(dim3Struct,weightValues);

            ConvWeights.add(dim3Struct);
        }

        Dim3Struct outputWeights = new Dim3Struct(10,numOfNeurons,1);
        we = uniformDistribution.generateValues(10*numOfNeurons,0.1,-0.1);
        UtilityMethods.PopulateDimStruct(outputWeights,we);

        NeuralNetwork network = builder.addInputBlock(new Dim3Struct.Dims(28,28,1))
                .addConvBlock(1,0,3,3,3,new Sigmoid()).addWeights(ConvWeights)
                .addFullyConnectedBlock(numOfNeurons,new Sigmoid()).addWeights(FCWeights)
                .addOutputLayer(10,new MSE()).addWeights(outputWeights).withPostOperation(new SoftmaxOp())
                .build();

        SGD sgd = new SGD();
        sgd.setMaxIterations(5);
        sgd.setLearningRate(0.01);
        network.setLearningRule(sgd);

        MnistDataReaderDataSet reader = new MnistDataReaderDataSet();

        trainingSet = new DataSet();
        testSet = new DataSet();

        DataSetSample[] samples = reader.readData("C:/Users/danielmurphy/IntelljProjects/ChessEngineProjects/NNFramework/src/test/java/NeuralNetwork/Block/data/train-images.idx3-ubyte", "C:/Users/danielmurphy/IntelljProjects/ChessEngineProjects/NNFramework/src/test/java/NeuralNetwork/Block/data/train-labels.idx1-ubyte");
        int trainSetSize = 29000;
        int testSetSize = 1000;
        for(int i=0;i<trainSetSize;i++){
            trainingSet.add(samples[i]);
        }

        for(int i=trainSetSize;i<trainSetSize+testSetSize;i++){
            testSet.add(samples[i]);
        }

        network.learn(trainingSet);

        for(int i=0;i<testSetSize;i++) {
            System.out.println("Test: " + i);
            double[] result = network.evaluate(testSet.getSamples().get(i).getInput());
            for (int j = 0; j < 10; j++) {
                System.out.print(result[j] + " ");
            }
            System.out.println();
            for (int j = 0; j < 10; j++) {
                System.out.print(testSet.getSamples().get(i).getExpectedOutput()[j]+" ");

            }
        }
        assertEquals(0,1);
    }
}