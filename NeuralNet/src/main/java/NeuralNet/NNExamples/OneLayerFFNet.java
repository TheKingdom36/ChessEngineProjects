package NeuralNet.NNExamples;

import NeuralNet.Interfaces.INetworkWeights;
import NeuralNet.Interfaces.INeuralNetwork;
import NeuralNet.Layers.*;
import NeuralNet.Models.Kernel;
import NeuralNet.Models.NNOutput;
import Common.Plane;

import java.util.ArrayList;
import java.util.List;

public class OneLayerFFNet {

    InputLayer inputLayer;
    FullyConnectedLayer FC1;
    OutputLayer outputLayer;

    public OneLayerFFNet(int numOfOutputs){

        FC1 = new FullyConnectedLayer(3);
        outputLayer = new OutputLayer(numOfOutputs);

        FC1.setNextLayer(outputLayer);
        outputLayer.setPreviousLayer(FC1);
    }


    public Plane evaluate(Plane plane) {
        NNOutput nnOutput = new NNOutput();
        Layer.setBatchSize(1);


        inputLayer = new InputLayer(plane);

        inputLayer.setNextLayer(FC1);

        FC1.setPreviousLayer(inputLayer);

        outputLayer.calculateOutputPlanes();
        Plane[][] policy= outputLayer.getOutputPlanes();

        return policy[0][0];

    }


    public List<NNOutput> evaluate(Plane[][] planes) {

        inputLayer = new InputLayer(planes);
return null;

    }


    public NNOutput evaluate(Plane[] planes) {
        return null;
    }


    public Layer getInputLayer() {
        return null;
    }


    public void configuration(INetworkWeights weights) {

    }

    public void setWeights(ArrayList<Integer> weights){
        Kernel[] kernel = new Kernel[1];
        kernel[0] = new Kernel(3,1,1);

        kernel[0].set

        FC1.setKernels();
    }


    public OneLayerFFNetWeights updateWeights() {

        return null;

    }


    public CNN3LayerNetWeights getNetworkWeights() {
        return null;
    }


    public void setNumOfOutputNodes(int numOfOutputNodes) {

    }


    public int getNumOfOutputNodes() {
        return 0;
    }
}
