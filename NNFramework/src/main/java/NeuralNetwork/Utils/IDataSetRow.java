package NeuralNetwork.Utils;

import java.io.Serializable;

public interface IDataSetRow<Input,Output> extends Serializable {
    Input getInput();

    void setInput(Input input);

    Output getExpectedOutput();

    void setExpectedOutput(Output expectedOutput);

    boolean isSupervised();

    @Override
    String toString();

    double[] toArray();

    String toCSV();

    @Override
    int hashCode();

    @Override
    boolean equals(Object obj);
}
