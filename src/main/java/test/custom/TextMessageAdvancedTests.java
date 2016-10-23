package test.custom;

import static org.junit.Assert.*;

import main.TextMessage;
import org.junit.Test;

import java.util.Random;

public class TextMessageAdvancedTests
{
    private static final Random random = new Random();
    public static final int TESTS = 100;

    @Test
    public void intBooleanConversion()
    {
        assertEquals(TextMessage.intToBitArray(0, 0).length, 0);
        assertEquals(TextMessage.bitArrayToInt(new boolean[0]), 0);
        assertArrayEquals(TextMessage.intToBitArray(0, 1), new boolean[] {false});
        assertArrayEquals(TextMessage.intToBitArray(1, 1), new boolean[] {true});

        for(int i = 0; i < TESTS; i++) // Byte
        {
            int value = random.nextInt((int) Byte.MAX_VALUE);
            boolean[] bits = TextMessage.intToBitArray(value, Byte.SIZE);
            assertEquals(bits.length, Byte.SIZE);
            int result = TextMessage.bitArrayToInt(bits);
            assertEquals(value, result);
        }

        for(int i = 0; i < TESTS; i++) // Integer
        {
            int value = random.nextInt(Integer.MAX_VALUE);
            boolean[] bits = TextMessage.intToBitArray(value, Integer.SIZE);
            assertEquals(bits.length, Integer.SIZE);
            int result = TextMessage.bitArrayToInt(bits);
            assertEquals(value, result);
        }
    }

    @Test
    public void stringBooleanConversion()
    {
        assertEquals(TextMessage.bitArrayToString(new boolean[0]), "");
        assertArrayEquals(TextMessage.stringToBitArray(""), new boolean[0]);
        assertArrayEquals(TextMessage.stringToBitArray(Character.toString('a')), TextMessage.intToBitArray('a', 16));

        for(int i = 0; i < TESTS; i++) // Random strings
        {
            int length = random.nextInt(1000);
            StringBuilder builder = new StringBuilder();
            for(int j = 0; j < length; j++)
            {
                builder.append((char) (random.nextInt('z' - 'A' + 1) + 'A'));
            }
            boolean[] bits = TextMessage.stringToBitArray(builder.toString());
            String str = TextMessage.bitArrayToString(bits);
            assertEquals(builder.toString(), str);
        }
    }
}
