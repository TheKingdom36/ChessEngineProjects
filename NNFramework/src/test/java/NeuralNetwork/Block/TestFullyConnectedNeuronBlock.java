package NeuralNetwork.Block;

import NeuralNetwork.ActivationFunctions.ReLU;
import NeuralNetwork.Utils.Dim3Struct;
import NeuralNetwork.Utils.UtilityMethods;
import junit.framework.TestCase;
import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class TestFullyConnectedNeuronBlock {
    @Test
    public void SingleFFBlockForwardPass(){
        Dim3Struct input = new Dim3Struct(3,1,1);

        //[0.1,5,0.7]
        input.getValues()[0][0][0]=0.1;
        input.getValues()[1][0][0]=0.5;
        input.getValues()[2][0][0]=0.7;

        Dim3Struct weights = new Dim3Struct(3,3,1);
        double[] weightValues = {1d,2d,3.4,0.5,0.8,6.9,0.7,0.8,10d};
        UtilityMethods.PopulateDimStruct(weights,weightValues);

        FullyConnectedBlock FCBlock = new FullyConnectedBlock(input.getWidth(),3,new ReLU());
        FCBlock.setUp();
FCBlock.setWeights(weights);
        FCBlock.calculate(input);

        Dim3Struct expectedOutput = new Dim3Struct(3,1,1);
        ReLU relu = new ReLU();
        double[] expectedValues = {3.48,5.28,7.47};
        UtilityMethods.PopulateDimStruct(expectedOutput,expectedValues);


        assertEquals(expectedOutput,FCBlock.getOutput());
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
        double[] weightValues = {1d,2d,3.4,0.5,0.8,6.9,0.7,0.8,10d,4d,5d,4d};
        UtilityMethods.PopulateDimStruct(weights,weightValues);

        FullyConnectedBlock FCBlock = new FullyConnectedBlock(input.getWidth(),3,new ReLU());
        FCBlock.setUp();
        FCBlock.setWeights(weights);

        FCBlock.calculate(input);

        Dim3Struct expectedOutput = new Dim3Struct(3,1,1);
        ReLU relu = new ReLU();
        double[] expectedValues = {3.83,4.58,9.3};
        UtilityMethods.PopulateDimStruct(expectedOutput,expectedValues);


        assertEquals(expectedOutput,FCBlock.getOutput());
    }

    @Test
    public void SingleFFBlockForwardPassRELUWithNegInputs(){
        Dim3Struct input = new Dim3Struct(3,1,1);

        //[0.1,5,0.7]
        input.getValues()[0][0][0]=-0.1;
        input.getValues()[1][0][0]=-0.5;
        input.getValues()[2][0][0]=-0.7;

        Dim3Struct weights = new Dim3Struct(3,3,1);
        double[] weightValues = {1d,2d,3.4,0.5,0.8,6.9,0.7,0.8,10d};
        UtilityMethods.PopulateDimStruct(weights,weightValues);

        FullyConnectedBlock FCBlock = new FullyConnectedBlock(input.getWidth(),3,new ReLU());
        FCBlock.setUp();
        FCBlock.setWeights(weights);

        FCBlock.calculate(input);

        Dim3Struct expectedOutput = new Dim3Struct(3,1,1);
        ReLU relu = new ReLU();
        double[] expectedValues = {0d,0d,0d};
        UtilityMethods.PopulateDimStruct(expectedOutput,expectedValues);


        assertEquals(expectedOutput,FCBlock.getOutput());
    }


    @Test
    public void SingleFFBlockForwardPassSingleInputOutput(){
        Dim3Struct input = new Dim3Struct(1,1,1);


        input.getValues()[0][0][0]=0.3;

        Dim3Struct weights = new Dim3Struct(1,1,1);
        double[] weightValues = {0.5};
        UtilityMethods.PopulateDimStruct(weights,weightValues);


        FullyConnectedBlock FCBlock = new FullyConnectedBlock(input.getWidth(),1,new ReLU());
        FCBlock.setUp();
        FCBlock.setWeights(weights);
        FCBlock.calculate(input);


        Dim3Struct expectedOutput = new Dim3Struct(1,1,1);
        ReLU relu = new ReLU();
        double[] expectedValues = {relu.getOutput(0.15)};
        UtilityMethods.PopulateDimStruct(expectedOutput,expectedValues);

        assertEquals(expectedOutput,FCBlock.getOutput());
    }

    @Test
    public void twoLayerFFBlockForwardPass(){
        Dim3Struct input = new Dim3Struct(3,1,1);

        //[0.1,5,0.7]
        input.getValues()[0][0][0]=0.1;
        input.getValues()[1][0][0]=0.5;
        input.getValues()[2][0][0]=0.7;

        Dim3Struct weights = new Dim3Struct(3,3,1);
        double[] weightValues = {1d,2d,3.4,0.5,0.8,6.9,0.7,0.8,10d};
        UtilityMethods.PopulateDimStruct(weights,weightValues);
        FullyConnectedBlock FCBlock = new FullyConnectedBlock(input.getWidth(),3,new ReLU());
        FCBlock.setUp();
        FCBlock.setWeights(weights);
        FCBlock.calculate(input);


        Dim3Struct weights2 = new Dim3Struct(3,3,1);
        double[] weightValues2 = {1d,2d,3.4,0.5,0.8,6.9,0.7,0.8,10d};
        UtilityMethods.PopulateDimStruct(weights2,weightValues2);
        FullyConnectedBlock FCBlock2 = new FullyConnectedBlock(FCBlock.getNeurons().getWidth(),3,new ReLU());
        FCBlock2.setUp();
        FCBlock2.setWeights(weights2);
        FCBlock2.calculate(FCBlock.getNeurons());


        Dim3Struct expectedOutput = new Dim3Struct(3,1,1);
        ReLU relu = new ReLU();
        double[] expectedValues = {39.438,57.507,81.36};
        UtilityMethods.PopulateDimStruct(expectedOutput,expectedValues);


        assertEquals(expectedOutput,FCBlock2.getNeurons());
    }


    @Test
    public void BackwardPassFullyConnectedBlock(){

        Dim3Struct input = new Dim3Struct(3,1,1);
        //[0.1,5,0.7]
        input.getValues()[0][0][0]=0.1;
        input.getValues()[1][0][0]=0.5;
        input.getValues()[2][0][0]=0.7;

        Dim3Struct weights = new Dim3Struct(3,3,1);
        double[] weightValues = {1d,2d,3.4,0.5,0.8,6.9,0.7,0.8,10d};
        UtilityMethods.PopulateDimStruct(weights,weightValues);

        FullyConnectedBlock FCB = new FullyConnectedBlock(3,3,new ReLU());
        FCB.setUp();
        FCB.setWeights(weights);
       FCB.calculate(input);
        Dim3Struct nextNeuronErrors = new Dim3Struct(3,1,1);

        nextNeuronErrors.getValues()[0][0][0]=0.3;
        nextNeuronErrors.getValues()[1][0][0]=0.2;
        nextNeuronErrors.getValues()[2][0][0]=0.2;

        Dim3Struct nextWeights = new Dim3Struct(3,3,1);
        UtilityMethods.PopulateDimStruct(nextWeights,weightValues);

        FullyConnectedBlock previousBlock = mock(FullyConnectedBlock.class);
        when(previousBlock.getOutput()).thenReturn(input);

        FullyConnectedBlock nextBlock = mock(FullyConnectedBlock.class);
        when(nextBlock.getWeights()).thenReturn(nextWeights);
        when(nextBlock.getNeuronErrors()).thenReturn(nextNeuronErrors);

        FCB.calculateErrors(previousBlock,nextBlock);


        Dim3Struct expectedNeuronErrors = new Dim3Struct(3,1,1);
        expectedNeuronErrors.getValues()[0][0][0]= 0.54;
        expectedNeuronErrors.getValues()[1][0][0]= 0.92;
        expectedNeuronErrors.getValues()[2][0][0]=4.4;
        assertEquals(expectedNeuronErrors,FCB.getNeuronErrors());
        System.out.println(FCB.getWeightErrors());

        Dim3Struct expectedWeightErrors = new Dim3Struct(3,3,1);
        double[] weightErrors = {0.054,0.27,	0.378,
                0.092,	0.46,	0.644,
                0.44,	2.2,	3.08};
        UtilityMethods.PopulateDimStruct(expectedWeightErrors,weightErrors);

        for(int i=0;i< expectedWeightErrors.getWidth();i++){
            for(int j=0;j<expectedWeightErrors.getLength();j++){
                assertEquals(expectedWeightErrors.getValues()[i][j][0],FCB.getWeightErrors().getValues()[i][j][0],0.0001);
            }
        }
    }

    @Test
    public void BackwardPassFullyConnectedBlockWeights44input41(){

        Dim3Struct input = new Dim3Struct(4,1,1);
        //[0.1,5,0.7]
        input.getValues()[0][0][0]=0.1;
        input.getValues()[1][0][0]=0.5;
        input.getValues()[2][0][0]=0.7;
        input.getValues()[3][0][0]=0.7;
        Dim3Struct weights = new Dim3Struct(4,4,1);
        double[] weightValues = {1d,2d,3.4,0.5,0.8,6.9,0.7,0.8,10d,4d,5d,4d,1d,2d,3.4,0.5};
        UtilityMethods.PopulateDimStruct(weights,weightValues);

        FullyConnectedBlock FCB = new FullyConnectedBlock(4,4,new ReLU());
        FCB.setUp();
        FCB.setWeights(weights);
        FCB.calculate(input);

        double[] expectedValues = {3.83,4.58,9.3,3.83};
        assertArrayEquals(FCB.getOutput().toArray(),expectedValues);


        Dim3Struct nextNeuronErrors = new Dim3Struct(3,1,1);

        nextNeuronErrors.getValues()[0][0][0]=0.3;
        nextNeuronErrors.getValues()[1][0][0]=0.2;
        nextNeuronErrors.getValues()[2][0][0]=0.2;
        Dim3Struct nextWeights = new Dim3Struct(3,4,1);
        UtilityMethods.PopulateDimStruct(nextWeights,weightValues);

        FullyConnectedBlock previousBlock = mock(FullyConnectedBlock.class);
        when(previousBlock.getOutput()).thenReturn(input);

        FullyConnectedBlock nextBlock = mock(FullyConnectedBlock.class);
        when(nextBlock.getWeights()).thenReturn(nextWeights);
        when(nextBlock.getNeuronErrors()).thenReturn(nextNeuronErrors);

        FCB.calculateErrors(previousBlock,nextBlock);

        Dim3Struct expectedNeuronErrors = new Dim3Struct(4,1,1);
        expectedNeuronErrors.getValues()[0][0][0]= 2.46;
        expectedNeuronErrors.getValues()[1][0][0]= 2.7800000000000002;
        expectedNeuronErrors.getValues()[2][0][0]=2.16;
        expectedNeuronErrors.getValues()[3][0][0]=1.11;
        System.out.println(FCB.getNeuronErrors());
        assertEquals(expectedNeuronErrors,FCB.getNeuronErrors());


        Dim3Struct expectedWeightErrors = new Dim3Struct(3,4,1);
        double[] weightErrors = {0.246, 1.23,1.722,1.722,0.278,1.39,1.946,1.946,0.216,1.08,1.512,1.512,0.111,0.555,0.777,0.777};
        UtilityMethods.PopulateDimStruct(expectedWeightErrors,weightErrors);

        for(int i=0;i< expectedWeightErrors.getWidth();i++){
            for(int j=0;j<expectedWeightErrors.getLength();j++){
                TestCase.assertEquals(expectedWeightErrors.getValues()[i][j][0],FCB.getWeightErrors().getValues()[i][j][0],0.0001);
            }
        }

    }



    @Test
    public void BackwardPassFullyConnectedBlockWeights34input41(){

        Dim3Struct input = new Dim3Struct(4,1,1);
        //[0.1,5,0.7]
        input.getValues()[0][0][0]=0.1;
        input.getValues()[1][0][0]=0.5;
        input.getValues()[2][0][0]=0.7;
        input.getValues()[3][0][0]=0.7;
        Dim3Struct weights = new Dim3Struct(3,4,1);
        double[] weightValues = {1d,2d,3.4,0.5,0.8,6.9,0.7,0.8,10d,4d,5d,4d};
        UtilityMethods.PopulateDimStruct(weights,weightValues);

        FullyConnectedBlock FCB = new FullyConnectedBlock(4,3,new ReLU());
        FCB.setUp();
        FCB.setWeights(weights);
        FCB.calculate(input);

        double[] expectedValues = {3.83,4.58,9.3};
        assertArrayEquals(FCB.getOutput().toArray(),expectedValues);


        Dim3Struct nextNeuronErrors = new Dim3Struct(3,1,1);

        nextNeuronErrors.getValues()[0][0][0]=0.3;
        nextNeuronErrors.getValues()[1][0][0]=0.2;
        nextNeuronErrors.getValues()[2][0][0]=0.2;
        Dim3Struct nextWeights = new Dim3Struct(3,4,1);
        UtilityMethods.PopulateDimStruct(nextWeights,weightValues);

        FullyConnectedBlock previousBlock = mock(FullyConnectedBlock.class);
        when(previousBlock.getOutput()).thenReturn(input);

        FullyConnectedBlock nextBlock = mock(FullyConnectedBlock.class);
        when(nextBlock.getWeights()).thenReturn(nextWeights);
        when(nextBlock.getNeuronErrors()).thenReturn(nextNeuronErrors);

        FCB.calculateErrors(previousBlock,nextBlock);

        Dim3Struct expectedNeuronErrors = new Dim3Struct(3,1,1);
        expectedNeuronErrors.getValues()[0][0][0]= 2.46;
        expectedNeuronErrors.getValues()[1][0][0]= 2.7800000000000002;
        expectedNeuronErrors.getValues()[2][0][0]=2.16;

        System.out.println(FCB.getNeuronErrors());
        assertEquals(expectedNeuronErrors,FCB.getNeuronErrors());


        Dim3Struct expectedWeightErrors = new Dim3Struct(3,4,1);
        double[] weightErrors = {0.246, 1.23,1.722,1.722,0.278,1.39,1.946,1.946,0.216,1.08,1.512,1.512};
        UtilityMethods.PopulateDimStruct(expectedWeightErrors,weightErrors);

        for(int i=0;i< expectedWeightErrors.getWidth();i++){
            for(int j=0;j<expectedWeightErrors.getLength();j++){
                TestCase.assertEquals(expectedWeightErrors.getValues()[i][j][0],FCB.getWeightErrors().getValues()[i][j][0],0.0001);
            }
        }

    }

    @Test
    public void BackwardPassFullyConnectedBlockWeights33input31(){

        Dim3Struct input = new Dim3Struct(3,1,1);
        //[0.1,5,0.7]
        input.getValues()[0][0][0]=1;
        input.getValues()[1][0][0]=1;
        input.getValues()[2][0][0]=1;

        Dim3Struct weights = new Dim3Struct(3,3,1);
        double[] weightValues = {1d,1d,1d,1d,1d,1d,1d,1d,1d};
        UtilityMethods.PopulateDimStruct(weights,weightValues);

        FullyConnectedBlock FCB = new FullyConnectedBlock(3,3,new ReLU());
        FCB.setUp();
        FCB.setWeights(weights);
        FCB.calculate(input);


        Dim3Struct nextNeuronErrors = new Dim3Struct(3,1,1);

        nextNeuronErrors.getValues()[0][0][0]=2;
        nextNeuronErrors.getValues()[1][0][0]=2;
        nextNeuronErrors.getValues()[2][0][0]=2;

        Dim3Struct nextWeights = new Dim3Struct(3,3,1);
        UtilityMethods.PopulateDimStruct(nextWeights,weightValues);

        FullyConnectedBlock previousBlock = mock(FullyConnectedBlock.class);
        when(previousBlock.getOutput()).thenReturn(input);

        FullyConnectedBlock nextBlock = mock(FullyConnectedBlock.class);
        when(nextBlock.getWeights()).thenReturn(nextWeights);
        when(nextBlock.getNeuronErrors()).thenReturn(nextNeuronErrors);

        FCB.calculateErrors(previousBlock,nextBlock);

        Dim3Struct expectedNeuronErrors = new Dim3Struct(3,1,1);
        expectedNeuronErrors.getValues()[0][0][0]= 6;
        expectedNeuronErrors.getValues()[1][0][0]= 6;
        expectedNeuronErrors.getValues()[2][0][0]= 6;
        assertEquals(expectedNeuronErrors,FCB.getNeuronErrors());


        Dim3Struct expectedWeightErrors = new Dim3Struct(3,3,1);
        double[] weightErrors = {6d,6d,6d,6d,6d,6d,6d,6d,6d};
        UtilityMethods.PopulateDimStruct(expectedWeightErrors,weightErrors);

        assertEquals(expectedWeightErrors,FCB.getWeightErrors());

    }
}
