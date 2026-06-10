package org.kp.ai.makemore.services;

import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

public class Generator {

    public RandomGenerator randomGenerator;

    public Generator() {
        this.randomGenerator = RandomGeneratorFactory.of("L128X256MixRandom").create();
    }

    public Generator(long seed) {
        this.randomGenerator = RandomGeneratorFactory.of("L128X256MixRandom").create(seed);
    }

    public float[] getRandomNormalDistribution(int row, int column) {
        randomGenerator.nextGaussian(50, 10);
        return null;
    }

    public Float[] generateRandomArr(int length) {
        var samples = new Float[length];
        for (int i = 0; i < length; i++) {
            samples[i] = randomGenerator.nextFloat();
        }
        return samples;
    }

    public int[] createSample(Float[] predictions, int numSamples) {
        var samples = new int[numSamples];
        for (int i = 0; i < samples.length; i++) {
            samples[i] = createSample(predictions);
        }
        return samples;
    }

    public int createSample(Float[] predictions) {
        var cumSum = 0F;
        float random = this.randomGenerator.nextFloat();
        for (int i = 0; i < predictions.length; i++) {
            if (predictions[i] == null) {
                predictions[i] = 0F;
            }
            cumSum += predictions[i];
            if (random < cumSum) {
                return i;
            }
        }
        return 0;
    }
}
