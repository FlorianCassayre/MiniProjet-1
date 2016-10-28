package test.custom;

import static org.junit.Assert.*;

import main.ImageMessage;
import main.Steganography;
import org.junit.Test;

import java.awt.*;
import java.util.Random;

public class SteganographyAdvancedTests
{

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
}
