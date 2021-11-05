package NeuralNetwork.Utils;

public class ValuePolicyDataSetRow implements IDataSetRow<Dim3Struct,ValuePolicyPair> {

    /**
     * The class fingerprint that is set to indicate serialization compatibility
     * with a previous version of the class
     */
    private static final long serialVersionUID = 1L;
    /**
     * Input vector for this row.
     */
    protected Dim3Struct input;

    /**
     * Desired output used for supervised learning algorithms.
     */
    private ValuePolicyPair expectedOutput;


 @Override
    public Dim3Struct getInput() {
        return input;
    }

    @Override
    public void setInput(Dim3Struct input) {

    }

    @Override
    public ValuePolicyPair getExpectedOutput() {
        return null;
    }

    @Override
    public void setExpectedOutput(ValuePolicyPair expectedOutput) {

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
