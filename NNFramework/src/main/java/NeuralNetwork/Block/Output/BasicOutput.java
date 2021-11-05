package NeuralNetwork.Block.Output;

import lombok.Getter;
import lombok.Setter;

public class BasicOutput implements NetworkOutput {
    @Getter @Setter
    private double[] output;

    public BasicOutput(double[] output){
        this.output = output;
    }
}
