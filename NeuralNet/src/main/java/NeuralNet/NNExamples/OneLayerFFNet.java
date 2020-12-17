package NeuralNet.NNExamples;

import NeuralNet.Interfaces.INetworkWeights;
import NeuralNet.Interfaces.INeuralNetwork;
import NeuralNet.Layers.Layer;
import NeuralNet.Models.NNOutput;
import Common.Plane;

import java.util.List;

public class OneLayerFFNet implements INeuralNetwork {
    @Override
    public NNOutput evaluate(Plane plane) {
        return null;
    }

    @Override
    public List<NNOutput> evaluate(Plane[][] planes) {
        return null;
    }

    @Override
    public NNOutput evaluate(Plane[] planes) {
        return null;
    }

    @Override
    public Layer getInputLayer() {
        return null;
    }

    @Override
    public void configuration(INetworkWeights weights) {

    }

    @Override
    public CNN3LayerNetWeights updateWeights() {
        return null;
    }

    @Override
    public CNN3LayerNetWeights getNetworkWeights() {
        return null;
    }

    @Override
    public void setNumOfOutputNodes(int numOfOutputNodes) {

    }

    @Override
    public int getNumOfOutputNodes() {
        return 0;
    }
}
