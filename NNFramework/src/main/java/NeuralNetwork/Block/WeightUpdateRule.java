package NeuralNetwork.Block;

public interface WeightUpdateRule {
     double calculate(double weight,double weightDelta);
}
