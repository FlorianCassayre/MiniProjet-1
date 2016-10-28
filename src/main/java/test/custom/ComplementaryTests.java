package test.custom;

import static org.junit.Assert.*;

import main.Helper;
import main.ImageMessage;
import main.Steganography;
import org.junit.Test;

public class ComplementaryTests
{

    @Test
    public void mickeyTiles()
    {
        final int[][] image = Helper.read("images/tiles-large.png");
        final boolean[][] expectation = ImageMessage.toBW(Helper.read("images/mickey-mouse-revealed.png"), 128);
        final int[][] hidden = Steganography.embedBWImage(image, expectation);
        final boolean[][] revealed = Steganography.revealBWImage(hidden);

        assertArrayEquals(revealed, expectation);
    }
}
