package NeuralNetwork.Block;

public class InputBlock extends Block{



public InputBlock(Dim3Struct.Dims inputSize){
    this.neurons = new Dim3Struct(inputSize);
}

    public void calculateErrors(Dim3Struct InputDeltas) {
        //not needed
    }

    @Override
    public void calculateErrors(Dim3Struct InputDeltas, Dim3Struct nextWeights) {

    }

    @Override
    public Dim3Struct calculate(Dim3Struct InputNeurons) {
        //not needed
        return neurons;
    }
}
