package NeuralNetwork.Utils;

import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.*;


/**
 * This class represents a collection of data rows (DataSetRow instances) used
 * for training and testing neural network.
 *
 *
 * http://openforecast.sourceforge.net/docs/net/sourceforge/openforecast/DataSet.html
 */
public class DataSet implements Serializable { // implements

    //hold data set samples
    @Getter @Setter
    ArrayList<DataSetRow> samples;

    boolean forSupervised;
    @Getter @Setter
    int sampleInputSize;
    @Getter @Setter
    int sampleExpectedOutputSize;


    public DataSet(){
        samples = new ArrayList<>();
    }

    //is for supervised?
    public boolean isForSupervised(){
        return forSupervised;
    }

    //

    //count
    public int size(){
        return samples.size();
    }



    //add
    public void add(double[] input){
        if(input == null){
            throw new IllegalArgumentException("The parameter value is null");
        }else if(input.length != sampleInputSize){
            throw new IllegalArgumentException("Array length is not equal to sampleInputSize");
        }else if(forSupervised == true){
            throw new IllegalArgumentException("Supervised must have expected output ");
        }

        samples.add(new DataSetRow(input));
    }

    public void add(double[] input,double[] output){
        if(input == null || output == null){
            throw new IllegalArgumentException("The parameter values must not be null");
        }else if(input.length != sampleInputSize){
            throw new IllegalArgumentException("Array length is not equal to sampleInputSize " + sampleInputSize);
        }else if(output.length != sampleExpectedOutputSize){
            throw new IllegalArgumentException("Array length is not equal to the expected output size " + sampleExpectedOutputSize);
        }

        samples.add(new DataSetRow(input,output));
    }

    public void add(DataSetRow sample){
        samples.add(sample);
    }

    //remove at
    public DataSetRow removeSampleAt(int position){
        return samples.remove(position);
    }

    //clear
    public void clear(){
        samples.clear();
    }

    //is empty
   public boolean isEmpty(){
        if(samples.size()==0){
            return true;
        }else{
            return false;
        }
   }

    //TODO make possible to split into a number of datasets

    //TODO save data to a file

    //TODO create from file

    //Iterator
    public Iterator<DataSetRow> iterator() {
        return this.samples.iterator();
    }


    //TODO load in data set samples, csv

    //TODO toString
}