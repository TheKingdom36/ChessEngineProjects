package Block;

import NeuralNetwork.Block.ActivationFunctions.ReLU;
import NeuralNetwork.Block.Dim3Struct;
import NeuralNetwork.Block.FullyConnectedBlock;
import NeuralNetwork.UtilityMethods;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class TestFullyConnectedBlock {
    @Test
    public void SingleFFBlockForwardPass(){
        Dim3Struct input = new Dim3Struct(3,1,1);

        //[0.1,5,0.7]
        input.getValues()[0][0][0]=0.1;
        input.getValues()[1][0][0]=0.5;
        input.getValues()[2][0][0]=0.7;

        Dim3Struct weights = new Dim3Struct(3,3,1);
        double[] weightValues = {1,2,3.4,0.5,0.8,6.9,0.7,0.8,10};
        UtilityMethods.PopulateDimStruct(weights,weightValues);

        FullyConnectedBlock FCBlock = new FullyConnectedBlock(input.getWidth(),3,new ReLU());
        FCBlock.setWeights(weights);

        FCBlock.calculate(input);

        Dim3Struct expectedOutput = new Dim3Struct(3,1,1);
        ReLU relu = new ReLU();
        double[] expectedValues = {3.28,5.28,7.47};
        UtilityMethods.PopulateDimStruct(expectedOutput,expectedValues);


        assertEquals(expectedOutput,FCBlock.getNeurons());
    }

    @Test
    public void SingleFFBlockForwardPassWeight34(){
        Dim3Struct input = new Dim3Struct(4,1,1);

        //[0.1,5,0.7]
        input.getValues()[0][0][0]=0.1;
        input.getValues()[1][0][0]=0.5;
        input.getValues()[2][0][0]=0.7;
        input.getValues()[3][0][0]=0.7;

        Dim3Struct weights = new Dim3Struct(3,4,1);
        double[] weightValues = {1,2,3.4,0.5,0.8,6.9,0.7,0.8,10,4,5,4};
        UtilityMethods.PopulateDimStruct(weights,weightValues);

        FullyConnectedBlock FCBlock = new FullyConnectedBlock(input.getWidth(),3,new ReLU());
        FCBlock.setWeights(weights);

        FCBlock.calculate(input);

        Dim3Struct expectedOutput = new Dim3Struct(3,1,1);
        ReLU relu = new ReLU();
        double[] expectedValues = {3.83,4.58,9.3};
        UtilityMethods.PopulateDimStruct(expectedOutput,expectedValues);


        assertEquals(expectedOutput,FCBlock.getNeurons());
    }

    @Test
    public void SingleFFBlockForwardPassRELUWithNegInputs(){
        Dim3Struct input = new Dim3Struct(3,1,1);

        //[0.1,5,0.7]
        input.getValues()[0][0][0]=-0.1;
        input.getValues()[1][0][0]=-0.5;
        input.getValues()[2][0][0]=-0.7;

        Dim3Struct weights = new Dim3Struct(3,3,1);
        double[] weightValues = {1,2,3.4,0.5,0.8,6.9,0.7,0.8,10};
        UtilityMethods.PopulateDimStruct(weights,weightValues);

        FullyConnectedBlock FCBlock = new FullyConnectedBlock(input.getWidth(),3,new ReLU());
        FCBlock.setWeights(weights);

        FCBlock.calculate(input);

        Dim3Struct expectedOutput = new Dim3Struct(3,1,1);
        ReLU relu = new ReLU();
        double[] expectedValues = {0,0,0};
        UtilityMethods.PopulateDimStruct(expectedOutput,expectedValues);


        assertEquals(expectedOutput,FCBlock.getNeurons());
    }


    @Test
    public void SingleFFBlockForwardPassSingleInputOutput(){
        Dim3Struct input = new Dim3Struct(1,1,1);


        input.getValues()[0][0][0]=0.3;

        Dim3Struct weights = new Dim3Struct(1,1,1);
        double[] weightValues = {0.5};
        UtilityMethods.PopulateDimStruct(weights,weightValues);


        FullyConnectedBlock FCBlock = new FullyConnectedBlock(input.getWidth(),1,new ReLU());
        FCBlock.setWeights(weights);
        FCBlock.calculate(input);


        Dim3Struct expectedOutput = new Dim3Struct(1,1,1);
        ReLU relu = new ReLU();
        double[] expectedValues = {relu.getOutput(0.15)};
        UtilityMethods.PopulateDimStruct(expectedOutput,expectedValues);

        assertEquals(expectedOutput,FCBlock.getNeurons());
    }

    @Test
    public void twoLayerFFBlockForwardPass(){
        Dim3Struct input = new Dim3Struct(3,1,1);

        //[0.1,5,0.7]
        input.getValues()[0][0][0]=0.1;
        input.getValues()[1][0][0]=0.5;
        input.getValues()[2][0][0]=0.7;

        Dim3Struct weights = new Dim3Struct(3,3,1);
        double[] weightValues = {1,2,3.4,0.5,0.8,6.9,0.7,0.8,10};
        UtilityMethods.PopulateDimStruct(weights,weightValues);
        FullyConnectedBlock FCBlock = new FullyConnectedBlock(input.getWidth(),3,new ReLU());
        FCBlock.setWeights(weights);
        FCBlock.calculate(input);


        Dim3Struct weights2 = new Dim3Struct(3,3,1);
        double[] weightValues2 = {1,2,3.4,0.5,0.8,6.9,0.7,0.8,10};
        UtilityMethods.PopulateDimStruct(weights2,weightValues2);
        FullyConnectedBlock FCBlock2 = new FullyConnectedBlock(FCBlock.getNeurons().getWidth(),3,new ReLU());
        FCBlock2.setWeights(weights2);
        FCBlock2.calculate(FCBlock.getNeurons());


        Dim3Struct expectedOutput = new Dim3Struct(3,1,1);
        ReLU relu = new ReLU();
        double[] expectedValues = {8.484,15.98,46.394};
        UtilityMethods.PopulateDimStruct(expectedOutput,expectedValues);


        assertEquals(expectedOutput,FCBlock2.getNeurons());
    }

}
