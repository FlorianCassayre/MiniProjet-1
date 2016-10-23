package main;

import java.util.Arrays;

public class Steganography {

    /*
     * ********************************************
     * Part 1b: embed/reveal BW
     * image ********************************************
     */

    /*
     * Methods to deal with the LSB
     */
    /**
     * Inserts a boolean into the Least Significant Bit of an int
     * @param value The int in which to insert a boolean value
     * @param m The boolean value to be inserted into the int
     * @return An int corresponding to {@code value} with {@code m} inserted into its LSB
     */
    public static int embedInLSB(int value, boolean m) {
        return (value & 0xfffffffe) | (m ? 1 : 0);
    }

    /**
     * Extracts the Least Significant Bit of an integer
     * @param value The integer from which to extract the LSB value
     * @return A boolean corresponding to the value of {@code value}'s LSB
     */
    public static boolean getLSB(int value) {
        return (value & 1) == 1;
    }

    /*
     * Linear embedding
     */
    /**
     * Embeds a black and white image into a color image's LSB layer using linear embedding
     * @param cover The image in which to embed {@code message}
     * @param message The image to embed into {@code cover}
     * @return A <b>copy</b> of {@code cover} with {@code message}'s pixel values embedded in a linear fashion in the LSB layer
     */
    public static int[][] embedBWImage(int[][] cover, boolean[][] message) {
        if(cover.length == 0)
            return new int[0][0];
        int[][] copy = new int[cover.length][cover[0].length];

        for(int line = 0; line < cover.length; line++)
        {
            for(int row = 0; row < cover[0].length; row++)
            {
                boolean bit = getLSB(cover[line][row]);
                if(message.length > 0 && line < message.length && row < message[0].length)
                    bit = message[line][row];
                copy[line][row] = embedInLSB(cover[line][row], bit);
            }
        }
        return copy;
    }

    /**
     * Reveals a black and white image which was embedded in the LSB layer of another
     * @param cover A color image containing an image embedded in its LSB layer
     * @return The image extracted from the LSB layer of {@code cover}
     */
    public static boolean[][] revealBWImage(int[][] cover) {
        if(cover.length == 0)
            return new boolean[0][0];
        boolean[][] reveal = new boolean[cover.length][cover[0].length];

        for(int line = 0; line < cover.length; line++)
        {
            for(int row = 0; row < cover[0].length; row++)
            {
                reveal[line][row] = getLSB(cover[line][row]);
            }
        }
        return reveal;
    }

    /*
     * ********************************************
     * Part 2b: embed/reveal
     * BitArray (Text)
     ********************************************
     */

    /**
     * Embeds a boolean array into the LSB layer of a color image, in a linear fashion
     * @param cover The image in which to embed the bit array
     * @param message The boolean array to be embedded
     * @return A <b>copy</b> of {@code cover} with {@code message}'s values embedded in a linear fashion in the LSB layer
     */
    public static int[][] embedBitArray(int[][] cover, boolean[] message) {
        if(cover.length == 0)
            return new int[0][0];
        int[][] copy = new int[cover.length][cover[0].length];
        int i = 0;
        for(int line = 0; line < cover.length; line++)
        {
            for(int row = 0; row < cover[0].length; row++)
            {
                copy[line][row] = i < message.length ? embedInLSB(cover[line][row], message[i]) : cover[line][row];
                i++;
            }
        }
        return copy;
    }

    /**
     * Reveals a boolean array which was embedded in the LSB layer of an image
     * @param cover A color image containing an bit array embedded in its LSB layer
     * @return The bit array extracted from the LSB layer of {@code cover}
     */
    public static boolean[] revealBitArray(int[][] cover) {
        if(cover.length == 0)
            return new boolean[0];
        boolean[] array = new boolean[cover.length * cover[0].length];
        int i = 0;
        for(int line = 0; line < cover.length; line++)
        {
            for(int row = 0; row < cover[0].length; row++)
            {
                array[i] = getLSB(cover[line][row]);
                i++;
            }
        }

        return array;
    }

    /**
     * Embeds a String into the LSB layer of a color image, in a linear fashion
     * @param cover The image in which to embed the bit array
     * @param message The String to be embedded
     * @return A <b>copy</b> of {@code cover} with {@code message}'s binary representation embedded in a linear fashion in the LSB layer
     * @see TextMessage#stringToBitArray(String)
     * @see Steganography#embedBitArray(int[][], boolean[])
     */
    public static int[][] embedText(int[][] cover, String message) {
        return embedBitArray(cover, TextMessage.stringToBitArray(message));
    }

    /**
     * Reveals a String which was embedded in the LSB layer of an image
     * @param cover A color image containing a String embedded in its LSB layer
     * @return The String extracted from the LSB layer of {@code cover}
     * @see TextMessage#bitArrayToString(boolean[])
     */
    public static String revealText(int[][] cover) {
        return TextMessage.bitArrayToString(revealBitArray(cover));
    }

    /*
     * ********************************************
     * Part 3: embed/reveal bit
     * array with spiral embedding
     ********************************************
     */

    /**
     * Embeds a black and white image into a color image's LSB layer using spiral embedding
     * @param cover The image in which to embed {@code message}
     * @param message The image to embed into {@code cover}
     * @return A <b>copy</b> of {@code cover} with {@code message}'s pixel values embedded in a spiral fashion in the LSB layer
     * @see ImageMessage#bwImageToBitArray(boolean[][])
     * @see Steganography#embedSpiralBitArray(int[][], boolean[])
     */
    public static int[][] embedSpiralImage(int[][] cover, boolean[][] bwImage) {
        boolean[] bits = ImageMessage.bwImageToBitArray(bwImage);
        return embedSpiralBitArray(cover, bits);
    }

    /**
     * Reveals an image which was embedded in the LSB layer of an image in a spiral fashion
     * @param cover A color image containing an bit array embedded in its LSB layer
     * @return The image extracted from the LSB layer of {@code cover}
     * @see ImageMessage#bitArrayToImage(boolean[])
     * @see Steganography#revealSpiralBitArray(int[][])
     */
    public static boolean[][] revealSpiralImage(int[][] cover) {
        boolean[] bits = revealSpiralBitArray(cover);
        return ImageMessage.bitArrayToImage(bits);
    }

    /**
     * Embeds a bit array into a color image's LSB layer using linear embedding
     * @param cover The image in which to embed {@code message}
     * @param message The boolean array to embed into {@code cover}
     * @return A <b>copy</b> of {@code cover} with {@code message}'s values embedded in a spiral fashion in the LSB layer
     */
    public static int[][] embedSpiralBitArray(int[][] cover, boolean[] message) {
        if(cover.length == 0)
            return new int[0][0];

        assert(Utils.isCoverLargeEnough(cover, message));

        int[][] copy = new int[cover.length][cover[0].length];
        for(int line = 0; line < cover.length; line++)
            for(int row = 0; row < cover[0].length; row++)
                copy[line][row] = cover[line][row];

        int minLine = 0, maxLine = cover.length - 1, minRow = 0, maxRow = cover[0].length - 1;
        int line = 0, row = 0;
        int dir = 0;
        for(int i = 0; i < message.length; i++)
        {
            copy[line][row] = embedInLSB(cover[line][row], message[i]); // Replacement

            if(row == maxRow && dir == 0)
            {
                minLine++;
                dir++;
            }
            else if(line == maxLine && dir == 1)
            {
                maxRow--;
                dir++;
            }
            else if(row == minRow && dir == 2)
            {
                maxLine--;
                dir++;
            }
            else if(line == minLine && dir == 3)
            {
                minRow++;
                dir = 0;
            }

            if(dir == 0)
                row++;
            else if(dir == 1)
                line++;
            else if(dir == 2)
                row--;
            else if(dir == 3)
                line--;
        }

        return copy;
    }

    /**
     * Reveals a boolean array which was embedded in the LSB layer of an image in a spiral fashion
     * @param cover A color image containing an bit array embedded in its LSB layer
     * @return The bit array extracted from the LSB layer of {@code cover}
     */
    public static boolean[] revealSpiralBitArray(int[][] hidden) {
        if(hidden.length == 0)
            return new boolean[0];
        boolean[] bits = new boolean[hidden.length * hidden[0].length];

        int minLine = 0, maxLine = hidden.length - 1, minRow = 0, maxRow = hidden[0].length - 1;
        int line = 0, row = 0;
        int dir = 0;
        for(int i = 0; i < bits.length; i++)
        {
            bits[i] = getLSB(hidden[line][row]); // Replacement

            if(row == maxRow && dir == 0)
            {
                minLine++;
                dir++;
            }
            else if(line == maxLine && dir == 1)
            {
                maxRow--;
                dir++;
            }
            else if(row == minRow && dir == 2)
            {
                maxLine--;
                dir++;
            }
            else if(line == minLine && dir == 3)
            {
                minRow++;
                dir = 0;
            }

            if(dir == 0)
                row++;
            else if(dir == 1)
                line++;
            else if(dir == 2)
                row--;
            else if(dir == 3)
                line--;
        } // TODO: cleaning

        return bits;
    }

}

