package NeuralNetwork.Block.Operations;

import NeuralNetwork.Block.Dim3Struct;
import NeuralNetwork.Block.functions.Softmax;

public class SoftmaxOp extends BlockOperation{

    public SoftmaxOp(){

    }



    @Override
    public Dim3Struct doOp(Dim3Struct input) {
        outputNeurons = input.Copy();
        double[] softmaxResult = Softmax.calculate(input.toArray());

        for(int i=0 ; i<softmaxResult.length;i++){
            outputNeurons.getValues()[i][0][0] = softmaxResult[i];
        }

        return outputNeurons;
    }


    @Override
    public Dim3Struct calculateDeltas(Dim3Struct deltas) {


        Dim3Struct newDeltas = new Dim3Struct(outputNeurons.getDims());

        double[] neuronsArray = outputNeurons.toArray();
        for(int i=0; i<newDeltas.getWidth();i++){
            double value = 0;
            for(int j=0; j<outputNeurons.getWidth();j++){
                double derivative = Softmax.calculateDerivative(neuronsArray,i,j);
                double delta = deltas.getValues()[j][0][0];
                value += (derivative*delta);
            }

            newDeltas.getValues()[i][0][0] = value;
        }

        return newDeltas;
    }
}
