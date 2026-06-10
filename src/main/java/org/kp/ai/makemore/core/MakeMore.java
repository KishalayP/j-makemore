package org.kp.ai.makemore.core;

import org.kp.ai.makemore.bigrams.Bigram;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class MakeMore {

    public List<String> input;
    public Bigram bigram;

    public MakeMore(Path inputPath, Bigram bigram) throws IOException {
        this.input = Files.readAllLines(inputPath);
        this.bigram = bigram;
        bigram.initialize(input);
    }

    public List<String> getPredictions(int numOfPredictions) {
        return bigram.getPredictions(numOfPredictions);
    }

    public String getPrediction() {
        return bigram.getPrediction();
    }

    public float getLoss() {
        return bigram.getLoss(input);
    }

    @Override
    public String toString() {
        return "MakeMore{" +
                "input=" + input.size() +
                ", bigrams=" + bigram +
                '}';
    }
}
