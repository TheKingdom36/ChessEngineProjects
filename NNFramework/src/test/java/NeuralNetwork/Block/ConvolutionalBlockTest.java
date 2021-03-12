package NeuralNetwork.Block;


import NeuralNetwork.Block.ActivationFunctions.ActivationFunction;
import NeuralNetwork.Block.ActivationFunctions.None;
import NeuralNetwork.Block.ActivationFunctions.Sigmoid;
import org.junit.jupiter.api.Test;
import NeuralNetwork.UtilityMethods;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

class ConvolutionalBlockTest {

    @Test
    void testBlockCalculationStride_3Padding_1Kernel3_3_3Input7_7_3() {

      //  Dim3Struct weights = new Dim3Struct(3,3,3);
       // double[] we = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
       // UtilityMethods.PopulateDimStruct(weights,we);
        ArrayList<Dim3Struct> weights = null;

        Dim3Struct.Dims inputNeuronDims = new Dim3Struct.Dims(7,7,3);
        Dim3Struct.Dims blockNeuronDims = new Dim3Struct.Dims(3,3,3);
        int stride=3;
        int padding=1;
        int kernelWidth=3 ;
        int kernelLength=3;
        int numOfKernels=3;
        ActivationFunction func=new None();

            //Set up conv block and test block with a few examples

        ConvolutionalBlock block = new ConvolutionalBlock(weights,inputNeuronDims,blockNeuronDims, stride,padding,kernelWidth,kernelLength,numOfKernels,func);

        block.setUp();

        Dim3Struct input = new Dim3Struct(inputNeuronDims);

        input.perValueOperation(x -> x = Double.valueOf(1));

        Dim3Struct result = block.calculate(input);

        System.out.print(result);


        Dim3Struct expectedResult = new Dim3Struct(3,3,1);
        double[] values = {12.0, 18.0, 12.0,
                18.0, 27.0, 18.0,
                12.0, 18.0, 12.0,

                12.0, 18.0, 12.0,
                18.0, 27.0, 18.0,
                12.0, 18.0, 12.0,

                12.0, 18.0, 12.0,
                18.0, 27.0, 18.0,
                12.0, 18.0, 12.0};
        UtilityMethods.PopulateDimStruct(expectedResult,values);


        for(int i=0;i< result.getWidth();i++){
            for(int j=0;j<result.getLength();j++){
                for(int k=0;k<result.getDepth();k++){
                assertEquals(result.getValues()[i][j][0],expectedResult.getValues()[i][j][0],0.0001);
            }
        }

    }
}
}