package NeuralNetwork.Block.Output;

import lombok.Getter;
import lombok.Setter;

public class ValuePolicyOutput implements NetworkOutput {
    @Getter @Setter
    private double value;
    @Getter @Setter
    private double[] policy;
}
