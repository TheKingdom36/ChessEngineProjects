package MontoCarlo.interfaces;

import java.util.Arrays;

public abstract class PolicyOutput {
    private double winScore;
    double[] probabilities;


    public double getWinScore() {
        return winScore;
    }

    public void setWinScore(double winScore) {
        this.winScore = winScore;
    }

    public double[] getProbabilities() {
        return probabilities;
    }

    public void setProbabilities(double[] probabilities) {
        this.probabilities = probabilities;
    }

    public PolicyOutput(int winScore, double[] probabilities){
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
