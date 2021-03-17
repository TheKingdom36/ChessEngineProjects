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
import java.util.Random;

import static org.junit.Assert.assertEquals;

class ConvNNBuilderTest {

    @Test
    //increase number of hidden layers
    public void CreateMnistFFWithConvNNBuilder() throws IOException {
        //TODO adjust weights to read in
        ConvNNBuilder builder = new ConvNNBuilder();
        DataSet trainingSet;
        DataSet testSet;
        int numOfNeurons = 128;
        Random rand = new Random();

        Dim3Struct weights = new Dim3Struct(numOfNeurons,28*28,1);
        double[] we = uniformDistribution.generateValues(numOfNeurons*28*28,0.1,-0.1);
        UtilityMethods.PopulateDimStruct(weights,we);

        Dim3Struct outputWeights = new Dim3Struct(10,numOfNeurons,1);
        we = uniformDistribution.generateValues(10*numOfNeurons,0.1,-0.1);
        UtilityMethods.PopulateDimStruct(outputWeights,we);

        NeuralNetwork network = builder.addInputBlock(new Dim3Struct.Dims(28*28,1,1))
                //.addConvBlock(10,new Sigmoid())
                .addFullyConnectedBlock(numOfNeurons,new Sigmoid()).addWeights(weights)
                .addOutputLayer(10,new MSE()).addWeights(outputWeights).withPostOperation(new SoftmaxOp())
                .build();

        SGD sgd = new SGD();
        sgd.setMaxIterations(5);
        sgd.setLearningRate(0.01);
        network.setLearningRule(sgd);

        MnistDataReaderDataSet reader = new MnistDataReaderDataSet();

        trainingSet = new DataSet();
        testSet = new DataSet();

        DataSetSample[] samples = reader.readData("C:/Users/danielmurphy/IntelljProjects/ChessEngineProjects/NNFramework/src/test/java/Block/data/train-images.idx3-ubyte", "C:/Users/danielmurphy/IntelljProjects/ChessEngineProjects/NNFramework/src/test/java/Block/data/train-labels.idx1-ubyte");
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