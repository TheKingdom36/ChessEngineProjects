package Block;

import NeuralNetwork.Block.Dim3Struct;
import NeuralNetwork.Block.LossFunctions.MSE;
import NeuralNetwork.Block.Operations.SoftmaxOp;
import NeuralNetwork.Block.BasicOutputBlock;
import NeuralNetwork.UtilityMethods;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;


class OutputBlockTest {

/*
    //execute only once, in the starting
    @BeforeClass
    public static void beforeClass() {


    }

    @Test
    void calculateLossFunc() {
    }

    @Test
    void blockCalculation() {
    }

    @Test
    void calculateWeightErrors() {
    }

    @Test
    void calculateNeuronErrors() {



    }

    @Test
    public void BackwardPassOutputBlock(){

        Dim3Struct input = new Dim3Struct(3,1,1);
        //[0.1,5,0.7]
        input.getValues()[0][0][0]=0.1;
        input.getValues()[1][0][0]=0.5;
        input.getValues()[2][0][0]=0.7;

        Dim3Struct weights = new Dim3Struct(3,3,1);
        double[] weightValues = {1d,2d,3.4,0.5,0.8,6.9,0.7,0.8,10d};
        UtilityMethods.PopulateDimStruct(weights,weightValues);

        BasicOutputBlock outBl = new BasicOutputBlock(3,3,new MSE());

        outBl.setWeights(weights);
        outBl.calculate(input);

        double[] correctFpass = {3.48 ,5.28, 7.47 };
        assertArrayEquals(outBl.getOutputNeurons().toArray(),correctFpass);

        double[] expectedValues = {4,6,8};

        assertEquals(outBl.calculateLossFunc(expectedValues),0.3565666666666667);

        outBl.calculateErrors(null,null);

        Dim3Struct expectedNeuronErrors = new Dim3Struct(3,1,1);
        expectedNeuronErrors.getValues()[0][0][0]= -0.3466666666666667;
        expectedNeuronErrors.getValues()[1][0][0]= -0.4799999999999998;
        expectedNeuronErrors.getValues()[2][0][0]=-0.3533333333333335 ;
        assertEquals(expectedNeuronErrors,outBl.getNeuronErrors());


        Dim3Struct expectedWeightErrors = new Dim3Struct(3,3,1);
        double[] weightErrors = {-0.03466666, -0.0479999, -0.0353333,
                -0.173333333, -0.2399999, -0.1766666,
                -0.24266666 ,-0.335999, -0.24733333, };

        UtilityMethods.PopulateDimStruct(expectedWeightErrors,weightErrors);
        System.out.println(expectedWeightErrors);
        System.out.println(outBl.getWeightErrors().toString());

        for(int i=0;i< expectedWeightErrors.getWidth();i++){
            for(int j=0;j<expectedWeightErrors.getLength();j++){
                assertEquals(expectedWeightErrors.getValues()[i][j][0],outBl.getWeightErrors().getValues()[i][j][0],0.0001);
            }
        }
    }

    @Test
    public void OutputBlockWithSoftmax(){
        Dim3Struct input = new Dim3Struct(3,1,1);
        input.getValues()[0][0][0]=1;
        input.getValues()[1][0][0]=1;
        input.getValues()[2][0][0]=1;

        Dim3Struct output = new Dim3Struct(3,1,1);
        output.getValues()[0][0][0]=1;
        output.getValues()[1][0][0]=1;
        output.getValues()[2][0][0]=1;

        BasicOutputBlock block = new BasicOutputBlock(3,3,new MSE());
        Dim3Struct weights = new Dim3Struct(3,3,1);
        double[] weightValues = {1d,1d,1d,1d,1d,1d,1d,1d,1d};
        UtilityMethods.PopulateDimStruct(weights,weightValues);

        block.addToPostNeuronOperations(new SoftmaxOp());
        block.setWeights(weights);

        block.calculate(input);
        double loss = block.calculateLossFunc(output.toArray());

        assertEquals(0.44444444,loss,0.0001);

        block.calculateErrors(null,null);

        Dim3Struct weightErrors = block.getWeightErrors();

        Dim3Struct expectedErrors = new Dim3Struct(3,3,1);

        System.out.println(weightErrors);

    }*/
}