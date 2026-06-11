package org.kp.ai.makemore;

import junit.framework.TestCase;
import org.kp.ai.makemore.bigrams.BigramsNeuralNet;

import java.util.Arrays;

public class MakeMoreNeuralNetTest extends TestCase {

    public void testNeuralNet1() {
        int[] x = {0, 5, 13, 13, 11};
        int[] y = {5, 13, 13, 1, 0};
        BigramsNeuralNet bigramsNeuralNet = new BigramsNeuralNet(x, y);
        float[][] xenc = bigramsNeuralNet.getOneHotEncoding(x, 27);
        System.out.println(Arrays.deepToString(xenc));
    }
}
