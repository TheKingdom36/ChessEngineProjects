package Block;

import NeuralNetwork.Block.*;
import NeuralNetwork.Block.ActivationFunctions.ReLU;
import NeuralNetwork.Block.LossFunctions.MSE;
import NeuralNetwork.UtilityMethods;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestNNBuilder {
    @Test
    public void CreateOneLayerFFWithNNBuilder(){
        NNBuilder builder = new NNBuilder();
        Dim3Struct input = new Dim3Struct(4,1,1);

        //[0.1,5,0.7]
        input.getValues()[0][0][0]=0.1;
        input.getValues()[1][0][0]=0.5;
        input.getValues()[2][0][0]=0.7;
        input.getValues()[3][0][0]=0.7;

        Dim3Struct weights = new Dim3Struct(3,4,1);
        double[] weightValues = {1,2,3.4,0.5,0.8,6.9,0.7,0.8,10,4,5,4};
        UtilityMethods.PopulateDimStruct(weights,weightValues);

        NeuralNetwork network = builder.addInputBlock(new Dim3Struct.Dims(3,1,1))
                .addFullyConnectedBlock(3,new ReLU())
                .addOutputLayer(3,new MSE())
                .build();

        SGD sgd = new SGD();
        sgd.setMaxIterations(10);
        network.setLearningRule(sgd);

        network.evaluate(input);
        DataSet set = new DataSet();
        set.setSampleInputSize(4);
        set.setSampleExpectedOutputSize(3);

        double[] inputArray = {3,3,3,3};
        double[] expectedArray = {1,1,1};
        set.add(inputArray,expectedArray);
        System.out.println("here2");
        network.learn(set);

        assertEquals(0,1);
    }
}
