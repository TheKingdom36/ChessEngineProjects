package NeuralNetwork.Block.Operations;

import NeuralNetwork.Block.Dim3Struct;
import NeuralNetwork.UtilityMethods;

public class FlattenOp extends BlockOperation{

    Dim3Struct.Dims orgDims;

    @Override
    public Dim3Struct doOp(Dim3Struct input) {

        Dim3Struct newStruct = new Dim3Struct(input.getWidth()*input.getLength()*input.getDepth(),1,1);

        orgDims = input.getDims();

        for(int d=0;d<input.getDepth();d++){
            for(int w=0; w< input.getWidth();w++){
                for(int l=0; l < input.getLength();l++){
                    newStruct.getValues()[w + l + d*input.getDepth()][0][0] = input.getValue(w,l,d);
                }
            }
        }

        outputNeurons = newStruct.Copy();

        return newStruct;
    }

    @Override
    public Dim3Struct calculateDeltas(Dim3Struct inputDeltas) {

        if(inputDeltas.getLength()> 1 || inputDeltas.getWidth() > 1){
            throw new RuntimeException("This deletas should be an X 1 1 dim struct");
        }

        //TODO
        Dim3Struct newDeltas = new Dim3Struct(orgDims);
        double[] values = new double[inputDeltas.totalNumOfValues()];

        for(int i=0; i<values.length;i++){
            values[i] = inputDeltas.getValue(i,0,0);

        }

        UtilityMethods.PopulateDimStruct(newDeltas,values);



        return newDeltas;
    }
}
