package NeuralNetwork.Utils;

import java.util.ArrayList;
import java.util.List;

public class NetworkRow implements IDataSetRow<Dim3Struct, List<double[]>> {

    private Dim3Struct input;

    private List<double[]> output;

    public NetworkRow(){
        output = new ArrayList<>();
    }

    @Override
    public Dim3Struct getInput() {
        return input;
    }

    @Override
    public void setInput(Dim3Struct input) {
        this.input = input;
    }


    @Override
    public List<double[]> getExpectedOutput() {
        return output;
    }

    @Override
    public void setExpectedOutput(List<double[]> expectedOutputs) {
this.output = expectedOutputs;
    }


    public void addExpectedOutput(double[] outputs) {
        this.output.add(outputs);
    }

    @Override
    public boolean isSupervised() {
        return false;
    }

    @Override
    public double[] toArray() {
        return new double[0];
    }

    @Override
    public String toCSV() {
        return null;
    }
}
