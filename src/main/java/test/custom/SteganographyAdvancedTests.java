package test.custom;

import main.Steganography;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class SteganographyAdvancedTests
{
    private final static Random random = new Random();

    @Test
    public void encodeDecodeLSB()
    {
        assertEquals(Steganography.embedInLSB(0, false), 0);
        assertEquals(Steganography.embedInLSB(0, true), 1);
        assertEquals(Steganography.embedInLSB(1, false), 0);
        assertEquals(Steganography.embedInLSB(1, true), 1);
        assertEquals(Steganography.embedInLSB(0x00f539e2, true), 0x00f539e3);
        assertEquals(Steganography.embedInLSB(0x0037251b, false), 0x0037251a);

        assertEquals(Steganography.getLSB(0), false);
        assertEquals(Steganography.getLSB(1), true);
        assertEquals(Steganography.getLSB(0x008c261d), true);
        assertEquals(Steganography.getLSB(0x00ecf520), false);
    }

    @Test
    public void spiralConversions()
    {
        assertArrayEquals(new boolean[] {true}, Steganography.revealSpiralBitArray(new int[][] {{0x005812a7}}));
        assertArrayEquals(new boolean[] {false}, Steganography.revealSpiralBitArray(new int[][] {{0x0027d582}}));

        assertArrayEquals(new boolean[] {true, false, true, false}, Steganography.revealSpiralBitArray(new int[][] {{1, 0}, {0, 1}}));
        assertArrayEquals(new boolean[] {false, false, true, true, false, true, false, false, true}, Steganography.revealSpiralBitArray(new int[][] {{0, 0, 1}, {0, 1, 1}, {0, 1, 0}}));

        final boolean[] message = {true, false, true, true, false, false, false, true, true, true, false, true, false, true, false};
        final boolean[][] encodedSpiral = {
                {true, false, true, true, false},
                {true, false, true, false, false},
                {false, true, true, true, false}
        };

        final int[][] image = new int[3][5];
        for(int line = 0; line < image.length; line++)
        {
            for(int row = 0; row < image[0].length; row++)
            {
                image[line][row] = random.nextInt(1 << 24);
            }
        }

        final int[][] imageHidden = Steganography.embedSpiralBitArray(image, message);
        final boolean[] resultArray = Steganography.revealSpiralBitArray(imageHidden);

        final boolean[][] spiralRaw = Steganography.revealBWImage(imageHidden);

        assertArrayEquals(message, resultArray);
        assertArrayEquals(spiralRaw, encodedSpiral);
    }
}
