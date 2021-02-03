import Common.Plane;
import NeuralNet.Layers.InputLayer;
import NeuralNet.NNExamples.OneLayerFFNet;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class NNTest {
    @Test
    public void FeedForwardNeuralNetworkForwardPassWithReluTest() {
        Plane inputPlane = new Plane(3,3);


        for(int i=0;i<inputPlane.getWidth();i++){
            for(int j=0;j<inputPlane.getHeight();j++){
                inputPlane.setValue(i,j,1);
            }
        }

       OneLayerFFNet ffNet = new OneLayerFFNet(3);



        ffNet.configuration();

        Plane plane = ffNet.evaluate(inputPlane);

        plane.Print();







        // assert statements
        assertEquals(0, tester.multiply(0, 0), "0 x 0 must be 0");
    }
}
