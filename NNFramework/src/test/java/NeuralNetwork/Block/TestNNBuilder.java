package NeuralNetwork.Block;

import NeuralNetwork.Block.ActivationFunctions.ReLU;
import NeuralNetwork.Block.ActivationFunctions.Sigmoid;
import NeuralNetwork.Block.LossFunctions.MSE;
import NeuralNetwork.Block.Mnist.MnistDataReaderDataSet;
import NeuralNetwork.Block.NNBuilders.NNBuilder;
import NeuralNetwork.Block.Operations.SoftmaxOp;
import NeuralNetwork.Block.WeightIntializers.Xavier;
import NeuralNetwork.Block.WeightIntializers.uniformDistribution;
import NeuralNetwork.UtilityMethods;
import org.junit.Test;

import java.io.IOException;
import java.util.Random;

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
        sgd.setMaxIterations(1000);
        network.setLearningRule(sgd);


        DataSet set = new DataSet();
        set.setSampleInputSize(4);
        set.setSampleExpectedOutputSize(3);

        double[] inputArray = {0.1,0.1,0.1,0.1};
        double[] expectedArray = {1,1,1};
        set.add(inputArray,expectedArray);
        set.add(inputArray,expectedArray);
        network.learn(set);

        assertEquals(0,1);
    }

    @Test
    //increase number of hidden layers
    public void CreateMnistFFWithNNBuilder() throws IOException {
        //TODO adjust weights to read in
        NNBuilder builder = new NNBuilder();
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

    @Test
    //increase number of hidden layers
    public void CreateMnistFFWithNNBuilder2Layers() throws IOException {
        //TODO adjust weightsLayer1 to read in
        NNBuilder builder = new NNBuilder();
        DataSet trainingSet;
        DataSet testSet;
        int numOfNeuronsLayer1 = 128;
        int numOfNeuronsLayer2 = 128;

        Dim3Struct weightsLayer1 = new Dim3Struct(numOfNeuronsLayer1,28*28,1);
        Dim3Struct weightsLayer2 = new Dim3Struct(numOfNeuronsLayer2,numOfNeuronsLayer1,1);



        double[] we= Xavier.generateValues(numOfNeuronsLayer1*28*28,numOfNeuronsLayer1,28*28);
        UtilityMethods.PopulateDimStruct(weightsLayer1,we);


        we = Xavier.generateValues(numOfNeuronsLayer1*numOfNeuronsLayer2,numOfNeuronsLayer1,numOfNeuronsLayer2);
        UtilityMethods.PopulateDimStruct(weightsLayer2,we);

        Dim3Struct outputWeights = new Dim3Struct(10,numOfNeuronsLayer2,1);
        we = Xavier.generateValues(10*numOfNeuronsLayer2,10,numOfNeuronsLayer2);
        UtilityMethods.PopulateDimStruct(outputWeights,we);

        NeuralNetwork network = builder.addInputBlock(new Dim3Struct.Dims(28*28,1,1))
                .addFullyConnectedBlock(numOfNeuronsLayer1,new ReLU()).addWeights(weightsLayer1)
                .addFullyConnectedBlock(numOfNeuronsLayer2,new ReLU()).addWeights(weightsLayer2)
                .addOutputLayer(10,new MSE()).addWeights(outputWeights)
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

    @Test
    //increase number of hidden layers
    public void SimpleFFTestNNBuilder() throws IOException {
        NNBuilder builder = new NNBuilder();


        Dim3Struct weights2 = new Dim3Struct(6,2,1);
        double[] weightValues2 = {0.01,0.02,0.03,0.01,0.01,0.02,0.01,0.02,0.01,0.02,0.01,0.02};
        UtilityMethods.PopulateDimStruct(weights2,weightValues2);

        Dim3Struct outputWeights = new Dim3Struct(1,6,1);
        double[] outputWeightsValues = {0.01,0.02,0.03,0.01,0.01,0.02};
        UtilityMethods.PopulateDimStruct(outputWeights, outputWeightsValues);

        NeuralNetwork network = builder.addInputBlock(new Dim3Struct.Dims(2,1,1))
                .addFullyConnectedBlock(6,new Sigmoid()).addWeights(weights2)
                .addOutputLayer(1,new MSE()).addWeights(outputWeights)
                .build();

        SGD sgd = new SGD();
        sgd.setLearningRate(0.01);
        sgd.setMaxIterations(1000);
        network.setLearningRule(sgd);


        DataSet set = new DataSet();
        set.setSampleInputSize(2);
        set.setSampleExpectedOutputSize(1);


        double[] inputArray1 = {0,0};
        double[] expectedArray1 = {0};
        double[] inputArray2 = {0,1};
        double[] expectedArray2 = {1};
        double[] inputArray3 = {1,0};
        double[] expectedArray3 = {1};
        double[] inputArray4 = {1,1};
        double[] expectedArray4 = {0};

        set.add(inputArray1,expectedArray1);
        set.add(inputArray2,expectedArray2);
        set.add(inputArray3,expectedArray3);
        set.add(inputArray4,expectedArray4);

        network.learn(set);

        System.out.println("Test 1 "+network.evaluate(inputArray1)[0]);
        System.out.println("Test 2 "+network.evaluate(inputArray2)[0]);
        System.out.println("Test 3 "+network.evaluate(inputArray3)[0]);
        System.out.println("Test 4 "+network.evaluate(inputArray4)[0]);


        assertEquals(0,1);
    }

    @Test
    public void SimpleFFTestNNBuilderAnd() throws IOException {
        NNBuilder builder = new NNBuilder();


        Dim3Struct weights2 = new Dim3Struct(4,2,1);
        double[] weightValues2 = {0.00001,0.00002,0.00003,0.00001,0.00001,0.0002,0.0001,0.00002};
        UtilityMethods.PopulateDimStruct(weights2,weightValues2);

        Dim3Struct outputWeights = new Dim3Struct(1,4,1);
        double[] outputWeightsValues = {0.01,0.02,0.03,0.01};
        UtilityMethods.PopulateDimStruct(outputWeights, outputWeightsValues);

        NeuralNetwork network = builder.addInputBlock(new Dim3Struct.Dims(2,1,1))
                .addFullyConnectedBlock(4,new Sigmoid()).addWeights(weights2)
                .addOutputLayer(1,new MSE()).addWeights(outputWeights)
                .build();

        SGD sgd = new SGD();
        sgd.setLearningRate(0.0001);
        sgd.setMaxIterations(100);
        network.setLearningRule(sgd);


        DataSet set = new DataSet();
        set.setSampleInputSize(2);
        set.setSampleExpectedOutputSize(1);


        double[] inputArray1 = {0,0};
        double[] expectedArray1 = {0};
        double[] inputArray2 = {0,1};
        double[] expectedArray2 = {0};
        double[] inputArray3 = {1,0};
        double[] expectedArray3 = {0};
        double[] inputArray4 = {1,1};
        double[] expectedArray4 = {1};

        set.add(inputArray1,expectedArray1);
        set.add(inputArray2,expectedArray2);
        set.add(inputArray3,expectedArray3);
        set.add(inputArray4,expectedArray4);

        network.learn(set);

        assertEquals(0,1);
    }
}
