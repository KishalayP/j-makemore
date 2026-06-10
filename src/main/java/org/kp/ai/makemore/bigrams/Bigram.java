package org.kp.ai.makemore.bigrams;

import java.util.List;

public interface Bigram {

    public String getPrediction();
    public void initialize(List<String> input);
    public float getLoss(List<String> expectedOutput);
    public List<String> getPredictions(int numOfPredictions);
}
