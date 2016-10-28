package test.custom;

import static org.junit.Assert.*;

import main.ImageMessage;
import org.junit.Test;

import java.awt.*;
import java.util.Random;

public class ImageMessageAdvancedTests
{
    private static final Random random = new Random();
    public static final int TESTS = 100;

    @Test
    public void colorsConversions()
    {
        assertEquals(ImageMessage.getRed(0), 0);
        assertEquals(ImageMessage.getGreen(0), 0);
        assertEquals(ImageMessage.getBlue(0), 0);
        assertEquals(ImageMessage.getRGB(0, 0, 0), 0);
        assertEquals(ImageMessage.getRGB(0), 0);
        assertEquals(ImageMessage.getRGB(false), 0);
        assertEquals(ImageMessage.getGray(0), 0);

        for(int i = 0; i < TESTS; i++)
        {
            final int rgb = random.nextInt(1 << 24);
            final Color color = new Color(rgb);
            assertEquals(color.getRed(), ImageMessage.getRed(rgb));
            assertEquals(color.getGreen(), ImageMessage.getGreen(rgb));
            assertEquals(color.getBlue(), ImageMessage.getBlue(rgb));
        }
    }

    @Test
    public void bwToImage()
    {
        final boolean[][] input = {{true, false, false, true}, {false, false, true, true}, {true, false, false, false}};
        final boolean[] output = {true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, true, false, false, true, true, true, false, false, false};

        assertArrayEquals(ImageMessage.bwImageToBitArray(input), output);
        assertArrayEquals(ImageMessage.bitArrayToImage(output), input);
    }
}
