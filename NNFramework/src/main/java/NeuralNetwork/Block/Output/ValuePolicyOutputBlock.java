package NeuralNetwork.Block.Output;

import NeuralNetwork.ActivationFunctions.ReLU;
import NeuralNetwork.Block.*;
import NeuralNetwork.LossFunctions.LossFunction;
import NeuralNetwork.Operations.BlockOperation;
import NeuralNetwork.Utils.Dim3Struct;
import NeuralNetwork.Utils.ValuePolicyPair;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class ValuePolicyOutputBlock implements OutputBlock<ValuePolicyPair> {

    private FullyConnectedBlock inputFCBlock;
    private List<FullyConnectedBlock> valuePath;
    @Getter @Setter
    private BasicOutputBlock valueOutput;
    private List<FullyConnectedBlock> policyPath;
    @Getter @Setter
    private BasicOutputBlock policyOutput;

    private LossFunction valueLossFunction;
    private LossFunction policyLossFunction;


    public ValuePolicyOutputBlock(){

    }

    public ValuePolicyOutputBlock(int numberOfInputNeurons,LossFunction valueLossFunction,LossFunction policyLossFunction){
        inputFCBlock = new FullyConnectedBlock(numberOfInputNeurons,128,new ReLU());
        this.valuePath = new ArrayList<>();
        this.policyPath = new ArrayList<>();

        this.valueLossFunction = valueLossFunction;
        this.policyLossFunction = policyLossFunction;
    }

    @Override
    public double calculateLossFunc(ValuePolicyPair expected) {

        double[] valueExpected = new double[1];
        valueExpected[0] = expected.getValue();
        return valueLossFunction.calculate(valueOutput.getOutput().get(0),valueExpected) + policyLossFunction.calculate(policyOutput.getOutput().get(0),expected.getPolicy());
    }

    @Override
    public void calculateErrors(WeightBlock previousBlock) {

        //Value Path
        valueOutput.calculateErrors(valuePath.get(valuePath.size()-1));

        if(valuePath.size() == 1){
            valuePath.get(valuePath.size()-1).calculateErrors(inputFCBlock , valueOutput);
        }else{
            valuePath.get(valuePath.size() - 1).calculateErrors(valuePath.get(valuePath.size() -2 ),valueOutput);


            for(int i = valuePath.size()-2; i>=1; i--){
                valuePath.get(i).calculateErrors(valuePath.get(i-1),valuePath.get(i+1));
            }

            valuePath.get(0).calculateErrors(inputFCBlock ,valuePath.get(1) );

        }


        //Policy Path
        policyOutput.calculateErrors(policyPath.get(policyPath.size()-1));

        if(policyPath.size() == 1){
            policyPath.get(policyPath.size()-1).calculateErrors(inputFCBlock , policyOutput);
        }else{
            policyPath.get(policyPath.size() - 1).calculateErrors(policyPath.get(policyPath.size() -2 ),policyOutput);


            for(int i = policyPath.size()-2; i>=1; i--){
                policyPath.get(i).calculateErrors(policyPath.get(i-1),policyPath.get(i+1));
            }

            policyPath.get(0).calculateErrors(inputFCBlock ,policyPath.get(1) );

        }

    }

    @Override
    public void clearWeightErrors() {
        valueOutput.clearWeightErrors();

        for(int i = 0; i< valuePath.size(); i++){
            valuePath.get(i).clearWeightErrors();
        }

        policyOutput.clearWeightErrors();

        for(int i = 0; i< policyPath.size(); i++){
            policyPath.get(i).clearWeightErrors();
        }
    }

    @Override
    public void updateWeights(WeightUpdateRule rule) {
        valueOutput.updateWeights(rule);

        for(int i = 0; i< valuePath.size(); i++){
            valuePath.get(i).updateWeights(rule);
        }

        policyOutput.updateWeights(rule);

        for(int i = 0; i< policyPath.size(); i++){
            policyPath.get(i).updateWeights(rule);
        }
    }

    @Override
    public Dim3Struct getWeights() {

        ValuePolicyWeights weights = new ValuePolicyWeights();

        ArrayList<Object> valueWeights = new ArrayList<>();
        valuePath.forEach(f ->valueWeights.add(f.getWeights()));

        ArrayList<Object> policyWeights = new ArrayList<>();
        policyPath.forEach(f ->policyWeights.add(f.getWeights()));

        weights.setValueWeights(valueWeights);
        weights.setPolicyWeights(policyWeights);
        return new Dim3Struct(1,1,1);
    }

    @Override
    public void setWeights(Dim3Struct weights) {

    }


    public void setWeights(ValuePolicyWeights weights) {
        valueOutput.clearWeightErrors();

        for(int i = 0; i< valuePath.size(); i++){
            valuePath.get(i).clearWeightErrors();
        }

        policyOutput.clearWeightErrors();

        for(int i = 0; i< policyPath.size(); i++){
            policyPath.get(i).clearWeightErrors();
        }

    }

    @Override
    public void calculateErrors(WeightBlock nextBlock ,WeightBlock previousBlock) {
//Need to refactor interfaces
    }

    @Override
    public void resetErrors() {
        valueOutput.resetErrors();
        for(int i = 0; i< valuePath.size(); i++){
            valuePath.get(i).resetErrors();
        }

        policyOutput.resetErrors();
        for(int i = 0; i< policyPath.size(); i++){
            policyPath.get(i).resetErrors();
        }
    }

    @Override
    public void addToPostNeuronOperations(BlockOperation operation) {

    }

    @Override
    public Dim3Struct getNeuronErrors() {
        return inputFCBlock.getNeuronErrors();
    }

    @Override
    public void verifyBlock() {
        inputFCBlock.verifyBlock();
        valuePath.forEach(f->f.verifyBlock());
        policyPath.forEach(f->f.verifyBlock());
    }

    @Override
    public ValuePolicyPair calculate(Dim3Struct input) {



        Dim3Struct pathInput = inputFCBlock.calculate(input);

        valuePath.get(0).calculate(pathInput);

        for (int i = 1; i< valuePath.size() ; i++) {
            valuePath.get(i).calculate(valuePath.get(i-1).getOutput());
        }

        valueOutput.calculate(valuePath.get(valuePath.size()-1).getOutput());


        policyPath.get(0).calculate(pathInput);

        for (int i = 1; i< policyPath.size() ; i++) {
            policyPath.get(i).calculate(policyPath.get(i-1).getOutput());
        }

        policyOutput.calculate(policyPath.get(policyPath.size()-1).getOutput());

        ValuePolicyPair pair = new ValuePolicyPair();
        pair.setValue(valueOutput.getOutput().get(0)[0]);
        pair.setPolicy(policyOutput.getOutput().get(0));

        return pair;
    }

    @Override
    public void setUp() {
        inputFCBlock.setUp();
        valuePath.forEach(f->f.setUp());
        policyPath.forEach(f->f.setUp());
    }

    @Override
    public ValuePolicyPair getOutput() {
        ValuePolicyPair pair = new ValuePolicyPair();
        pair.setValue(valueOutput.getOutput().get(0)[0]);
        pair.setPolicy(policyOutput.getOutput().get(0));
        return pair;
    }


    public void addToValuePath(FullyConnectedBlock block){
     valuePath.add(block);
    }

    public void addPostNeuronOperationValuePath(BlockOperation op){
        valuePath.get(valuePath.size()-1).addToPostNeuronOperations(op);
    }

    public void addPostNeuronOperationPolicyPath(BlockOperation op){
        policyPath.get(policyPath.size()-1).addToPostNeuronOperations(op);
    }

    public class ValuePolicyWeights{
        @Getter @Setter
        private List<Object> valueWeights;
        @Getter @Setter
        private Dim3Struct valueOutputWeights;
        @Getter @Setter
        private List<Object> policyWeights;
        @Getter @Setter
        private Dim3Struct policyOutputWeights;

   }


}