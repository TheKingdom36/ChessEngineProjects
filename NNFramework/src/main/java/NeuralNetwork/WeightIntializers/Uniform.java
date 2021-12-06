package NeuralNetwork.WeightIntializers;

import NeuralNetwork.Utils.Dim4Struct;
import lombok.Getter;
import lombok.Setter;

public class Uniform implements WeightInitializer{

    @Getter @Setter
    private InitializerType type;
    private double min;
    private double max;

    public Uniform(double max, double min){
        this.min = min;
        this.max = max;
        this.type = InitializerType.uniform;
    }

    public Dim4Struct generate(Dim4Struct.Dims dims){

        Dim4Struct struct = new Dim4Struct(dims);

        double[] values = new double[struct.totalNumOfValues()];

        for(int i=0; i<values.length;i++){
            values[i] = (Math.random() * ((max - min))) + min;
        }

        struct.populate(values);

        return struct;
    }
}
