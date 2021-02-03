package NeuralNetwork.Block.Operations;

import NeuralNetwork.Block.Dim3Struct;
import NeuralNetwork.Block.NeuralNetwork;
import lombok.Getter;
import lombok.Setter;


public abstract class BlockOperation implements Operation<Dim3Struct> {

    protected Dim3Struct neurons;
    @Getter @Setter
    protected Dim3Struct deltas;

    @Getter @Setter
    protected Dim3Struct.Dims outputDims;

    protected BlockOperation(Dim3Struct.Dims outputDims){
        this.outputDims = outputDims;
    }

    protected BlockOperation(){

    }


    /**
     * Used to calculate the deltas of the operation neurons from the deltas passed in
     *
     * @param inputDeltas Values required to compute the neurons deltas
     * @return
     */
    public abstract Dim3Struct calculateDeltas(Dim3Struct inputDeltas);

}
