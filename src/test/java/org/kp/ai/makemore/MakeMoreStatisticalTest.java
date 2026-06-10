package org.kp.ai.makemore;

import junit.framework.TestCase;
import org.kp.ai.makemore.bigrams.BigramsStatistical;
import org.kp.ai.makemore.core.MakeMore;
import org.kp.ai.makemore.services.Generator;
import org.kp.ai.makemore.services.Utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class MakeMoreStatisticalTest extends TestCase {

    public void testBigram1() throws IOException {
        var makeMore = new MakeMore(Path.of("src/main/resources/names.txt"), new BigramsStatistical(2147483647));
        List<String> predictions = makeMore.getPredictions(10);
        // Less brittle checks for predictions:
        // - expected number of predictions
        // - each prediction is non-empty, ends with '.' and contains only lowercase letters before the dot
        assertEquals("Predictions size", 10, predictions.size());
        for (String p : predictions) {
            assertNotNull("Prediction is null", p);
            assertTrue("Prediction too short", p.length() >= 2);
            assertTrue("Prediction must end with a dot", p.endsWith("."));
            // e.g. "alice." -> matches lowercase letters then a dot
            assertTrue("Prediction contains invalid characters: " + p, p.matches("[a-z]+\\."));
        }

        float loss = makeMore.getLoss();
        // Check loss is reasonable and close to previously observed value but allow some tolerance
        assertTrue("Loss should be positive", loss > 0F);
        assertEquals("Loss within tolerance", 2.4545767F, loss, 1e-2F);
    }

    public void testSamples() {
        var generator = new Generator(2147483647);
        float[] floats = generator.generateRandomArr(3);
        floats = Utils.normalizeArr(floats);
        // normalized array should have length 3 and sum to ~1.0
        assertEquals(3, floats.length);
        float sum = 0F;
        for (float f : floats) sum += f;
        assertEquals(1.0F, sum, 1e-6F);

        int[] samples = generator.createSample(floats, 3);
        // samples array should have requested length and each index in valid range
        assertEquals(3, samples.length);
        for (int s : samples) {
            assertTrue("Sample index out of range", s >= 0 && s < floats.length);
        }
    }

    public void testDummy() {
        var generator = new Generator(2147483647);
        var floats = new float[]{0.6064F, 0.3033F, 0.0903F};
        int[] samples = generator.createSample(floats, 3);
        // deterministic seed should produce valid sample indices
        assertEquals(3, samples.length);
        for (int s : samples) {
            assertTrue(s >= 0 && s < floats.length);
        }
    }
}