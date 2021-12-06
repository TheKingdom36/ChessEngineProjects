package NeuralNetwork.Block;

import NeuralNetwork.Utils.Dim4Struct;

@FunctionalInterface
public interface BranchBlockCreator {

    public BranchBlock create(Dim4Struct inputs);

}
