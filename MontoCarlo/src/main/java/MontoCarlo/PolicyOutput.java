package MontoCarlo;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

public class PolicyOutput {
    @Getter @Setter
    private double winScore;
    @Getter @Setter
    private double[] probabilities;

    public PolicyOutput(double winScore, double[] probabilities){
        this.winScore = winScore;
        this.probabilities = probabilities;
    }

    @Override
    public String toString() {
        return "NNOutput{" +
                "win_score=" + winScore +
                ", probabilities=" + Arrays.toString(probabilities) +
                '}';
    }
}
