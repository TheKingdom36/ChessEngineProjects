package Common;

import java.util.Random;

public class Plane {
    private double[][] values;
    private int width;
    private int height;
    private int paddingSize;

    static Random rand = new Random();

    public Plane(int width, int height){
        values = new double[width][height];
        this.width = width;
        this.height = height;
    }

    public Plane(int width, int height, boolean randomValues, int min, int max){
        values = new double[width][height];
        this.width = width;
        this.height = height;

        if(randomValues ==true) {

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    int in = rand.nextInt(max - min) + min;

                    values[i][j] = in;
                }
            }
        }


    }

    public double[][] getValues(){
        return values;
    }

    public void       setValues(double[][] values){
        this.values = values;
    }

    public int        getWidth() {
        return width;
    }

    public int        getHeight() {
        return height;
    }

    public void       setValue(int width, int height ,double newValue){
        values[width][height]= newValue;
    }

    public void       addPadding(int paddingSize){
        this.paddingSize = paddingSize;

        double[][] newValues = new double[width+paddingSize*2][height+paddingSize*2];

        int countx=0;
        int county = 0;
        for(int i=paddingSize;i<(newValues.length-paddingSize);i++){
            county=0;
            for(int j=paddingSize;j<newValues[0].length-paddingSize;j++){
                newValues[i][j] = values[countx][county];
                county++;
            }
            countx++;
        }

        width = width+paddingSize*2;
        height = height+paddingSize*2;

        values = newValues;


    }

    public void Print(){
        for(int i=0;i<width;i++){
            System.out.println();
            for(int j=0;j<height;j++){
                System.out.print(values[i][j]+" ");
            }
        }
    }

    public static Plane ConvertPlanesToPlane(Plane[] Planes, int newplaneWidth, int newplaneHeight){
        Plane plane = new Plane(newplaneWidth,newplaneHeight);

        int pos =0;
        int widthPos=0;
        int heightPos=0;
        for(int i = 0; i< Planes.length; i++){
            for(int j = 0; j< Planes[0].getWidth(); j++){
                for(int k = 0; k< Planes[0].getHeight(); k++){
                    if(pos>newplaneWidth*newplaneHeight){
                        return plane;
                    }
                    if(widthPos==newplaneWidth){
                        heightPos++;
                        widthPos=0;

                        if(heightPos>=newplaneHeight){
                            return plane;
                        }
                    }
                    plane.setValue(widthPos,heightPos, Planes[i].getValues()[j][k]);
                    pos++;
                    widthPos++;
                }
            }


        }
        return plane;
    }
}
