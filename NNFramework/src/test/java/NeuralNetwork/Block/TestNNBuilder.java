package NeuralNetwork.Block;

import Events.LearningEvent;
import Events.LearningEventListener;
import NeuralNetwork.ActivationFunctions.ReLU;
import NeuralNetwork.ActivationFunctions.Sigmoid;
import NeuralNetwork.Learning.LearningRule;
import NeuralNetwork.Learning.SGD;
import NeuralNetwork.LossFunctions.MSE;
import NeuralNetwork.Mnist.MnistDataReaderDataSet;
import NeuralNetwork.NNBuilders.NNBuilder;
import NeuralNetwork.Operations.SoftmaxOp;
import NeuralNetwork.Utils.DataSet;
import NeuralNetwork.Utils.PolicyDataSetRow;
import NeuralNetwork.Utils.Dim3Struct;
import NeuralNetwork.WeightIntializers.Xavier;
import NeuralNetwork.WeightIntializers.uniformDistribution;
import NeuralNetwork.Utils.UtilityMethods;
import org.junit.Test;

import java.io.IOException;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class TestNNBuilder {
 /*   @Test
    public void CreateOneLayerFFWithNNBuilder(){
        NNBuilder builder = new NNBuilder();


        Dim3Struct weights = new Dim3Struct(3,4,1);
        double[] weightValues = {0.1,0.2,0.4,0.5,0.8,0.9,0.7,0.8,0.1,0.34,0.4,0.4};
        UtilityMethods.PopulateDimStruct(weights,weightValues);

        Dim3Struct outputWeights = new Dim3Struct(3,3,1);
        double[] outputWeightsValues = {0.1,.2,0.4,0.5,0.8,0.9,0.7,0.8,0.2};
        UtilityMethods.PopulateDimStruct(outputWeights, outputWeightsValues);

        BasicNetwork network = builder.addInputBlock(new Dim3Struct.Dims(4,1,1))
                .addFullyConnectedBlock(3,new ReLU()).addWeights(weights)
                .addPolicyOutputBlock(3,new MSE()).addWeights(outputWeights)
                .build();

        SGD sgd = new SGD();
        sgd.setMaxIterations(1000);
        network.setLearningRule(sgd);


        DataSet set = new DataSet();
        set.setSampleInputSize(4);


        double[] inputArray = {0.1,0.1,0.1,0.1};
        double[] expectedArray = {1,1,1};

        PolicyDataSetRow newRow = new PolicyDataSetRow(inputArray,expectedArray);

        set.add(newRow);
       // network.learn(set);

        assertEquals(0,1);
    }

    @Test
    //increase number of hidden layers
    public void CreateMnistFFWithNNBuilder() throws IOException {
        //TODO adjust weights to read in
        NNBuilder builder = new NNBuilder();
        DataSet<PolicyDataSetRow> trainingSet;
        DataSet<PolicyDataSetRow> testSet;
        int numOfNeurons = 128;
        Random rand = new Random();

        Dim3Struct weights = new Dim3Struct(numOfNeurons,28*28,1);
        double[] we = uniformDistribution.generateValues(numOfNeurons*28*28,0.1,-0.1);
        UtilityMethods.PopulateDimStruct(weights,we);

        Dim3Struct outputWeights = new Dim3Struct(10,numOfNeurons,1);
        we = uniformDistribution.generateValues(10*numOfNeurons,0.1,-0.1);
        UtilityMethods.PopulateDimStruct(outputWeights,we);

        BasicNetwork<LearningRule,double[]> network = builder.addInputBlock(new Dim3Struct.Dims(28*28,1,1))
                .addFullyConnectedBlock(numOfNeurons,new Sigmoid()).addWeights(weights)
                .addPolicyOutputBlock(10,new MSE()).addWeights(outputWeights).withPostOperation(new SoftmaxOp())
                .build();

        MnistDataReaderDataSet reader = new MnistDataReaderDataSet();
        trainingSet = new DataSet();
        testSet = new DataSet();

        PolicyDataSetRow[] samples = reader.readData("C:/Users/danielmurphy/IntelljProjects/ChessEngineProjects/NNFramework/src/test/java/NeuralNetwork/Block/data/train-images.idx3-ubyte", "C:/Users/danielmurphy/IntelljProjects/ChessEngineProjects/NNFramework/src/test/java/NeuralNetwork/Block/data/train-labels.idx1-ubyte");
        int trainSetSize = 2000;
        int testSetSize = 100;

        SGD sgd = new SGD();
        sgd.setMaxIterations(5);
        sgd.setLearningRate(0.01);

        sgd.addListener(new LearningEventListener() {
            @Override
            public void handleLearningEvent(LearningEvent event) {
                if(event.getEventType() == LearningEvent.Type.EPOCH_ENDED){
                    double totalLoss = 0;
                    for(int i=0;i<testSetSize;i++) {
                        double[] result = network.evaluate(testSet.getSamples().get(i).getInput());

                        double loss = network.loss(testSet.getSamples().get(i).getExpectedOutput());

                        totalLoss += loss;
                    }

                    System.out.println("Epoch total loss " + totalLoss);
                }
            }
        });

        network.setLearningRule(sgd);




        for(int i=0;i<trainSetSize;i++){
            trainingSet.add(samples[i]);
        }

        for(int i=trainSetSize;i<trainSetSize+testSetSize;i++){
            testSet.add(samples[i]);
        }

        network.learn(trainingSet);


        assertEquals(0,1);
    }

    @Test
    //increase number of hidden layers
    public void CreateMnistFFWithNNBuilder2Layers() throws IOException {
        //TODO adjust weightsLayer1 to read in
        NNBuilder builder = new NNBuilder();
        DataSet<PolicyDataSetRow> trainingSet;
        DataSet<PolicyDataSetRow> testSet;
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

        BasicNetwork<LearningRule,double[]> network = builder.addInputBlock(new Dim3Struct.Dims(28*28,1,1))
                .addFullyConnectedBlock(numOfNeuronsLayer1,new ReLU()).addWeights(weightsLayer1)
                .addFullyConnectedBlock(numOfNeuronsLayer2,new ReLU()).addWeights(weightsLayer2)
                .addPolicyOutputBlock(10,new MSE()).addWeights(outputWeights)
                .build();



        MnistDataReaderDataSet reader = new MnistDataReaderDataSet();
        trainingSet = new DataSet();
        testSet = new DataSet();

        PolicyDataSetRow[] samples = reader.readData("C:/Users/danielmurphy/IntelljProjects/ChessEngineProjects/NNFramework/src/test/java/NeuralNetwork/Block/data/train-images.idx3-ubyte", "C:/Users/danielmurphy/IntelljProjects/ChessEngineProjects/NNFramework/src/test/java/NeuralNetwork/Block/data/train-labels.idx1-ubyte");
        int trainSetSize = 2000;
        int testSetSize = 100;

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

                        double[] result = network.evaluate(testSet.getSamples().get(i).getInput());

                        int highest =0;

                        for(int r=1;r<result.length;r++){
                            if(result[highest]<result[r]){
                                highest = r;
                            }
                        }

                        double loss = network.loss(testSet.getSamples().get(i).getExpectedOutput());
                        totalLoss += loss;

                        int expected=0;
                        for(int e=0;e<testSet.getSamples().get(i).getExpectedOutput().length;e++){
                            if(testSet.getSamples().get(i).getExpectedOutput()[e]==1){
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




        for(int i=0;i<trainSetSize;i++){
            trainingSet.add(samples[i]);
        }

        for(int i=trainSetSize;i<trainSetSize+testSetSize;i++){
            testSet.add(samples[i]);
        }

        network.learn(trainingSet);


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

        BasicNetwork network = builder.addInputBlock(new Dim3Struct.Dims(2,1,1))
                .addFullyConnectedBlock(6,new Sigmoid()).addWeights(weights2)
                .addPolicyOutputBlock(1,new MSE()).addWeights(outputWeights)
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

        set.add(new PolicyDataSetRow(inputArray1,expectedArray1));
        set.add(new PolicyDataSetRow(inputArray2,expectedArray2));
        set.add(new PolicyDataSetRow(inputArray3,expectedArray3));
        set.add(new PolicyDataSetRow(inputArray4,expectedArray4));

        network.learn(set);

        System.out.println("Test 1 "+((double[])network.evaluate(inputArray1))[0]);
        System.out.println("Test 2 "+((double[])network.evaluate(inputArray2))[0]);
        System.out.println("Test 3 "+((double[])network.evaluate(inputArray3))[0]);
        System.out.println("Test 4 "+((double[])network.evaluate(inputArray4))[0]);


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

        BasicNetwork network = builder.addInputBlock(new Dim3Struct.Dims(2,1,1))
                .addFullyConnectedBlock(4,new Sigmoid()).addWeights(weights2)
                .addPolicyOutputBlock(1,new MSE()).addWeights(outputWeights)
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

        set.add(new PolicyDataSetRow(inputArray1,expectedArray1));
        set.add(new PolicyDataSetRow(inputArray2,expectedArray2));
        set.add(new PolicyDataSetRow(inputArray3,expectedArray3));
        set.add(new PolicyDataSetRow(inputArray4,expectedArray4));

        network.learn(set);

        assertEquals(0,1);
    }*/
}
