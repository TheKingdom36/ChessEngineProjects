package NeuralNetwork.Block;

import NeuralNetwork.Utils.Dim3Struct;

import static org.junit.Assert.assertEquals;

public class ConvolutionalBlockTestUtilities {

    public static void assertEqualsBetweenDimStructs(Dim3Struct actual,Dim3Struct expected){
        if(actual.getWidth() != expected.getWidth() || actual.getLength() != expected.getLength()|| actual.getDepth() != expected.getDepth()){
            throw new RuntimeException("Dimensions should be equal between expected and actual");
        }

        for (int i = 0; i < actual.getWidth(); i++) {
            for (int j = 0; j < actual.getLength(); j++) {
                for (int k = 0; k < actual.getDepth(); k++) {
                    assertEquals(actual.getValues()[i][j][0] ,expected.getValues()[i][j][0] ,0.0001);
                }
            }
        }


    }
}
