package NeuralNet.Interfaces;



import Common.Plane;
import NeuralNet.Layers.Layer;
import NeuralNet.Models.NNOutput;
import NNExamples.CNN3LayerNetWeights;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface INeuralNetwork {
    NNOutput evaluate(Plane plane);

    List<NNOutput> evaluate(Plane[][] planes);

    Layer getInputLayer();

    void configuration(INetworkWeights weights);

    CNN3LayerNetWeights updateWeights();

    CNN3LayerNetWeights getNetworkWeights();
}
