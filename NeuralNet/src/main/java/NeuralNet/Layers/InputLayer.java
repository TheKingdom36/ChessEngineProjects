package NeuralNet.Layers;


import NeuralNet.Models.Kernel;
import Common.Plane;

public class InputLayer extends Layer {



    public InputLayer(Plane[][] Planes){
        this.outputPlanes = Planes;

    }

    public InputLayer(Plane plane){
        this.outputPlanes=new Plane[1][1];
        this.outputPlanes[1][1] = plane;
    }


    @Override
    public Kernel[] getKernels() {
        return new Kernel[0];
    }

    @Override
    public void calculateOutputPlanes() {
        //Does nothing no operation required on input plane

    }

    public void calculateErrors(){
        this.nextLayer.calculateErrors();
    }

}
