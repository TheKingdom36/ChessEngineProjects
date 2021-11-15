package NeuralNetwork.Block;

import NeuralNetwork.ActivationFunctions.ActivationFunction;
import NeuralNetwork.Utils.Dim3Struct;
import NeuralNetwork.Utils.Dim4Struct;

import java.util.ArrayList;

public final class ConvolutionalBlock extends FeatureBlock {

    private int numOfKernels;
    private int kernelLength;
    private int kernelWidth;
    private int padding;
    private int stride;



    public ConvolutionalBlock(Dim4Struct.Dims inputNeuronDims, int stride, int padding, int kernelWidth , int kernelLength, int numOfKernels, ActivationFunction func) {
        super(inputNeuronDims,func);

        this.padding = padding;
        this.kernelLength = kernelLength;
        this.kernelWidth = kernelWidth;
        this.numOfKernels = numOfKernels;
        this.stride = stride;

    }


    @Override
    public void setUp(){


        if(neurons ==null ){
            Dim4Struct.Dims dims ;
            dims = new Dim4Struct.Dims(1,numOfKernels,(((inputNeuronsDims.getWidth()-kernelWidth-2*padding)/stride) + 1),(((inputNeuronsDims.getLength()-kernelLength-2*padding)/stride) + 1));
            this.neurons = new Dim4Struct(dims);
        }

        if(weights==null){
            throw new RuntimeException("Weights have not been initialised") ;
        }

        verifyBlock();
    }



    @Override
    public void updateWeights(WeightUpdateRule rule) {

        for (int kerNum = 0; kerNum < weights.getNum(); kerNum++) {

            for (int i = 0; i < weights.getWidth(); i++) {
                for (int j = 0; j < weights.getLength(); j++) {
                    for (int k = 0; k < weights.getChannels(); k++) {


                        weights.getValues()[kerNum][k][i][j] = rule.calculate(weights.getValues()[kerNum][k][i][j] ,weightErrors.getValues()[kerNum][k][i][j]);

                    }
                }
            }
        }

    }

    @Override
    protected Dim4Struct blockCalculation(Dim4Struct Input) {

        Dim4Struct PaddedInput = pad(Input,padding);

        int newLength = ((PaddedInput.getLength() - kernelLength + 2*padding)/stride) + 1;
        int newWidth =  ((PaddedInput.getWidth() - kernelWidth + 2*padding)/stride) + 1;




        Dim4Struct output = new Dim4Struct(new Dim4Struct.Dims(1,numOfKernels,newLength,newWidth));

        int outputW =0;
        int outputL = 0;

        for(int kernel=0;kernel<numOfKernels;kernel++){

            outputL =0 ;


            for(int inputL=0;inputL<=PaddedInput.getLength() - kernelLength; inputL = inputL +stride){

                outputW = 0;

                for(int inputW=0;inputW<=PaddedInput.getWidth() - kernelWidth; inputW = inputW + stride){



                    for(int KW =0 ; KW<kernelWidth;KW++){
                        for(int KL =0 ; KL < kernelLength;KL++){
                            for(int KD =0; KD<PaddedInput.getChannels();KD++){


                             output.getValues()[0][kernel][outputW][outputL]+= weights.getValues()[kernel][KD][KW][KL] * PaddedInput.getValues()[0][KD][inputW+KW][inputL+KL];
                            }
                        }
                    }
                    outputW++;
                }
                outputL++;
            }
        }


        return output;
    }

    @Override
    public void clearWeightErrors() {
        weightErrors.clear();
    }

    private Dim4Struct pad(Dim4Struct input,int padding) {

        Dim4Struct output = new Dim4Struct(1,input.getChannels(),input.getWidth()+2*padding, input.getLength()+2*padding);

        for(int w  =0;w<input.getWidth();w++){
            for(int l  =0;l<input.getLength();l++){
                for(int d =0;d<input.getChannels();d++){
                    output.getValues()[0][d][w+padding][l+padding] = input.getValues()[0][d][w][l];
                }
            }
        }


        return output;
    }

    @Override
    protected Dim4Struct calculateWeightErrors(Dim4Struct neuronErrors,Dim4Struct inputNeurons) {

        int errorsWidth = neuronErrors.getWidth();
        int errorsLength = neuronErrors.getLength();

        Dim4Struct PaddedInput = pad(inputNeurons,padding);

        this.weightErrors = new Dim4Struct(weights.getDims());


        for(int kernel=0;kernel<numOfKernels;kernel++){
            for(int kernelW =0; kernelW<kernelWidth;kernelW++){
                for(int kernelL =0; kernelL<kernelLength;kernelL++) {
                    for(int kernelD=0;kernelD< PaddedInput.getChannels();kernelD++)

                    for (int i = 0; i < errorsWidth; i++) {
                        for (int j = 0; j < errorsLength; j++) {
                            weightErrors.getValues()[kernel][kernelD][kernelW][kernelL]+= neuronErrors.getValue(0, kernel,i,j)*PaddedInput.getValue(0,kernelD,i*stride + kernelW,j*stride+kernelL);

                        }
                    }
                }
            }
        }

        return weightErrors;
    }

    @Override
    protected Dim4Struct calculateNeuronErrors(Dim4Struct nextNeuronErrors,Dim4Struct nextWeights) {

        if((nextWeights).getNum()==1 ){
            //neurons errors should be errors of con. As we are connected to a fc
                this.neuronErrors = new Dim4Struct(outputNeurons.getDims());

            for(int neuronCount=0;neuronCount<neuronErrors.getWidth();neuronCount++) {
                for (int inputDeltaCount = 0; inputDeltaCount < nextNeuronErrors.getWidth(); inputDeltaCount++) {
                    neuronErrors.getValues()[0][0][neuronCount][0] += nextNeuronErrors.getValues()[0][0][inputDeltaCount][0]*nextWeights.getValues()[0][0][inputDeltaCount][neuronCount];
                }
            }
        }else{
            //do normal CB
            this.neuronErrors = new Dim4Struct(neurons.getDims());

            Dim4Struct nextNeuronErrorsPadded = pad(nextNeuronErrors,nextWeights.getWidth()/2+1);

            //do it per kernel
            int kernelSize = (nextWeights).getNum();
            for(int ker=0;ker<kernelSize;ker++){

                //for each neuron value
                for(int Wne =0; Wne < neuronErrors.getWidth(); Wne++){
                    for(int Lne =0; Lne < neuronErrors.getLength(); Lne++){
                        for(int Dne=0; Dne < neuronErrors.getChannels(); Dne++){

                                    for (int weighti = -nextWeights.getWidth() / 2; weighti <= nextWeights.getWidth() / 2; weighti++) {
                                        for (int weightj = -nextWeights.getLength() / 2; weightj <= nextWeights.getLength() / 2; weightj++) {

                                            neuronErrors.getValues()[0][Dne][Wne][Lne] += nextNeuronErrorsPadded.getValues()[0][ker][Wne + nextWeights.getWidth() / 2 + weighti][Lne + nextWeights.getLength() / 2 + weightj]*nextWeights.getValues()[ker][Dne][nextWeights.getWidth() / 2 - weighti][nextWeights.getLength() / 2 - weightj];

                                        }
                                    }

                        }
                    }
                }

        }
        }

        return neuronErrors;
    }




}
