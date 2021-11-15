package NeuralNetwork.Utils;

import NeuralNetwork.Operations.Operation;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;

public class Dim4Struct {

    private double[][][][] values;
    @Getter
    @Setter
    private int length;
    @Getter
    @Setter
    private int width;
    @Getter
    @Setter
    private int channels;
    @Getter
    @Setter
    private int num;


    public Dim4Struct(int num, int channels, int width ,int length) {
        this.length = length;
        this.width = width;
        this.channels = channels;
        this.num = num;
        values = new double[num][channels][width][length];
    }

    public Dim4Struct(Dims dims) {
        this.length = dims.getLength();
        this.width = dims.getWidth();
        this.channels = dims.getChannel();
        this.num = dims.getNum();
        values = new double[num][channels][width][length];
    }


    public double[][][][] getValues() {
        return values;
    }

    public void setValues(double[][][][] values) {
        this.values = values;
    }

    public void perValueOperation(Operation<Double> op) {
        for (int t = 0; t < num; t++) {
            for (int k = 0; k < channels; k++) {
                for (int i = 0; i < width; i++) {
                    for (int j = 0; j < length; j++) {

                        values[t][k][i][j] = op.doOp(values[t][k][i][j]);

                    }
                }
            }
        }
    }

        public double getValue( int num, int channel, int width, int length){
            return values[num][channel][width][length];
        }

        public boolean CompareDimensions (Dim4Struct otherStruct){
            if (width == otherStruct.getWidth() && length == otherStruct.getLength() && channels == otherStruct.getChannels()) {
                return true;
            }

            return false;

        }

        public Dim4Struct Copy () {
            Dim4Struct copy = new Dim4Struct(num,channels,width ,length);

            for (int t = 0; t < num; t++) {
            for (int k = 0; k < channels; k++) {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < length; j++) {

                        copy.getValues()[t][k][i][j] = values[t][k][i][j];
                    }
                }
            }
        }
            return copy;
    }

        public void RandomlyInitialiseValues ( double min, double max) {
            Random rand = new Random();
            for (int t = 0; t < num; t++) {
                for (int k = 0; k < channels; k++) {
                    for (int i = 0; i < width; i++) {
                        for (int j = 0; j < length; j++) {

                            values[t][k][i][j] = ((max - min) * rand.nextDouble() + min);
                        }
                    }
                }
            }
        }

        public boolean equals (Object struct){
            Dim4Struct dim3Struct = (Dim4Struct) struct;

            if (dim3Struct.getWidth() != this.width || dim3Struct.getLength() != this.length || dim3Struct.getChannels() != this.channels) {
                return false;
            }

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < length; j++) {
                    for (int k = 0; k < channels; k++) {
                        if (values[i][j][k] != dim3Struct.getValues()[i][j][k]) {
                            return false;
                        }
                    }
                }
            }

            return true;

        }

        public void populate ( double[] array) {
            if (array.length != width * length * channels * num) {
                throw new IllegalArgumentException("The number of values must be equal to " + values.length);
            }
            int count = 0;
            for (int t = 0; t < num; t++) {
                for (int k = 0; k < channels; k++) {
                    for (int i = 0; i < width; i++) {
                        for (int j = 0; j < length; j++) {
                            values[t][k][i][j] = array[count];
                            count++;
                        }
                    }
                }
            }
        }

        public double[] toArray () {
                double[] array = new double[width * length * channels * num];
                int count = 0;
                for (int t = 0; t < num; t++) {

                    for (int k = 0; k < channels; k++) {
                        for (int i = 0; i < width; i++) {
                            for (int j = 0; j < length; j++) {
                                array[count] = values[t][k][i][j];
                                count++;
                            }
                        }
                    }


                }
                return array;
            }

        public int totalNumOfValues () {
            return width * length * channels * num;
        }

        @Override
        public String toString () {
            StringBuilder builder = new StringBuilder();
            for (int t = 0; t < num; t++) {

            for (int k = 0; k < channels; k++) {
                builder.append("channel " + k);
                builder.append("\n");
                for (int i = 0; i < width; i++) {
                    for (int j = 0; j < length; j++) {
                        builder.append(values[t][k][i][j] + " ");
                    }
                    builder.append("\n");
                }
            }

        }
            return builder.toString();
    }

        public Dims getDims () {
            return new Dims(num,channels,width ,length);
        }

        public void clear () {

            values = new double[num][channels][width][length];
    }

    public static class Dims {
        @Getter
        @Setter
        private int length;
        @Getter
        @Setter
        private int width;
        @Getter
        @Setter
        private int channel;
        @Getter
        @Setter
        private int num;

        public Dims(int num ,int channel ,int width ,int length) {
            this.width = width;
            this.length = length;
            this.channel = channel;
            this.num = num;
        }

        @Override
        public String toString() {
            return "Dims{" +
                    ", num=" + num +
                    ", channel=" + channel +
                    "width=" + width +
                    ", length=" + length +

                    '}';
        }
    }
    }
