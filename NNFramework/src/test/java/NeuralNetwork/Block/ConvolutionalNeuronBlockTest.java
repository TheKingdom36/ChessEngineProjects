package NeuralNetwork.Block;


import NeuralNetwork.ActivationFunctions.ActivationFunction;
import NeuralNetwork.ActivationFunctions.None;
import NeuralNetwork.Utils.Dim3Struct;
import org.junit.jupiter.api.Test;
import NeuralNetwork.Utils.UtilityMethods;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

class ConvolutionalNeuronBlockTest {

    @Test
    void testBlockCalculationStride_3Padding_1Kernel3_3_3Input7_7_3() {

        Dim3Struct.Dims inputNeuronDims = new Dim3Struct.Dims(7 ,7 ,3);
        Dim3Struct.Dims blockNeuronDims = new Dim3Struct.Dims(3 ,3 ,3);
        int stride = 3;
        int padding = 1;
        int kernelWidth = 3;
        int kernelLength = 3;
        int numOfKernels = 3;
        ActivationFunction func = new None();
        double[] we = {1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1};
        ArrayList<Dim3Struct> weights = new ArrayList<>(3);
        weights.add(new Dim3Struct(3 ,3 ,3));
        weights.add(new Dim3Struct(3 ,3 ,3));
        weights.add(new Dim3Struct(3 ,3 ,3));

        weights.forEach(x -> UtilityMethods.PopulateDimStruct(x ,we));


        //Set up conv block and test block with a few examples

        ConvolutionalBlock block = new ConvolutionalBlock(inputNeuronDims ,stride ,padding ,kernelWidth ,kernelLength ,numOfKernels ,func);

        block.setWeights(weights);

        block.setUp();

        Dim3Struct input = new Dim3Struct(inputNeuronDims);

        input.perValueOperation(x -> 1d);

        Dim3Struct result = block.calculate(input);

        Dim3Struct expectedResult = new Dim3Struct(3 ,3 ,3);
        double[] values = {12.0 ,18.0 ,12.0 ,
                18.0 ,27.0 ,18.0 ,
                12.0 ,18.0 ,12.0 ,

                12.0 ,18.0 ,12.0 ,
                18.0 ,27.0 ,18.0 ,
                12.0 ,18.0 ,12.0 ,

                12.0 ,18.0 ,12.0 ,
                18.0 ,27.0 ,18.0 ,
                12.0 ,18.0 ,12.0};

        UtilityMethods.PopulateDimStruct(expectedResult ,values);


        for (int i = 0; i < result.getWidth(); i++) {
            for (int j = 0; j < result.getLength(); j++) {
                for (int k = 0; k < result.getDepth(); k++) {
                    assertEquals(result.getValues()[i][j][0] ,expectedResult.getValues()[i][j][0] ,0.0001);
                }
            }

        }
    }
/*
    @Test
    void testBlockCalculationStride_1Padding_0Kernel3_3_1Input5_5_1() {

        Dim3Struct.Dims inputNeuronDims = new Dim3Struct.Dims(5 ,5 ,1);
        Dim3Struct.Dims blockNeuronDims = new Dim3Struct.Dims(3 ,3 ,1);
        int stride = 1;
        int padding = 0;
        int kernelWidth = 3;
        int kernelLength = 3;
        int numOfKernels = 1;
        ActivationFunction func = new None();

        //Set Up weights for con block
        double[] we = {1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1};
        ArrayList<Dim3Struct> weights = new ArrayList<>(3);
        weights.add(new Dim3Struct(3 ,3 ,1));
        weights.forEach(x -> UtilityMethods.PopulateDimStruct(x ,we));


        //Set up conv block and test block with a few examples

        ConvolutionalBlock block = new ConvolutionalBlock(weights ,inputNeuronDims ,blockNeuronDims ,stride ,padding ,kernelWidth ,kernelLength ,numOfKernels ,func);

        block.setUp();

        Dim3Struct input = new Dim3Struct(inputNeuronDims);

        input.perValueOperation(x -> 1d);

        Dim3Struct result = block.calculate(input);

        Dim3Struct expectedResult = new Dim3Struct(3 ,3 ,1);
        double[] values = {9.0 ,9.0 ,9.0 ,
                9.0 ,9.0 ,9.0 ,
                9.0 ,9.0 ,9.0 };

        UtilityMethods.PopulateDimStruct(expectedResult ,values);


        for (int i = 0; i < result.getWidth(); i++) {
            for (int j = 0; j < result.getLength(); j++) {
                for (int k = 0; k < result.getDepth(); k++) {
                    assertEquals(result.getValues()[i][j][0] ,expectedResult.getValues()[i][j][0] ,0.0001);
                }
            }
        }
    }
    @Test
    void TestBackWardPassCalculationsAllOnesStride_1Padding_0Kernel3_3_1Input5_5_1_netCCC(){

        Dim3Struct.Dims inputNeuronDims = new Dim3Struct.Dims(5 ,5 ,1);
        Dim3Struct.Dims blockNeuronDims = new Dim3Struct.Dims(3 ,3 ,1);
        int stride = 1;
        int padding = 0;
        int kernelWidth = 3;
        int kernelLength = 3;
        int numOfKernels = 1;
        ActivationFunction func = new None();

        Dim3Struct input = new Dim3Struct(inputNeuronDims);
        input.perValueOperation(x -> 1d);

        //Set Up weights for con block
        double[] we = {1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1};

        ArrayList<Dim3Struct> weights = new ArrayList<>();
        weights.add(new Dim3Struct(kernelWidth ,kernelLength ,inputNeuronDims.getDepth()));
        weights.forEach(x -> UtilityMethods.PopulateDimStruct(x ,we));

        int nextKernelWidth =3;
        int nextKernelLength =3;
        int numNextKernels = 1;
        ArrayList<Dim3Struct> nextWeights = new ArrayList<>();
        nextWeights.add(new Dim3Struct( nextKernelWidth,nextKernelLength,numNextKernels));
        nextWeights.forEach(x -> UtilityMethods.PopulateDimStruct(x ,we));

        Dim3Struct nextNeuronErrors = new Dim3Struct((blockNeuronDims.getWidth()- nextKernelWidth + 2*padding)/stride +1,(blockNeuronDims.getLength()- nextKernelLength + 2*padding)/stride +1,numNextKernels);
        nextNeuronErrors.getValues()[0][0][0] = 1;

        ConvolutionalBlock previousBlock = mock(ConvolutionalBlock.class);
        when(previousBlock.getOutputNeurons()).thenReturn(input);

        ConvolutionalBlock nextBlock = mock(ConvolutionalBlock.class);
        when(nextBlock.getWeights()).thenReturn(nextWeights);
        when(nextBlock.getNeuronErrors()).thenReturn(nextNeuronErrors);

        //Set up conv block and test block with a few examples

        ConvolutionalBlock block = new ConvolutionalBlock(weights ,inputNeuronDims ,blockNeuronDims ,stride ,padding ,kernelWidth ,kernelLength ,numOfKernels ,func);

        block.setUp();

        Dim3Struct result = block.calculate(input);

        block.calculateErrors(previousBlock,nextBlock);

        Dim3Struct neuronErrors = block.getNeuronErrors();

        ArrayList<Dim3Struct> weightErrors = block.getWeightErrors();


        Dim3Struct actualWeightError = new Dim3Struct(3,3,1);
        double[] weightErrorValues = {9,9,9,9,9,9,9,9,9};
        UtilityMethods.PopulateDimStruct(actualWeightError,weightErrorValues);



        ConvolutionalBlockTestUtilities.assertEqualsBetweenDimStructs(actualWeightError,weightErrors.get(0));

    }

    @Test
    void TestBackWardPassCalculationsAllOnesStride_1Padding_0Kernel3_3_3_3Input5_5_3_netCCC(){

        Dim3Struct.Dims inputNeuronDims = new Dim3Struct.Dims(5 ,5 ,3);
        Dim3Struct.Dims blockNeuronDims = new Dim3Struct.Dims(3 ,3 ,3);
        int stride = 1;
        int padding = 0;
        int kernelWidth = 3;
        int kernelLength = 3;
        int numOfKernels = 3;
        ActivationFunction func = new None();


        Dim3Struct input = new Dim3Struct(inputNeuronDims);
        input.perValueOperation(x -> 1d);

        //Set Up weights for con block
        double[] we = {1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1};

        ArrayList<Dim3Struct> weights = new ArrayList<>();
        for(int n=0;n<numOfKernels;n++){
            weights.add(new Dim3Struct(kernelWidth ,kernelLength ,inputNeuronDims.getDepth()));

        }
        weights.forEach(x -> UtilityMethods.PopulateDimStruct(x ,we));

        int nextKernelWidth =3;
        int nextKernelLength =3;
        int numNextKernels = 3;
        ArrayList<Dim3Struct> nextWeights = new ArrayList<>();
        nextWeights.add(new Dim3Struct( nextKernelWidth,nextKernelLength,numNextKernels));
        nextWeights.forEach(x -> UtilityMethods.PopulateDimStruct(x ,we));

        Dim3Struct nextNeuronErrors = new Dim3Struct((blockNeuronDims.getWidth()- nextKernelWidth + 2*padding)/stride +1,(blockNeuronDims.getLength()- nextKernelLength + 2*padding)/stride +1,numNextKernels);
        nextNeuronErrors.getValues()[0][0][0] = 1;


        ConvolutionalBlock previousBlock = mock(ConvolutionalBlock.class);
        when(previousBlock.getOutputNeurons()).thenReturn(input);

        ConvolutionalBlock nextBlock = mock(ConvolutionalBlock.class);
        when(nextBlock.getWeights()).thenReturn(nextWeights);
        when(nextBlock.getNeuronErrors()).thenReturn(nextNeuronErrors);

        //Set up conv block and test block with a few examples

        ConvolutionalBlock block = new ConvolutionalBlock(weights ,inputNeuronDims ,blockNeuronDims ,stride ,padding ,kernelWidth ,kernelLength ,numOfKernels ,func);

        block.setUp();

        Dim3Struct result = block.calculate(input);

        block.calculateErrors(previousBlock,nextBlock);

        Dim3Struct neuronErrors = block.getNeuronErrors();

        ArrayList<Dim3Struct> weightErrors = block.getWeightErrors();


        Dim3Struct actualWeightError = new Dim3Struct(3,3,3);
        double[] weightErrorValues = {27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27};
        UtilityMethods.PopulateDimStruct(actualWeightError,weightErrorValues);


        ConvolutionalBlockTestUtilities.assertEqualsBetweenDimStructs(actualWeightError,weightErrors.get(0));

    }
*/

}