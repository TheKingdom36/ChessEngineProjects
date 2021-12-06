package NeuralNetwork.Block;

import NeuralNetwork.ActivationFunctions.ReLU;
import NeuralNetwork.Utils.Dim4Struct;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BranchBlockTest {

    @Test
    public void createEmptyBranchBlock(){

        BranchBlock block = new BranchBlock.builder(new Dim4Struct.Dims(1,1,1,1)).build();

        assertEquals(1,block.getTopBlockPath().size());
        assertEquals(1,block.getBottomBlockPath().size());
    }

    @Test
    public void createBranchBlockWithSingleBlockInEachPath(){

        BranchBlock block = new BranchBlock
                .builder(new Dim4Struct.Dims(1,1,1,1))
                .addFCToPath(3,new ReLU(), BranchBlock.builder.PATH.TOP)
                .addFCToPath(3,new ReLU(), BranchBlock.builder.PATH.BOTTOM)
                .build();

        assertEquals(block.getTopBlockPath().size(),2);
        assertEquals(block.getBottomBlockPath().size(),2);

    }

    @Test
    public void createBranchBlockWithConvBlocksBlockInEachPath(){

        BranchBlock block = new BranchBlock
                .builder(new Dim4Struct.Dims(1,1,3,3))
                .addConvToPath(1,0,3,3,3,new ReLU(), BranchBlock.builder.PATH.TOP)
                .addConvToPath(1,0,3,3,3,new ReLU(), BranchBlock.builder.PATH.BOTTOM)
                .build();

        assertEquals(block.getTopBlockPath().size(),2);
        assertEquals(block.getBottomBlockPath().size(),2);

    }

    @Test
    public void createBranchBlockWithUnequalLengthPaths(){

        BranchBlock block = new BranchBlock
                .builder(new Dim4Struct.Dims(1,1,3,3))
                .addConvToPath(1,0,3,3,3,new ReLU(), BranchBlock.builder.PATH.TOP)
                .addFCToPath(3,new ReLU(), BranchBlock.builder.PATH.TOP)
                .addConvToPath(1,0,3,3,3,new ReLU(), BranchBlock.builder.PATH.BOTTOM)
                .build();

        assertEquals(block.getTopBlockPath().size(),3);
        assertEquals(block.getBottomBlockPath().size(),2);

    }

    @Test
    public void createBranchBlockWithLongPaths(){

        BranchBlock block = new BranchBlock
                .builder(new Dim4Struct.Dims(1,1,3,3))
                .addConvToPath(1,0,3,3,3,new ReLU(), BranchBlock.builder.PATH.TOP)
                .addConvToPath(1,0,1,1,3,new ReLU(),BranchBlock.builder.PATH.TOP)
                .addFCToPath(3,new ReLU(), BranchBlock.builder.PATH.TOP)
                .addFCToPath(3,new ReLU(),BranchBlock.builder.PATH.TOP)
                .addFCToPath(3,new ReLU(), BranchBlock.builder.PATH.TOP)
                .addFCToPath(3,new ReLU(),BranchBlock.builder.PATH.TOP)
                .addConvToPath(1,0,3,3,3,new ReLU(), BranchBlock.builder.PATH.BOTTOM)
                .addConvToPath(1,0,1,1,3,new ReLU(),BranchBlock.builder.PATH.BOTTOM)
                .addFCToPath(3,new ReLU(), BranchBlock.builder.PATH.BOTTOM)
                .addFCToPath(3,new ReLU(),BranchBlock.builder.PATH.BOTTOM)
                .addFCToPath(3,new ReLU(), BranchBlock.builder.PATH.BOTTOM)
                .addFCToPath(3,new ReLU(),BranchBlock.builder.PATH.BOTTOM)
                .build();

        assertEquals(block.getTopBlockPath().size(),7);
        assertEquals(block.getBottomBlockPath().size(),7);

    }

    @Test
    public void forwardPassFCLayerEachPathBranchBlock(){
        double[] weights = {1,1,1};

        Dim4Struct FCWeights = new Dim4Struct(1,1,3,1);
        FCWeights.populate(weights);
        Dim4Struct FCWeights2 = new Dim4Struct(1,1,3,1);
        FCWeights2.populate(weights);

        BranchBlock block = new BranchBlock
                .builder(new Dim4Struct.Dims(1,1,1,1))
                .addFCToPath(3,new ReLU(), BranchBlock.builder.PATH.TOP).withWeights(FCWeights)
                .addFCToPath(3,new ReLU(), BranchBlock.builder.PATH.BOTTOM).withWeights(FCWeights2)
                .build();

        Dim4Struct input = new Dim4Struct(1,1,1,1);
        input.setValue(1,0,0,0,0);

        Dim4Struct result = block.calculate(input);

        Dim4Struct expectedResult = new Dim4Struct(2,1,3,1);
        double[] expected = {1,1,1,1,1,1};
        expectedResult.populate(expected);



        assertEquals(expected,result);

    }

}