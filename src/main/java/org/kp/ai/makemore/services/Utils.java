package org.kp.ai.makemore.services;

public class Utils {

    public static float[] normalizeArr(float[] floats) {
        var normalizedArr = new float[floats.length];
        var sum = 0F;
        for (float aFloat : floats) {
            sum += aFloat;
        }
        for (int i = 0; i < floats.length; i++) {
            normalizedArr[i] = floats[i] / sum;
        }
        return normalizedArr;
    }

    public static float[] normalizeArr(int[] floats) {
        var normalizedArr = new float[floats.length];
        var sum = 0F;
        for (float aFloat : floats) {
            sum += aFloat;
        }
        sum += floats.length;
        for (int i = 0; i < floats.length; i++) {
            normalizedArr[i] = (floats[i] + 1) / sum;
        }
        return normalizedArr;
    }

    public static char getItoS(Integer chNum) {
        if (chNum == 0) {
            return '.';
        }
        return (char) (chNum + 96);
    }

    public static int getStoI(Character ch) {
        if (ch == '.') {
            return 0;
        }
        return ((int) (ch)) - (96);
    }
}
