package org.kp.ai.makemore.bigrams;

import org.kp.ai.makemore.model.Matrix;
import org.kp.ai.makemore.services.Utils;
import org.kp.ai.makemore.services.Generator;

import java.util.ArrayList;
import java.util.List;

import static org.kp.ai.makemore.services.Utils.getItoS;

public class BigramsStatistical implements Bigram {

    public Generator generator;
    public Matrix<Float> countsMatrix;
    public Matrix<Float> predictions;

    public BigramsStatistical(long randomSeed) {
        this.countsMatrix = new Matrix<>(Float.class, 27, 27, 0F);
        generator = new Generator(randomSeed);
    }

    @Override
    public void initialize(List<String> input) {
        populateBigrams(input);
        populatePredictions();
    }

    @Override
    public String getPrediction() {
        var sb = new StringBuilder();
        var idx = 0;
        do {
            Float[] prediction = predictions.getRow(idx);
            idx = generator.createSample(prediction);
            sb.append(getItoS(idx));
        } while (idx != 0);
        return sb.toString();
    }

    @Override
    public List<String> getPredictions(int numOfPredictions) {
        var result = new ArrayList<String>();
        for (int i = 0; i < numOfPredictions; i++) {
            result.add(getPrediction());
        }
        return result;
    }

    @Override
    public float getLoss(List<String> expectedOutput) {
        double logLikeliHood = 0;
        int c = 0;
        for (String word : expectedOutput) {
            String paddedWord = '.' + word + '.';
            char[] charArray = paddedWord.toCharArray();
            int i;
            for (i = 0; i < charArray.length - 1; i++) {
                var i1 = Utils.getStoI(charArray[i]);
                var i2 = Utils.getStoI(charArray[i + 1]);
                var prediction = predictions.getValue(i1, i2);
                logLikeliHood += Math.log(prediction);
                c++;
            }
        }
        return (float) (-logLikeliHood / c);
    }

    @Override
    public String toString() {
        return "BigramsStatistical{" +
                "generator=" + generator +
                ", countsMatrix=" + countsMatrix +
                ", predictions=" + predictions +
                '}';
    }

    private void populateBigrams(List<String> input) {
        for (String word : input) {
            String paddedWord = '.' + word + '.';
            char[] charArray = paddedWord.toCharArray();
            for (int i = 0; i < charArray.length - 1; i++) {
                addBigram(charArray[i], charArray[i + 1]);
            }
        }
    }

    private void addBigram(char ch1, char ch2) {
        var i1 = Utils.getStoI(ch1);
        var i2 = Utils.getStoI(ch2);
        if (i1 < 0 || i2 < 0) {
            return;
        }
        setFrequencyOfBigram(i1, i2);
    }

    private void setFrequencyOfBigram(int i1, int i2) {
        Float value = countsMatrix.getValue(i1, i2);
        if (value == null) {
            countsMatrix.setValue(i1, i2, 1F);
        } else {
            countsMatrix.setValue(i1, i2, ++value);
        }
    }

    private void populatePredictions() {
        predictions = new Matrix<>(Float.class, countsMatrix.getRowSize(), countsMatrix.getColumnSize(), 0F);
        for (int i = 0; i < countsMatrix.getRowSize(); i++) {
            predictions.setRow(i, Utils.normalizeArr(countsMatrix.getRow(i)));
        }
    }
}
