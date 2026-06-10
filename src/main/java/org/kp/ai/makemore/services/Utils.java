package org.kp.ai.makemore.services;

public class Utils {

    public static Float[] normalizeArr(Float[] floats) {
        var normalizedArr = new Float[floats.length];
        var sum = 0F;
        for (Float aFloat : floats) {
            if (aFloat == null) {
                aFloat = 0F;
            }
            sum += aFloat;
        }
        sum += floats.length;
        for (int i = 0; i < floats.length; i++) {
            Float aFloat = floats[i];
            if (aFloat == null) {
                aFloat = 0F;
            }
            normalizedArr[i] = (aFloat + 1) / sum;
        }
        return normalizedArr;
    }

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
