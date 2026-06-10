package org.kp.ai.makemore.bigrams;

import org.kp.ai.makemore.services.Utils;
import org.kp.ai.makemore.services.Generator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BigramsStatistical implements Bigram {

    public static final List<Character> stoi = intializeStoi();
    public static final List<Integer> itos = intializeItos();

    public Generator generator;
    public int[][] counts;
    public float[][] predictions;

    public BigramsStatistical(long randomSeed) {
        this.counts = new int[27][27];
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
            float[] prediction = predictions[idx];
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
                var i1 = itos.get(charArray[i]);
                var i2 = itos.get(charArray[i + 1]);
                var prediction = predictions[i1][i2];
                logLikeliHood += Math.log(prediction);
                c++;
            }
        }
        return (float) (-logLikeliHood / c);
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        sb.append("Bigrams{");
        append2DArray(sb, counts);
        append2DArray(sb, predictions);
        return sb.toString();
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
        var i1 = itos.get(ch1);
        var i2 = itos.get(ch2);
        if (i1 < 0 || i2 < 0) {
            return;
        }
        counts[i1][i2] += 1;
    }

    private static List<Integer> intializeItos() {
        var stoi = new LinkedList<Integer>();
        int c = 0;
        stoi.add(c);
        for (char ch = 'a'; ch <= 'z'; ch++) {
            stoi.add(c++);
        }
        return stoi;
    }

    private static List<Character> intializeStoi() {
        var stoi = new LinkedList<Character>();
        stoi.add('.');
        for (char ch = 'a'; ch <= 'z'; ch++) {
            stoi.add(ch);
        }
        return stoi;
    }

    private void populatePredictions() {
        predictions = new float[counts.length][counts[0].length];
        for (int i = 0; i < counts.length; i++) {
            predictions[i] = Utils.normalizeArr(counts[i]);
        }
    }

    public Character getItoS(int i) {
        return stoi.get(i);
    }

    private void append2DArray(StringBuilder sb, int[][] matrix) {
        sb.append("counts").append(" = [");
        for (int[] row : matrix) {
            sb.append("\n");
            for (float value : row) {
                sb.append(value).append(", ");
            }
        }
        sb.append("]\n");
    }

    private void append2DArray(StringBuilder sb, float[][] matrix) {
        sb.append("predictions").append(" = [\n");
        for (float[] row : matrix) {
            sb.append("\n");
            for (float value : row) {
                sb.append(value).append(", ");
            }
        }
        sb.append("]\n");
    }
}
