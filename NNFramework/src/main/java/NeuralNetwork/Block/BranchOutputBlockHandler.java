package NeuralNetwork.Block;

import NeuralNetwork.LossFunctions.LossFunction;
import NeuralNetwork.Utils.Dim4Struct;

import java.util.List;

public class BranchOutputBlockHandler implements OutputBlockHandler<BranchBlock> {

    BranchBlock block;

    public BranchOutputBlockHandler(BranchBlock block){
        this.block = block;
    }

    @Override
    public List<double[]> getOutput() {
        return null;
    }

    @Override
    public void calculateWeightsErrors(Dim4Struct struct, List<double[]> expectedArrays) {

    }


    @Override
    public double calculateLoss(List<LossFunction> functions, List<double[]> expectedArrays) {
        return 0;
    }
}
