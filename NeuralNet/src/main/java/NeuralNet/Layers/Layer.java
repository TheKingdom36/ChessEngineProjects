package NeuralNet.Layers;


import NeuralNet.Models.Kernel;
import Common.Plane;

import java.util.Arrays;

public abstract class Layer {
    protected Layer nextLayer;
    protected Layer previousLayer;

    // a double array of planes first parameter is the batchSize second parameter is the plane number
    protected Plane[][] outputPlanes;
    protected static int batchSize;

    //batchSize, error plane, error plane width , error plane height
    protected double[][][][] errors;

    public abstract void calculateOutputPlanes();
    public abstract void calculateErrors();
    public abstract Kernel[] getKernels();

    public static int getBatchSize() {
        return batchSize;
    }
    public static void setBatchSize(int batchSize) {
        Layer.batchSize = batchSize;
    }

    public double[][][][] getErrors() {
        return errors;
    }


    public Layer getNextLayer(){
        return nextLayer;
    }
    public void setNextLayer(Layer layer){
        this.nextLayer = layer;
    }
    Layer getPreviousLayer(){
        return previousLayer;
    }
    public void setPreviousLayer(Layer layer){
        previousLayer = layer;
    }

    public Plane[][] getOutputPlanes(){
        return outputPlanes;
    }
    public void setOutputPlanes(Plane[][] Planes){
        this.outputPlanes = Planes;
    }

    public double[][] extendDoubleArray(double[][] doubleArray, int extendByWidth, int extendByHeight){

        int m = doubleArray.length;
        int n = doubleArray[0].length;


        final double[][] extendMatrix = new double[m + 2 * (extendByWidth)][n + 2 * (extendByHeight)];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++)
                extendMatrix[i + extendByWidth - 1][j + extendByHeight - 1] = doubleArray[i][j];
        }

        return extendMatrix;
    }


    @Override
    public String toString() {
        return "Layer{" +
                ", nextLayer=" + nextLayer +
                ", previousLayer=" + previousLayer +
                ", outputplanes=" + Arrays.toString(outputPlanes) +
                ", errors=" + Arrays.toString(errors) +
                '}';
    }
}
