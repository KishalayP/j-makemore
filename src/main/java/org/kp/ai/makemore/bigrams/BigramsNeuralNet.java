package org.kp.ai.makemore.bigrams;

import java.util.List;

public class BigramsNeuralNet implements Bigram {

    private int[] inputForTrainingData;
    private int[] outputForTrainingData;
    private int numInputsOfNN;

    public BigramsNeuralNet(int[] inputForTrainingData, int[] outputForTrainingData) {
        this.inputForTrainingData = inputForTrainingData;
        this.outputForTrainingData = outputForTrainingData;
    }

    @Override
    public String getPrediction() {
        return "";
    }

    @Override
    public void initialize(List<String> input) {

    }

    @Override
    public float getLoss(List<String> expectedOutput) {
        return 0;
    }

    @Override
    public List<String> getPredictions(int numOfPredictions) {
        return List.of();
    }

    private void populateBigrams(List<String> input) {
        for (String word : input) {
            String paddedWord = '.' + word + '.';
            char[] charArray = paddedWord.toCharArray();
            for (int i = 0; i < charArray.length - 1; i++) {

            }
        }
    }

    public float[][] getOneHotEncoding(int[] toEncode, int numOfClasses) {
        var result = new float[numOfClasses][toEncode.length];
        for (int i = 0; i < toEncode.length; i++) {
            var rowResult = new float[numOfClasses];
            rowResult[toEncode[i]] = 1;
            result[i] = rowResult;
        }
        return result;
    }
}
