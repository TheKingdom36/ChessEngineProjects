package NeuralNet.NNExamples;

import Common.Plane;
import NeuralNet.Interfaces.INetworkWeights;
import NeuralNet.Interfaces.INeuralNetwork;
import NeuralNet.Layers.*;
import NeuralNet.Models.*;
import lombok.Getter;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;


public class CNN3LayerNet implements INeuralNetwork {

    @Getter
    @Setter
    int NumOfOutputNodes;

    InputLayer inputLayer;
    FullyConnectedLayer fcLayer1;

    BatchNormLayer batchNormLayer1;
    BatchNormLayer batchNormLayer2;
    BatchNormLayer batchNormLayer3;

    ConvLayer convLayer1;
    ConvLayer convLayer2;

    ReLULayer ReLULayer ;
    ReLULayer ReLULayer2 ;
    ReLULayer ReLULayer3;

    OutputLayerWithValueAndPolicyHead outputLayerWithValueAndPolicyHead;

    public CNN3LayerNet(){

    }

    public CNN3LayerNet(CNN3LayerNetWeights CNN3LayerNetWeights,int NumOfOutputNodes){
        this.configuration(CNN3LayerNetWeights);
        this.NumOfOutputNodes = NumOfOutputNodes;
    }

    @Override
    public NNOutput evaluate(Plane inputPlane){
        NNOutput nnOutput = new NNOutput();
        Layer.setBatchSize(1);


        inputLayer = new InputLayer(inputPlane);

        inputLayer.setNextLayer(convLayer1);

        convLayer1.setPreviousLayer(inputLayer);

        outputLayerWithValueAndPolicyHead.calculateOutputPlanes();
        Plane[][] policy= outputLayerWithValueAndPolicyHead.getPolicy();

        double[] probs = new double[policy[0][0].getWidth()];
        for(int i=0;i<policy[0][0].getWidth();i++){
            probs[i] = policy[0][0].getValues()[i][0];
        }



        nnOutput.setProbabilities(probs);
        nnOutput.setWin_score(outputLayerWithValueAndPolicyHead.getValues()[0][0].getValues()[0][0]);

        return nnOutput;



    }




    @Override
    public List<NNOutput> evaluate(Plane[][] inputPlanes) {
        List<NNOutput> nnOutputList = new ArrayList<>();

        Layer.setBatchSize(inputPlanes.length);


        inputLayer = new InputLayer(inputPlanes);

        inputLayer.setNextLayer(convLayer1);

        convLayer1.setPreviousLayer(inputLayer);

        outputLayerWithValueAndPolicyHead.calculateOutputPlanes();

        Plane[][] policys= outputLayerWithValueAndPolicyHead.getPolicy();
        Plane[][] values = outputLayerWithValueAndPolicyHead.getValues();

        double[] tempProbs;
        NNOutput tempNNOutput;
        for(int batchElement=0;batchElement<inputPlanes.length;batchElement++) {

            tempProbs = new double[policys[batchElement][0].getWidth()];
            for (int i = 0; i < policys[batchElement][0].getWidth(); i++) {
                tempProbs[i] = policys[batchElement][0].getValues()[i][0];
            }
            tempNNOutput = new NNOutput();
            tempNNOutput.setProbabilities(tempProbs);
            tempNNOutput.setWin_score(values[batchElement][0].getValues()[0][0]);

            nnOutputList.add(tempNNOutput);
        }



        return nnOutputList;
    }

    @Override
    public NNOutput evaluate(Plane[] planes) {
        Plane[][] tempPlanes = new Plane[1][planes.length];
        return evaluate(tempPlanes).get(0);
    }


    @Override
    public  void configuration (INetworkWeights weights) throws RuntimeException{

        if(!(weights instanceof CNN3LayerNetWeights)){
            throw new RuntimeException("The weights object of class "+weights.getClass()+" is not compatible with this network required CNN3LayerNetWeights");
        }

        fcLayer1 = new FullyConnectedLayer(20);
        fcLayer1.setKernels(((CNN3LayerNetWeights) weights).getFCKernels());


        batchNormLayer1 = new BatchNormLayer();
        batchNormLayer1.setBeta(((CNN3LayerNetWeights) weights).getBatchNorm1BetaValues());
        batchNormLayer1.setGamma(((CNN3LayerNetWeights) weights).getBatchNorm1GammaValues());

        batchNormLayer2 = new BatchNormLayer();
        batchNormLayer2.setBeta(((CNN3LayerNetWeights) weights).getBatchNorm2BetaValues());
        batchNormLayer2.setGamma(((CNN3LayerNetWeights) weights).getBatchNorm2GammaValues());

        batchNormLayer3 = new BatchNormLayer();
        batchNormLayer3.setBeta(((CNN3LayerNetWeights) weights).getBatchNorm3BetaValues());
        batchNormLayer3.setGamma(((CNN3LayerNetWeights) weights).getBatchNorm3GammaValues());

        //number of input planes theres 21 input planes
        convLayer1 = new ConvLayer(10,1,3,3,21,1);
        convLayer1.setKernels(((CNN3LayerNetWeights) weights).getConv1Kernels());

        convLayer2 = new ConvLayer(10,1,3,3,10,1);
        convLayer2.setKernels(((CNN3LayerNetWeights) weights).getConv2Kernels());

        ReLULayer = new ReLULayer();
        ReLULayer2 = new ReLULayer();
        ReLULayer3 = new ReLULayer();

        outputLayerWithValueAndPolicyHead = new OutputLayerWithValueAndPolicyHead(20, NumOfOutputNodes);
        outputLayerWithValueAndPolicyHead.setKernels(((CNN3LayerNetWeights) weights).getOutputKernels());
        outputLayerWithValueAndPolicyHead.setPolicyKernels(((CNN3LayerNetWeights) weights).getPolicyHeadKernels());
        outputLayerWithValueAndPolicyHead.setValueKernels(((CNN3LayerNetWeights) weights).getValueHeadKernels());

        convLayer1.setNextLayer(batchNormLayer1);

        batchNormLayer1.setPreviousLayer(convLayer1);
        batchNormLayer1.setNextLayer(ReLULayer);

        ReLULayer.setPreviousLayer(batchNormLayer1);
        ReLULayer.setNextLayer(convLayer2);

        convLayer2.setPreviousLayer(ReLULayer);
        convLayer2.setNextLayer(batchNormLayer2);

        batchNormLayer2.setPreviousLayer(convLayer2);
        batchNormLayer2.setNextLayer(ReLULayer2);

        ReLULayer2.setPreviousLayer(batchNormLayer2);
        ReLULayer2.setNextLayer(fcLayer1);

        fcLayer1.setPreviousLayer(ReLULayer2);
        fcLayer1.setNextLayer(batchNormLayer3);

        batchNormLayer3.setPreviousLayer(fcLayer1);
        batchNormLayer3.setNextLayer(ReLULayer3);

        ReLULayer3.setPreviousLayer(batchNormLayer3);
        ReLULayer3.setNextLayer(outputLayerWithValueAndPolicyHead);

        outputLayerWithValueAndPolicyHead.setPreviousLayer(ReLULayer3);


    }


    @Override
    public Layer getInputLayer() {
        return inputLayer;
    }

    public synchronized void AssignNewWeights(CNN3LayerNetWeights CNN3LayerNetWeights){
        convLayer1.setKernels(CNN3LayerNetWeights.getConv1Kernels());
        convLayer2.setKernels(CNN3LayerNetWeights.getConv2Kernels());
        outputLayerWithValueAndPolicyHead.setKernels(CNN3LayerNetWeights.getOutputKernels());
        fcLayer1.setKernels(CNN3LayerNetWeights.getFCKernels());
        batchNormLayer1.setBeta(CNN3LayerNetWeights.getBatchNorm1BetaValues());
        batchNormLayer2.setBeta(CNN3LayerNetWeights.getBatchNorm2BetaValues());
        batchNormLayer3.setBeta(CNN3LayerNetWeights.getBatchNorm3BetaValues());
        batchNormLayer1.setGamma(CNN3LayerNetWeights.getBatchNorm1GammaValues());
        batchNormLayer2.setGamma(CNN3LayerNetWeights.getBatchNorm2GammaValues());
        batchNormLayer3.setGamma(CNN3LayerNetWeights.getBatchNorm3GammaValues());

    }

    @Override
    public CNN3LayerNetWeights updateWeights() {
        convLayer1.UpdateWeights();
        convLayer2.UpdateWeights();
        outputLayerWithValueAndPolicyHead.UpdateWeights();
        fcLayer1.UpdateWeights();
        batchNormLayer1.UpdateWeights();
        batchNormLayer2.UpdateWeights();
        batchNormLayer3.UpdateWeights();

        return getNetworkWeights();
    }

    @Override
    public CNN3LayerNetWeights getNetworkWeights() {
        CNN3LayerNetWeights CNN3LayerNetWeights = new CNN3LayerNetWeights();

        CNN3LayerNetWeights.setConv1Kernels(convLayer1.getKernels());
        CNN3LayerNetWeights.setConv2Kernels(convLayer2.getKernels());
        CNN3LayerNetWeights.setFCKernels(fcLayer1.getKernels());
        CNN3LayerNetWeights.setOutputKernels(outputLayerWithValueAndPolicyHead.getKernels());
        CNN3LayerNetWeights.setBatchNorm1GammaValues(batchNormLayer1.getGamma());
        CNN3LayerNetWeights.setBatchNorm2GammaValues(batchNormLayer2.getGamma());
        CNN3LayerNetWeights.setBatchNorm3GammaValues(batchNormLayer3.getGamma());
        CNN3LayerNetWeights.setBatchNorm1BetaValues(batchNormLayer1.getBeta());
        CNN3LayerNetWeights.setBatchNorm2BetaValues(batchNormLayer2.getBeta());
        CNN3LayerNetWeights.setBatchNorm3BetaValues(batchNormLayer3.getBeta());

        return CNN3LayerNetWeights;
    }

    private Kernel[] initializeKernels(int numOfKernels, int kernalWidth, int kernalHeight, int kernalDepth){

            Kernel[] kernels = new Kernel[numOfKernels];

            for(int i=0;i<numOfKernels;i++){
                kernels[i] = new Kernel(kernalWidth,kernalHeight,kernalDepth);
                kernels[i].InitializeRandomValues();
            }

            return kernels;
        }

}

