package NeuralNet.Models;

import NeuralNet.NNExamples.CNN3LayerNetWeights;
import NeuralNet.Interfaces.INeuralNetwork;
import NeuralNet.Training.TrainingData;

public class TrainNetwork {

    public TrainNetwork(){

    }



    //Not yet implemented
    public static CNN3LayerNetWeights Train(TrainingData trainingData, INeuralNetwork neuralNetwork){

        neuralNetwork.getInputLayer().calculateErrors();


        CNN3LayerNetWeights newCNN3LayerNetWeights = neuralNetwork.updateWeights();


        return newCNN3LayerNetWeights;

    }
}
