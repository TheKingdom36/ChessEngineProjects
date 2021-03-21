package NeuralNetwork.Block;

import NeuralNetwork.Block.Operations.Operation;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;

public class Dim3Struct {

    private double[][][] values;
    @Getter @Setter
    private int length;
    @Getter @Setter
    private int width;
    @Getter @Setter
    private int depth;


    public Dim3Struct(int width, int length , int depth){
        this.length = length;
        this.width = width;
        this.depth = depth;

        values = new double[width][length][depth];
    }

    public Dim3Struct(Dims dims){
        this.length = dims.getLength();
        this.width = dims.getWidth();
        this.depth = dims.getDepth();

        values = new double[width][length][depth];
    }



    public double[][][] getValues() { return values; }

    public void setValues(double[][][] values) { this.values = values; }

    public void perValueOperation(Operation<Double> op){
        for(int i=0; i< width ; i++){
            for(int j=0; j< length ; j++){
                for(int k=0; k< depth ; k++){
                    values[i][j][k]= op.doOp(values[i][j][k]);

                }
            }
        }
    }

    public double getValue(int width,int length,int depth){
        return values[width][length][depth];
    }

    public boolean CompareDimensions(Dim3Struct otherStruct){
        if(width == otherStruct.getWidth() && length == otherStruct.getLength() && depth == otherStruct.getDepth()){
            return true;
        }

        return false;

    }

    public Dim3Struct Copy() {
        Dim3Struct copy = new Dim3Struct(width,length,depth);

        for(int i=0; i< width ; i++){
            for(int j=0; j< length ; j++){
                for(int k=0; k< depth ; k++){
                    copy.getValues()[i][j][k]= values[i][j][k];
                }
            }
        }

        return copy;
    }

    public void RandomlyInitialiseValues(double min , double max){
        Random rand = new Random();

        for(int i=0; i< width ; i++){
            for(int j=0; j< length ; j++){
                for(int k=0; k< depth ; k++){
                    values[i][j][k]= ((max - min)* rand.nextDouble() + min);
                }
            }
        }
    }


    public boolean equals(Object struct){
        Dim3Struct dim3Struct = (Dim3Struct)struct;

        if(dim3Struct.getWidth() != this.width ||dim3Struct.getLength() != this.length||dim3Struct.getDepth() != this.depth ){
            return false;
        }

        for(int i=0; i< width ; i++){
            for(int j=0; j< length ; j++){
                for(int k=0; k< depth ; k++){
                    if(values[i][j][k]!= dim3Struct.getValues()[i][j][k]){
                        return false;
                    }
                }
            }
        }

        return true;

    }

    public void populate(double[] array){
        if(array.length != width*length*depth){
            throw new IllegalArgumentException("The number of values must be equal to " + values.length );
        }
        int count = 0;
        for(int k=0; k< depth ; k++){
            for(int i=0; i< width ; i++){
                for(int j=0; j< length ; j++){
                    values[i][j][k] = array[count];
                    count++;
                }
            }
        }
    }

    public double[] toArray(){
        double[] array = new double[width*length*depth];
        int count=0;
        for(int k=0; k< depth ; k++){
            for(int i=0; i< width ; i++){
                for(int j=0; j< length ; j++){
                    array[count] = values[i][j][k];
                    count++;
                }
            }
        }

        return array;
    }

    public int totalNumOfValues(){
        return width*length*depth;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();

        for(int k=0; k< depth ; k++){
            builder.append("Depth " + k);
            builder.append("\n");
            for(int i=0; i< width ; i++){
            for(int j=0; j< length ; j++){
                    builder.append(values[i][j][k] + " ");
                }
            builder.append("\n");
            }
        }
        return builder.toString();
    }

    public Dims getDims(){
        return new Dims(width,length,depth);
    }

    public void clear() {
        for(int k=0; k< depth ; k++){
            for(int i=0; i< width ; i++){
                for(int j=0; j< length ; j++){
                   values[i][j][k]=0;
                }
            }
        }
    }

    public static class Dims{
        @Getter @Setter
        private int length;
        @Getter @Setter
        private int width;
        @Getter @Setter
        private int depth;

        public Dims(int width,int length,int depth){
            this.width = width;
            this.length = length;
            this.depth = depth;
        }

        @Override
        public String toString() {
            return "Dims{" +
                    "width=" + width +
                    ", length=" + length +
                    ", depth=" + depth +
                    '}';
        }
    }
}
