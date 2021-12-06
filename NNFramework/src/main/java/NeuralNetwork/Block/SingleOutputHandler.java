package NeuralNetwork.Block;

import NeuralNetwork.LossFunctions.LossFunction;
import NeuralNetwork.Utils.Dim4Struct;

import java.util.List;

public class SingleOutputHandler implements OutputBlockHandler<SingleFeatureBlock> {

    private SingleFeatureBlock singleFeatureBlock;

    public SingleOutputHandler(SingleFeatureBlock featureBlock){
        singleFeatureBlock = featureBlock;
    }


    @Override
    public List<double[]> getOutput() {
        return null;
    }

    @Override
    public void calculateWeightsErrors(Dim4Struct struct, List<double[]> expectedArrays) {

    }


    @Override
    public double calculateLoss(List<LossFunction> functions,List<double[]> expected) {
        double[] actualArray = singleFeatureBlock.getOutput().toArray();

        double lossValue = 0;


        //Should only be one of each
        LossFunction function = functions.get(0);
        double[] expectedArray = expected.get(0);

        lossValue = function.calculate(actualArray,expectedArray);

        return lossValue;
    }
}
