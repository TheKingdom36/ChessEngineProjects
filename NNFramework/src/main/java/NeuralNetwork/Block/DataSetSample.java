package NeuralNetwork.Block;

import java.io.Serializable;
import java.util.Arrays;


/**
 * This class represents a single data row in a data set. It has input and
 * desired output for supervised learning rules. It can also be used only with
 * input for unsupervised learning rules.
 *
 */
public class DataSetSample implements Serializable {

    /**
     * The class fingerprint that is set to indicate serialization compatibility
     * with a previous version of the class
     */
    private static final long serialVersionUID = 1L;
    /**
     * Input vector for this row.
     */
    protected double[] input;

    /**
     * Desired output used for supervised learning algorithms.
     */
    private double[] expectedOutput;


    /**
     * Creates new training element with specified input and desired output
     * vectors
     *
     * @param input input array
     * @param desiredOutput desired output array
     */
    public DataSetSample(double[] input, double[] desiredOutput) {
        this.input = input;
        this.expectedOutput = desiredOutput;
    }

    /**
     * Creates new training element with input array
     *
     * @param input input array
     */
    public DataSetSample(double... input) {
        this.input = input;
    }



    public double[] getInput() {
        return this.input;
    }

    public void setInput(double[] input) {
        this.input = input;
    }

    public double[] getExpectedOutput() {
        return expectedOutput;
    }

    public void setExpectedOutput(double[] expectedOutput) {
        this.expectedOutput = expectedOutput;
    }

    public boolean isSupervised() {
        return (expectedOutput != null);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Input: ");
        for (double in : input) {
            sb.append(in).append(", ");
        }
        sb.delete(sb.length() - 2, sb.length() - 1);

        if (isSupervised()) {
            sb.append(" Desired output: ");
            for (double out : expectedOutput) {
                sb.append(out).append(", ");
            }
            sb.delete(sb.length() - 2, sb.length() - 1);
        }

        return sb.toString();
    }

    public double[] toArray() {
        double[] row = new double[input.length + expectedOutput.length];
        System.arraycopy(input, 0, row, 0, input.length);
        System.arraycopy(expectedOutput, 0, row, input.length, expectedOutput.length);
        return row;
    }

    public String toCSV() {
        StringBuilder sb = new StringBuilder();

        for (double in : input) {
            sb.append(in).append(", ");
        }

        if (isSupervised()) {
            for (double out : expectedOutput) {
                sb.append(out).append(", ");
            }
        }

        sb.delete(sb.length() - 2, sb.length() - 1);

        return sb.toString();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DataSetSample other = (DataSetSample) obj;
        if (!Arrays.equals(this.input, other.input)) {
            return false;
        }
        if (!Arrays.equals(this.expectedOutput, other.expectedOutput)) {
            return false;
        }
        return true;
    }

}