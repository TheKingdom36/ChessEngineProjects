package NeuralNetwork.Block;

import NeuralNetwork.Block.ActivationFunctions.ActivationFunction;

import java.awt.image.Kernel;
import java.util.ArrayList;

public final class ConvolutionalBlock extends FeatureBlock<ArrayList<Dim3Struct>> {

    private int numOfKernels;
    private int kernelLength;
    private int kernelWidth;
    private int padding;
    private int stride;

    public boolean isConnectedToFC() {
        return connectedToFC;
    }

    public void setConnectedToFC(boolean connectedToFC) {
        this.connectedToFC = connectedToFC;
    }

    private boolean connectedToFC=false;

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

        if(preNeuronOperations.size() >0){
            preNeuronOperations.get(0).doOp(new Dim3Struct(this.inputNeuronsDims));
            for (int i=1 ; i<preNeuronOperations.size();i++){
                preNeuronOperations.get(i).doOp(preNeuronOperations.get(i-1).getOutputNeurons());
            }
        }

        if(neurons ==null ){
            Dim3Struct.Dims dims ;

            if(preNeuronOperations.size()>0){
                dims = new Dim3Struct.Dims((((preNeuronOperations.get(preNeuronOperations.size()-1).getOutputNeurons().getWidth()-kernelWidth-2*padding)/stride) + 1),(((preNeuronOperations.get(preNeuronOperations.size()-1).getOutputNeurons().getLength()-kernelLength-2*padding)/stride) + 1),numOfKernels);

            }else{
                dims = new Dim3Struct.Dims((((inputNeuronsDims.getWidth()-kernelWidth-2*padding)/stride) + 1),(((inputNeuronsDims.getLength()-kernelLength-2*padding)/stride) + 1),numOfKernels);

            }

            this.neurons = new Dim3Struct(dims);
        }

        if(weights==null){
           throw new RuntimeException("Weights have not been intialised") ;
        }



        VerifyBlock();
    }


    @Override
    public void updateWeights(WeightUpdateRule rule) {
//TODO

    }

    @Override
    protected Dim3Struct blockCalculation(Dim3Struct Input) {

        Dim3Struct PaddedInput = pad(Input);

        int newLength = ((PaddedInput.getLength() - kernelLength + 2*padding)/stride) + 1;
        int newWidth =  ((PaddedInput.getWidth() - kernelWidth + 2*padding)/stride) + 1;



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
                                System.out.println(output.getValues()[outputW][outputL][kernel]);
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
    protected void clearWeightErrors() {
//TODO
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

                    for (int i = 0; i < errorsWidth; i++) {
                        for (int j = 0; j < errorsLength; j++) {
                            weightErrors.get(kernel).getValues()[kernelW][kernelL][kernel] = neuronErrors.getValue(i,j,kernel)*PaddedInput.getValue(i*stride + kernelW,j*stride+kernelL,kernel);
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
            //neurons errors should be errors od con. As we are connected to a fc layer
            neuronErrors = nextNeuronErrors.Copy();
        }else if(nextWeights instanceof ArrayList){
            //do normal CB

            this.neuronErrors = new Dim3Struct(neurons.getDims());

            //do it per kernel
            for(int ker=0;ker<((ArrayList<Dim3Struct>) nextWeights).size();ker++){

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
