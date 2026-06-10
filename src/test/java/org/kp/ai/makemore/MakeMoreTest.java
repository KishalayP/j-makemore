package org.kp.ai.makemore;

import junit.framework.TestCase;
import org.kp.ai.makemore.bigrams.BigramsStatistical;
import org.kp.ai.makemore.core.MakeMore;
import org.kp.ai.makemore.services.Generator;
import org.kp.ai.makemore.services.Utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class MakeMoreTest extends TestCase {

    public void testBigram1() throws IOException {
        var makeMore = new MakeMore(Path.of("src/main/resources/names.txt"), new BigramsStatistical(2147483647));
        List<String> predictions = makeMore.getPredictions(10);
        System.out.println(predictions);
        float loss = makeMore.getLoss();
        System.out.println(loss);
    }

    public void testSamples() {
        var generator = new Generator(2147483647);
        float[] floats = generator.generateRandomArr(3);
        floats = Utils.normalizeArr(floats);
        int[] samples = generator.createSample(floats, 3);
    }

    public void testDummy() {
        var generator = new Generator(2147483647);
        var floats = new float[]{0.6064F, 0.3033F, 0.0903F};
        int[] samples = generator.createSample(floats, 3);
    }
}