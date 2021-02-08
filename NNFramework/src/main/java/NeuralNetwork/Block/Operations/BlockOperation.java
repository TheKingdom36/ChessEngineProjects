package NeuralNetwork.Block.Operations;

import NeuralNetwork.Block.Dim3Struct;
import NeuralNetwork.Block.NeuralNetwork;
import lombok.Getter;
import lombok.Setter;


public abstract class BlockOperation implements Operation<Dim3Struct> {

    @Getter @Setter
    protected Dim3Struct neurons;
    @Getter @Setter
    protected Dim3Struct deltas;

    protected BlockOperation(){

    }

    public Dim3Struct doOp(Dim3Struct input){
        neurons = blockOpCal(input);
        return neurons.Copy();
    }

    /**
     * Used to calculate the deltas of the operation neurons from the deltas passed in
     *
     * @param inputDeltas Values required to compute the neurons deltas
     * @return
     */
    public Dim3Struct calculateDeltas(Dim3Struct inputDeltas){
        deltas = blockOpCalDeltas(inputDeltas);

        return deltas.Copy();
    }

    protected abstract Dim3Struct blockOpCal(Dim3Struct input);



    public abstract Dim3Struct blockOpCalDeltas(Dim3Struct inputDeltas);

}
