package NeuralNetwork.Block;

import NeuralNetwork.ActivationFunctions.ActivationFunction;
import NeuralNetwork.Utils.Dim3Struct;

import java.util.ArrayList;

public final class ConvolutionalBlock extends FeatureBlock<ArrayList<Dim3Struct>> {

    private int numOfKernels;
    private int kernelLength;
    private int kernelWidth;
    private int padding;
    private int stride;



    public ConvolutionalBlock(Dim3Struct.Dims inputNeuronDims, int stride, int padding, int kernelWidth , int kernelLength, int numOfKernels, ActivationFunction func) {
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
            Dim3Struct.Dims dims ;
            dims = new Dim3Struct.Dims((((inputNeuronsDims.getWidth()-kernelWidth-2*padding)/stride) + 1),(((inputNeuronsDims.getLength()-kernelLength-2*padding)/stride) + 1),numOfKernels);
            this.neurons = new Dim3Struct(dims);
        }

        if(weights==null){
           throw new RuntimeException("Weights have not been intialised") ;
        }

        verifyBlock();
    }

    @Override
    public Dim3Struct getOutput() {
        return outputNeurons;
    }


    @Override
    public void updateWeights(WeightUpdateRule rule) {

        for (int kerNum = 0; kerNum < weights.size(); kerNum++) {

            for (int i = 0; i < weights.get(kerNum).getWidth(); i++) {
                for (int j = 0; j < weights.get(kerNum).getLength(); j++) {
                    for (int k = 0; k < weights.get(kerNum).getDepth(); k++) {


                        weights.get(kerNum).getValues()[i][j][k] = rule.calculate(weights.get(kerNum).getValues()[i][j][k] ,weightErrors.get(kerNum).getValues()[i][j][k]);

                    }
                }
            }
        }

    }

    @Override
    protected Dim3Struct blockCalculation(Dim3Struct Input) {

        Dim3Struct PaddedInput = pad(Input);

        int newLength = ((PaddedInput.getLength() - kernelLength + 2*padding)/stride) + 1;
        int newWidth =  ((PaddedInput.getWidth() - kernelWidth + 2*padding)/stride) + 1;


        if(newLength<0 || newWidth<0){
           System.out.print("Here");
        }


        Dim3Struct output = new Dim3Struct(new Dim3Struct.Dims(newLength,newWidth,numOfKernels));

        int outputW =0;
        int outputL = 0;

        for(int kernel=0;kernel<numOfKernels;kernel++){

            outputL =0 ;


            for(int inputL=0;inputL<=PaddedInput.getLength() - kernelLength; inputL = inputL +stride){

                outputW = 0;

                for(int inputW=0;inputW<=PaddedInput.getWidth() - kernelWidth; inputW = inputW + stride){



                    for(int KW =0 ; KW<kernelWidth;KW++){
                        for(int KL =0 ; KL < kernelLength;KL++){
                            for(int KD =0; KD<PaddedInput.getDepth();KD++){
                             output.getValues()[outputW][outputL][kernel] += weights.get(kernel).getValues()[KW][KL][KD] * PaddedInput.getValues()[inputW+KW][inputL+KL][KD];
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
        for(Dim3Struct w:weightErrors){
            w.clear();
        }
    }

    private Dim3Struct pad(Dim3Struct input) {

        Dim3Struct output = new Dim3Struct(input.getWidth()+2*padding, input.getLength()+2*padding,input.getDepth());

        for(int w  =0;w<input.getWidth();w++){
            for(int l  =0;l<input.getLength();l++){
                for(int d =0;d<input.getDepth();d++){
                    output.getValues()[w+padding][l+padding][d] = input.getValues()[w][l][d];
                }
            }
        }


        return output;
    }

    @Override
    protected ArrayList<Dim3Struct> calculateWeightErrors(Dim3Struct neuronErrors,Dim3Struct inputNeurons) {

        int errorsWidth = neuronErrors.getWidth();
        int errorsLength = neuronErrors.getLength();

        Dim3Struct PaddedInput = pad(inputNeurons);

        this.weightErrors = new ArrayList<>();
        Dim3Struct.Dims weightDims = weights.get(0).getDims();
        for(int i=0; i< weights.size(); i++){
            this.weightErrors.add(new Dim3Struct(weightDims));
        }

        for(int kernel=0;kernel<numOfKernels;kernel++){
            for(int kernelW =0; kernelW<kernelWidth;kernelW++){
                for(int kernelL =0; kernelL<kernelLength;kernelL++) {
                    for(int kernelD=0;kernelD< PaddedInput.getDepth();kernelD++)

                    for (int i = 0; i < errorsWidth; i++) {
                        for (int j = 0; j < errorsLength; j++) {
                            weightErrors.get(kernel).getValues()[kernelW][kernelL][kernelD] += neuronErrors.getValue(i,j,kernel)*PaddedInput.getValue(i*stride + kernelW,j*stride+kernelL,kernelD);

                        }
                    }
                }
            }
        }

        return weightErrors;
    }

    @Override
    protected Dim3Struct calculateNeuronErrors(Dim3Struct nextNeuronErrors,Object nextWeights) {

        if(nextWeights instanceof Dim3Struct){
            //neurons errors should be errors od con. As we are connected to a fc
                this.neuronErrors = new Dim3Struct(outputNeurons.getDims());

            for(int neuronCount=0;neuronCount<neuronErrors.getWidth();neuronCount++) {
                for (int inputDeltaCount = 0; inputDeltaCount < nextNeuronErrors.getWidth(); inputDeltaCount++) {
                    neuronErrors.getValues()[neuronCount][0][0] += nextNeuronErrors.getValues()[inputDeltaCount][0][0]*((Dim3Struct)nextWeights).getValues()[inputDeltaCount][neuronCount][0];
                }
            }
        }else if(nextWeights instanceof ArrayList){
            //do normal CB
            this.neuronErrors = new Dim3Struct(neurons.getDims());

            //do it per kernel
            int kernelSize = ((ArrayList<Dim3Struct>) nextWeights).size();
            for(int ker=0;ker<kernelSize;ker++){

                //for each neuron value
                for(int Wne =0; Wne < neuronErrors.getWidth(); Wne++){
                    for(int Lne =0; Lne < neuronErrors.getLength(); Lne++){
                        for(int Dne=0; Dne < neuronErrors.getDepth(); Dne++){

                            for(int WNxtErr=0;WNxtErr<nextNeuronErrors.getWidth();WNxtErr++){
                                for(int LNxtErr=0;LNxtErr<nextNeuronErrors.getLength();LNxtErr++){

                                    neuronErrors.getValues()[Wne][Lne][Dne] += nextNeuronErrors.getValues()[WNxtErr][WNxtErr][ker]*((ArrayList<Dim3Struct>) nextWeights).get(ker).getValues()[Wne - WNxtErr*(stride)][Lne - LNxtErr*stride][Dne];

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
