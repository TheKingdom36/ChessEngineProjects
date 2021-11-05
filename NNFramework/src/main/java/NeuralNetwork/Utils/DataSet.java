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
public class DataSet<DataSetRow extends IDataSetRow> implements Serializable { // implements

    //hold data set samples
    @Getter @Setter
    transient ArrayList<DataSetRow> samples;


    boolean forSupervised;
    @Getter @Setter
    int sampleInputSize;



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

    public DataSetRow getSample(int position){
        return (DataSetRow) (samples.get(position));
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