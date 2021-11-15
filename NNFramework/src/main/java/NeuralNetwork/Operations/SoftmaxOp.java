package NeuralNetwork.Operations;

import NeuralNetwork.Utils.Dim3Struct;
import NeuralNetwork.Functions.Softmax;
import NeuralNetwork.Utils.Dim4Struct;

public class SoftmaxOp extends BlockOperation{

    Dim4Struct outputNeurons;

    public SoftmaxOp(){

    }



    @Override
    public Dim4Struct doOp(Dim4Struct input) {
        outputNeurons = input.Copy();
        double[] softmaxResult = Softmax.calculate(input.toArray());

        for(int i=0 ; i<softmaxResult.length;i++){
            outputNeurons.getValues()[0][0][i][0] = softmaxResult[i];
        }

        return outputNeurons;
    }


    @Override
    public Dim4Struct calculateDeltas(Dim4Struct deltas) {


        Dim4Struct newDeltas = new Dim4Struct(outputNeurons.getDims());

        double[] neuronsArray = outputNeurons.toArray();
        for(int i=0; i<newDeltas.getWidth();i++){
            double value = 0;
            for(int j=0; j<outputNeurons.getWidth();j++){
                double derivative = Softmax.calculateDerivative(neuronsArray,i,j);
                double delta = deltas.getValues()[0][0][j][0];
                value += (derivative*delta);
            }

            newDeltas.getValues()[0][0][i][0] = value;
        }

        return newDeltas;
    }
}
