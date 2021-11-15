package NeuralNetwork.Block;

import Events.LearningEvent;
import Events.LearningEventListener;
import NeuralNetwork.ActivationFunctions.ReLU;
import NeuralNetwork.Learning.SGD;
import NeuralNetwork.LossFunctions.MSE;
import NeuralNetwork.Mnist.MnistDataReaderDataSet;
import NeuralNetwork.NNBuilders.ConvNNBuilder;
import NeuralNetwork.Networks.ConvNetwork;
import NeuralNetwork.Operations.FlattenOp;
import NeuralNetwork.Operations.SoftmaxOp;
import NeuralNetwork.Utils.*;
import NeuralNetwork.WeightIntializers.uniformDistribution;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class ConvNNBuilderTest {


    //increase number of hidden layers
    @Test
    public void FullCreateMnistConvWithConvNNBuilder() throws IOException {
        CreateMnistConvWithConvNNBuilder(40000,1000);
    }

    @Test
    public void ShortCreateMnistConvWithConvNNBuilder() throws IOException {
        CreateMnistConvWithConvNNBuilder(10000,1000);

    }

    private void CreateMnistConvWithConvNNBuilder(int trainSetSize,int testSetSize) throws IOException {
        ConvNNBuilder builder = new ConvNNBuilder();
        NetworkDataSet trainingSet;
        NetworkDataSet testSet;
        int numOfNeurons = 128;
        Random rand = new Random();

        Dim4Struct FCWeights = new Dim4Struct(1,1,numOfNeurons,1728);
        populateWeights(FCWeights);

        Dim4Struct FCWeights2 = new Dim4Struct(1,1,numOfNeurons,128);
        populateWeights(FCWeights2);

        Dim4Struct convWeights= new Dim4Struct(3,1,3,3);
        populateWeights(convWeights);


        Dim4Struct convWeights2= new Dim4Struct(3,3,3,3);
        populateWeights(convWeights2);

        Dim4Struct outputWeights = new Dim4Struct(1,1,10,numOfNeurons);
        populateWeights(outputWeights);



        ConvNetwork network = new ConvNetwork.builder().addInputBlock(new Dim4Struct.Dims(1,1,28,28))
                .addConvBlock(1,0,3,3,3,new ReLU()).addWeights(convWeights)
                .addConvBlock(1,0,3,3,3,new ReLU()).addWeights(convWeights2)
                .addFullyConnectedBlock(numOfNeurons,new ReLU()).addWeights(FCWeights)
                .addOutputBlock(10,new MSE())
                .addWeights(outputWeights).withPostOperation(new SoftmaxOp())
                .build();

        MnistDataReaderDataSet reader = new MnistDataReaderDataSet();

        trainingSet = new NetworkDataSet();
        testSet = new NetworkDataSet();

        SGD sgd = new SGD();
        sgd.setMaxIterations(5);
        sgd.setLearningRate(0.01);

        sgd.addListener(new LearningEventListener() {
            @Override
            public void handleLearningEvent(LearningEvent event) {
                if(event.getEventType() == LearningEvent.Type.EPOCH_ENDED){
                    double totalLoss = 0;

                    int correctCount=0;
                    int incorrectCount=0;
                    for(int i=0;i<testSetSize;i++) {
                        double[] result = network.evaluate(testSet.getSample(i).getInput()).get(0);

                        int highest =0;

                        for(int r=1;r<result.length;r++){
                            if(result[highest]<result[r]){
                                highest = r;
                            }
                        }

                        double loss = network.loss(testSet.getSamples().get(i).getExpectedOutput());
                        totalLoss += loss;

                        int expected=0;
                        for(int e=0;e<testSet.getSamples().get(i).getExpectedOutput().get(0).length;e++){
                            if(testSet.getSamples().get(i).getExpectedOutput().get(0)[e]==1){
                                expected=e;
                                break;
                            }
                        }

                        if(highest == expected){
                            correctCount++;
                        }else {
                            incorrectCount++;
                        }
                    }

                    System.out.println("Epoch total loss " + totalLoss);
                    System.out.println("Correct" + correctCount);
                    System.out.println("Incorrect "+incorrectCount);
                }
            }
        });

        network.setLearningRule(sgd);



        NetworkRow[] samples = reader.readData("C:/Users/danielmurphy/IntelljProjects/ChessEngineProjects/NNFramework/src/test/java/NeuralNetwork/Block/data/train-images.idx3-ubyte", "C:/Users/danielmurphy/IntelljProjects/ChessEngineProjects/NNFramework/src/test/java/NeuralNetwork/Block/data/train-labels.idx1-ubyte");

        for(int i=0;i<trainSetSize;i++){
            trainingSet.add(samples[i]);
        }

        for(int i=trainSetSize;i<trainSetSize+testSetSize;i++){
            testSet.add(samples[i]);
        }

        network.learn(trainingSet);


        assertEquals(0,1);
    }

    public void populateWeights(Dim4Struct toPopulate){
        double[] we = uniformDistribution.generateValues(toPopulate.totalNumOfValues(),0.1,-0.1);
        UtilityMethods.PopulateDimStruct(toPopulate,we);

    }
}