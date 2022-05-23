package ChessEngine;

import MontoCarlo.PolicyOutput;

public class NeuralNetworkPolicyOutput extends PolicyOutput {
    public NeuralNetworkPolicyOutput(int winScore ,double[] probabilities) {
        super(winScore ,probabilities);
    }
}
