package NeuralNetwork.WeightIntializers;

import NeuralNetwork.Utils.Dim4Struct;
import lombok.Getter;
import lombok.Setter;

public class  Xavier implements WeightInitializer{
    @Getter @Setter
    private InitializerType type;


    public Xavier(){
        type = InitializerType.xavier;
    }


    public Dim4Struct generate(Dim4Struct.Dims dims){

        Dim4Struct struct = new Dim4Struct(dims);

        //TODO

        double min =-1 * Math.sqrt(6)/Math.sqrt(dims.getWidth()+dims.getLength());
        double max =Math.sqrt(6)/Math.sqrt(dims.getWidth()+dims.getLength()) ;

        double[] values = new double[struct.totalNumOfValues()];

        for(int i=0; i<values.length;i++){
             values[i] = (Math.random() * ((max - min))) + min;
        }

        struct.populate(values);

        return struct;

    }

}
