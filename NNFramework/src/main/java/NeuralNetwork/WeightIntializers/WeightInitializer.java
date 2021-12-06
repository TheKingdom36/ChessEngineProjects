package NeuralNetwork.WeightIntializers;

import NeuralNetwork.Utils.Dim4Struct;

public interface WeightInitializer {

    public Dim4Struct generate(Dim4Struct.Dims dims);

    public InitializerType getType();
}
