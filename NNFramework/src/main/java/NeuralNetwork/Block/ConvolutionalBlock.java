package NeuralNetwork.Block;

import NeuralNetwork.Block.ActivationFunctions.ActivationFunction;

import java.util.ArrayList;

public class ConvolutionalBlock extends FeatureBlock<ArrayList<Dim3Struct>> {

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

    public ConvolutionalBlock(ArrayList<Dim3Struct> weights, Dim3Struct.Dims inputNeuronDims, Dim3Struct.Dims blockNeuronDims, int stride, int padding, int kernelWidth , int kernelLength, int numOfKernels, ActivationFunction func) {
        super(blockNeuronDims,inputNeuronDims,weights,func);

        this.padding = padding;
        this.kernelLength = kernelLength;
        this.kernelWidth = kernelWidth;
        this.numOfKernels = numOfKernels;
        this.stride = stride;

    }

    @Override
    protected void generateBlockWeights(Dim3Struct.Dims inputDims) {

        for(int i=0;i<numOfKernels;i++){
            weights.add(new Dim3Struct(kernelWidth,kernelLength,inputDims.getDepth()));
        }

    }

    @Override
    void generateBlockWeights() {

    }

    @Override
    public void updateWeights(WeightUpdateRule rule) {

    }

    @Override
    protected Dim3Struct blockCalculation(Dim3Struct Input) {

        Dim3Struct PaddedInput = pad(Input);

        int newLength = ((PaddedInput.getLength() - kernelLength + 2*padding)/stride) + 1;
        int newWidth =  ((PaddedInput.getWidth() - kernelWidth + 2*padding)/stride) + 1;

        System.out.println(newWidth + " " + newLength);

        Dim3Struct output = new Dim3Struct(new Dim3Struct.Dims(newLength,newWidth,PaddedInput.getDepth()));

        int outputW =0;
        int outputL = 0;

        for(int kernel=0;kernel<numOfKernels;kernel++){

            outputL =0 ;


            for(int inputL=0;inputL<=PaddedInput.getLength() - kernelLength; inputL = inputL +stride){

                outputW = 0;

                for(int inputW=0;inputW<=PaddedInput.getWidth() - kernelWidth; inputW = inputW + stride){


                    System.out.println(outputW + " " + outputL);

                    for(int KW =0 ; KW<kernelWidth;KW++){
                        for(int KL =0 ; KL < kernelLength;KL++){
                            for(int KD =0; KD<PaddedInput.getDepth();KD++){

                             //CH output.getValues()[outputW][outputL][kernel] += weights.getValues()[KW][KL][KD] * PaddedInput.getValues()[inputW+KW][inputL+KL][KD];
                            }
                        }
                    }
                    outputW++;
                }
                outputL++;
            }
        }

        System.out.println(output);
        return output;
    }

    @Override
    protected void clearWeightErrors() {

    }

    private Dim3Struct pad(Dim3Struct input) {
        System.out.println(input);
        Dim3Struct output = new Dim3Struct(input.getWidth()+2*padding, input.getLength()+2*padding,input.getDepth());

        for(int w  =0;w<input.getWidth();w++){
            for(int l  =0;l<input.getLength();l++){
                for(int d =0;d<input.getDepth();d++){
                    output.getValues()[w+padding][l+padding][d] = input.getValues()[w][l][d];
                }
            }
        }

        System.out.println(output);
        return output;
    }

    @Override
    protected ArrayList<Dim3Struct> calculateWeightErrors(Dim3Struct neuronErrors,Dim3Struct inputNeurons) {

        int deltasWidth = neuronErrors.getWidth();
        int deltasLength = neuronErrors.getLength();

        Dim3Struct PaddedInput = pad(inputNeurons);


        for(int kernel=0;kernel<numOfKernels;kernel++){
            for(int kernelW =0; kernelW<kernelWidth;kernelW++){
                for(int kernelL =0; kernelL<kernelLength;kernelL++) {

                    for (int i = 0; i < deltasWidth; i++) {
                        for (int j = 0; j < deltasLength; j++) {
                            //CH weights.getValues()[kernelW][kernelL][kernel] = inputDeltas.getValue(i,j,kernel)*PaddedInput.getValue(i*stride + kernelW,j*stride+kernelL,kernel);
                        }
                    }
                }
            }
        }

        return null;
    }

    @Override
    protected Dim3Struct calculateNeuronErrors(Dim3Struct inputDeltas,Object nextWeights) {

        if(nextWeights instanceof Dim3Struct){
            //neurons errors should be errors od con. As we are connected to a fc layer
        }else{
            //do normal CB
        }

        return null;
    }


}
