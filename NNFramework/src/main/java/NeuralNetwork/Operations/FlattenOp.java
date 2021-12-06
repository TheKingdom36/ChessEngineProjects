package NeuralNetwork.Operations;

import NeuralNetwork.Utils.Dim3Struct;
import NeuralNetwork.Utils.Dim4Struct;
import NeuralNetwork.Utils.UtilityMethods;

public class FlattenOp extends BlockOperation{

    Dim4Struct.Dims orgDims;

    @Override
    public Dim4Struct doOp(Dim4Struct input) {

        Dim4Struct newStruct = new Dim4Struct(1,1,input.getWidth()*input.getLength()*input.getChannels(),1);

        orgDims = input.getDims();

        int count = 0;
        for(int n=0;n<input.getNum();n++){
        for(int d=0;d<input.getChannels();d++){
            for(int w=0; w< input.getWidth();w++){
                for(int l=0; l < input.getLength();l++){

                    newStruct.getValues()[0][0][count][0] = input.getValue(n,d,w,l);
                count++;
                }
            }
        }}



        return newStruct;
    }

    @Override
    public Dim4Struct calculateDeltas(Dim4Struct inputDeltas) {

        if(inputDeltas.getLength()> 1 || inputDeltas.getChannels() > 1){
            throw new RuntimeException("This deletas should be an X 1 1 dim struct");
        }


        Dim4Struct newDeltas = new Dim4Struct(orgDims);
        double[] values = new double[inputDeltas.totalNumOfValues()];

        for(int i=0; i<values.length;i++){
            values[i] = inputDeltas.getValue(0,0,i,0);

        }

        UtilityMethods.PopulateDimStruct(newDeltas,values);



        return newDeltas;
    }
}
