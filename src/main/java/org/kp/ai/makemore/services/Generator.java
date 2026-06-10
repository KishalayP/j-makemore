package org.kp.ai.makemore.services;

import java.util.Random;

public class Generator {

    public Random random;

    public Generator() {
        this.random = new Random();
    }

    public Generator(long seed) {
        this.random = new Random(seed);
    }

    public float[] generateRandomArr(int length) {
        var samples = new float[length];
        for (int i = 0; i < length; i++) {
            samples[i] = random.nextFloat();
        }
        return samples;
    }

    public int[] createSample(float[] predictions, int numSamples) {
        var samples = new int[numSamples];
        for (int i = 0; i < samples.length; i++) {
            samples[i] = createSample(predictions);
        }
        return samples;
    }

    public int createSample(float[] predictions) {
        var cumSum = 0F;
        float random = this.random.nextFloat();
        for (int i = 0; i < predictions.length; i++) {
            cumSum += predictions[i];
            if (random < cumSum) {
                return i;
            }
        }
        return 0;
    }
}
