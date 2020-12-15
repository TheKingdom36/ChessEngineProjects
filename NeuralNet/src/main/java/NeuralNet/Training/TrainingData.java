package NeuralNet.Training;


import MontoCarlo.TrainingSample;
import NeuralNet.Models.NNOutput;
import lombok.Getter;

import java.util.List;

/**
 * NeuralNet.Training data contains training elements used for training a neural network
 */
public class TrainingData {
    @Getter
    TrainingElement[] trainingElements;

    public TrainingData(List<TrainingSample> trainingSampleList , List<NNOutput> nnOutputs){


        trainingElements = new TrainingElement[trainingSampleList.size()];

        for (int i = 0; i < trainingSampleList.size(); i++) {
            trainingElements[i] = new TrainingElement(nnOutputs.get(i).getWin_score(), trainingSampleList.get(i).getValue(),nnOutputs.get(i).getProbabilities(),trainingSampleList.get(i).getPolicy() );
        }
    }

}
