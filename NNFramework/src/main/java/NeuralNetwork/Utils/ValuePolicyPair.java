package NeuralNetwork.Utils;

import lombok.Getter;
import lombok.Setter;

public class ValuePolicyPair {
    @Getter @Setter
private double value;

    @Getter @Setter
private double[] policy;
}
