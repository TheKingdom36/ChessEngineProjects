package Block;

import NeuralNetwork.Block.Dim3Struct;

import NeuralNetwork.Block.Operations.SoftmaxOp;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;


public class SoftmaxOpTest {

    @Test
    public void DeltaCalculationAllValues1(){
        Dim3Struct neurons = new Dim3Struct(3,1,1);
        Dim3Struct deltas = new Dim3Struct(3,1,1);

        neurons.getValues()[0][0][0] = 1;
        neurons.getValues()[1][0][0] = 1;
        neurons.getValues()[2][0][0] = 1;

        deltas.getValues()[0][0][0] = 1;
        deltas.getValues()[1][0][0] = 1;
        deltas.getValues()[2][0][0] = 1;

        SoftmaxOp op = new SoftmaxOp();

        op.doOp(neurons);
        Dim3Struct output = op.calculateDeltas(deltas);

        double[] expectedOutput = {0,0,0};
        assertArrayEquals(expectedOutput,output.toArray());

        for(int i=0;i<expectedOutput.length;i++){
            assertEquals(expectedOutput[i], output.toArray()[i],0.000001);

        }

    }
    @Test
    public void DeltaCalculationAllValuesLessThen1(){
        Dim3Struct neurons = new Dim3Struct(3,1,1);
        Dim3Struct deltas = new Dim3Struct(3,1,1);

        neurons.getValues()[0][0][0] = 0.4;
        neurons.getValues()[1][0][0] = 0.3;
        neurons.getValues()[2][0][0] = 0.8;

        deltas.getValues()[0][0][0] = 0.2;
        deltas.getValues()[1][0][0] = 0.7;
        deltas.getValues()[2][0][0] = 0.1;

        SoftmaxOp op = new SoftmaxOp();


        op.doOp(neurons);
        Dim3Struct output = op.calculateDeltas(deltas);

        double[] expectedOutput = {-0.068d,0.099d,-0.216d};

        assertArrayEquals(expectedOutput,output.toArray());

    }
}