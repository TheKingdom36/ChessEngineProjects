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
        double[] expectedValues = {3.48,5.28,7.47};
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


        assertEquals(expectedOutput,FCBlock.getOutputNeurons());
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


        assertEquals(expectedOutput,FCBlock.getOutputNeurons());
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
        double[] weightValues = {1,2,3.4,0.5,0.8,6.9,0.7,0.8,10};
        UtilityMethods.PopulateDimStruct(weights,weightValues);

        FullyConnectedBlock FCB = new FullyConnectedBlock(3,3,new ReLU());
        FCB.calculate(input);
        FCB.setWeights(weights);

        Dim3Struct inputDeltas = new Dim3Struct(3,1,1);

        inputDeltas.getValues()[0][0][0]=0.3;
        inputDeltas.getValues()[1][0][0]=0.2;
        inputDeltas.getValues()[2][0][0]=0.2;

        Dim3Struct nextWeights = new Dim3Struct(3,3,1);
        UtilityMethods.PopulateDimStruct(nextWeights,weightValues);


        FCB.calculateErrors(inputDeltas,nextWeights);


        Dim3Struct expectedNeuronErrors = new Dim3Struct(3,1,1);
        expectedNeuronErrors.getValues()[0][0][0]= 0.54;
        expectedNeuronErrors.getValues()[1][0][0]= 0.92;
        expectedNeuronErrors.getValues()[2][0][0]=4.4;
        assertEquals(expectedNeuronErrors,FCB.getNeuronErrors());

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
    public void BackwardPassFullyConnectedBlockWeights34input41(){

        Dim3Struct input = new Dim3Struct(4,1,1);
        //[0.1,5,0.7]
        input.getValues()[0][0][0]=0.1;
        input.getValues()[1][0][0]=0.5;
        input.getValues()[2][0][0]=0.7;
        input.getValues()[3][0][0]=0.7;
        Dim3Struct weights = new Dim3Struct(3,4,1);
        double[] weightValues = {1,2,3.4,0.5,0.8,6.9,0.7,0.8,10,4,5,4};
        UtilityMethods.PopulateDimStruct(weights,weightValues);

        FullyConnectedBlock FCB = new FullyConnectedBlock(4,3,new ReLU());
        FCB.calculate(input);
        FCB.setWeights(weights);

        Dim3Struct inputDeltas = new Dim3Struct(3,1,1);

        inputDeltas.getValues()[0][0][0]=0.3;
        inputDeltas.getValues()[1][0][0]=0.2;
        inputDeltas.getValues()[2][0][0]=0.2;

        Dim3Struct nextWeights = new Dim3Struct(3,4,1);
        UtilityMethods.PopulateDimStruct(nextWeights,weightValues);


        FCB.calculateErrors(inputDeltas,nextWeights);

        Dim3Struct expectedNeuronErrors = new Dim3Struct(3,1,1);
        expectedNeuronErrors.getValues()[0][0][0]= 0.54;
        expectedNeuronErrors.getValues()[1][0][0]= 0.92;
        expectedNeuronErrors.getValues()[2][0][0]=4.4;
        //assertEquals(expectedNeuronErrors,FCB.getNeuronErrors());


        Dim3Struct expectedWeightErrors = new Dim3Struct(3,4,1);
        double[] weightErrors = {1,2,3.4,0.5,0.8,6.9,0.7,0.8,10,4,5,4};
        UtilityMethods.PopulateDimStruct(expectedWeightErrors,weightErrors);

        assertEquals(expectedWeightErrors,FCB.getWeightErrors());

    }

}
