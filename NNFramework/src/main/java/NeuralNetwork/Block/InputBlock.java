package NeuralNetwork.Block;

import NeuralNetwork.Operations.BlockOperation;
import NeuralNetwork.Utils.Dim3Struct;
import NeuralNetwork.Utils.Dim4Struct;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


public class InputBlock implements Block {

    @Getter @Setter
    Dim4Struct outputNeurons;

    List<BlockOperation> blockOperations;

public InputBlock(Dim4Struct.Dims inputSize){


    outputNeurons = new Dim4Struct(inputSize);
    blockOperations = new ArrayList<>();
}


    @Override
    public void verifyBlock() {

    }

    @Override
    public Dim4Struct calculate(Dim4Struct input) {
    outputNeurons = input.Copy();

    blockOperations.forEach(b -> outputNeurons = b.doOp(outputNeurons));

        return outputNeurons;
    }

    @Override
    public void setUp() {

    }

    @Override
    public Dim4Struct getOutput() {
        return outputNeurons;
    }

    @Override
    public void addToPostCalculationOperations(BlockOperation operation) {
        blockOperations.add(operation);
    }
}
