package main;
/**
 * @author
 */
public final class ImageMessage {


    /*
     * ********************************************
     * Part 1a: prepare image message (RGB image <-> BW image)
     * ********************************************
     */
    /**
     * Returns red component from given packed color.
     * @param rgb 32-bits RGB color
     * @return an integer between 0 and 255
     * @see #getGreen
     * @see #getBlue
     * @see #getRGB(int, int, int)
     */
    public static int getRed(int rgb) {
        return Utils.clearAlpha(rgb) >> 16;
    }

    /**
     * Returns green component from given packed color.
     * @param rgb 32-bits RGB color
     * @return an integer between 0 and 255
     * @see #getRed
     * @see #getBlue
     * @see #getRGB(int, int, int)
     */
    public static int getGreen(int rgb) {
        return (Utils.clearAlpha(rgb) >> 8) & 0xff;
    }

    /**
     * Returns blue component from given packed color.
     * @param rgb 32-bits RGB color
     * @return an integer between 0 and 255
     * @see #getRed
     * @see #getGreen
     * @see #getRGB(int, int, int)
     */
    public static int getBlue(int rgb) {
        return Utils.clearAlpha(rgb) & 0xff;
    }

    /**
     * Returns the average of red, green and blue components from given packed color.
     * @param rgb 32-bits RGB color
     * @return an integer between 0 and 255
     * @see #getRed
     * @see #getGreen
     * @see #getBlue
     * @see #getRGB(int)
     */
    public static int getGray(int rgb) {
        return (getRed(rgb) + getGreen(rgb) + getBlue(rgb)) / 3;
    }

    /**
     * @param gray an integer between 0 and 255
     * @param threshold
     * @return true if gray is greater or equal to threshold, false otherwise
     */
    public static boolean getBW(int gray, int threshold) {
        return gray >= threshold;
    }

    /**
     * Returns packed RGB components from given red, green and blue components.
     * @param red an integer between 0 and 255
     * @param green an integer between 0 and 255
     * @param blue an integer between 0 and 255
     * @return 32-bits RGB color
     * @see #getRed
     * @see #getGreen
     * @see #getBlue
     */
    public static int getRGB(int red, int green, int blue) {
        return (Utils.safeColor(red) << 16) + (Utils.safeColor(green) << 8) + Utils.safeColor(blue);
    }

    /**
     * Returns packed RGB components from given grayscale value.
     * @param gray an integer between 0 and 255
     * @return 32-bits RGB color
     * @see #getGray
     */
    public static int getRGB(int gray) {
        return getRGB(gray, gray, gray);
    }


    /**
    * Returns packed RGB components from a boolean value.
    * @param value a boolean
    * @return 32-bits RGB encoding of black if value is false
    * and encoding of white otherwise
    */
    public static int getRGB(boolean value) {
        final int color = value ? 255 : 0;
        return getRGB(color, color, color);
    }


    /**
     * Converts packed RGB image to grayscale image.
     * @param image a HxW int array
     * @return a HxW int array
     * @see #getGray
     */
    public static int[][] toGray(int[][] image) {
        if(!Utils.isImage(image))
            return null;

        int[][] grayscale = new int[image.length][image[0].length];
        for(int line = 0; line < image.length; line++)
        {
            for(int row = 0; row < image[0].length; row++)
            {
                grayscale[line][row] = getGray(image[line][row]);
            }
        }
        return grayscale;
    }

    /**
     * Converts grayscale image to packed RGB image.
     * @param gray a HxW grayscale array
     * @return a HxW int array
     * @see #getRGB(int)
     */
    public static int[][] toRGB(int[][] gray) {
        if(!Utils.isImage(gray))
            return null;

        int[][] packed = new int[gray.length][gray[0].length];
        for(int line = 0; line < gray.length; line++)
        {
            for(int row = 0; row < gray[0].length; row++)
            {
                packed[line][row] = getRGB(gray[line][row]);
            }
        }
        return packed;
    }

    /**
     * Converts grayscale image to a black and white image using a given threshold
     * @param gray a HxW int array
     * @param threshold an integer threshold
     * @return a HxW int array
     */
    public static boolean[][] toBW(int[][] gray, int threshold) {
        if(!Utils.isImage(gray))
            return null;

        boolean[][] bw = new boolean[gray.length][gray[0].length];
        for(int line = 0; line < gray.length; line++)
        {
            for(int row = 0; row < gray[0].length; row++)
            {
                bw[line][row] = getBW(gray[line][row], threshold);
            }
        }
        return bw;
    }

    /**
     * Converts a black and white image to packed RGB image
     * @param image a HxW boolean array (false stands for black)
     * @return a HxW int array
     */
    public static int[][] toRGB(boolean[][] image) {
        if(!Utils.isImage(image))
            return null;

        int[][] packed = new int[image.length][image[0].length];
        for(int line = 0; line < image.length; line++)
        {
            for(int row = 0; row < image[0].length; row++)
            {
                packed[line][row] = getRGB(image[line][row]);
            }
        }
        return packed;
    }

    /*
     * ********************************************
     * Part 3: prepare image message for spiral encoding (image <-> bit array)
     * ********************************************
     */
    /**
     * Converts a black-and-white image to a bit array
     * @param bwImage A black and white (boolean) image
     * @return A boolean array containing the binary representation of the image's height and width (32 bits each), followed by the image's pixel values
     * @see ImageMessage#bitArrayToImage(boolean[])
     */
    public static boolean[] bwImageToBitArray(boolean[][] bwImage) {
        if(!Utils.isImage(bwImage))
            return null;

        boolean[] array = new boolean[32 * 2 + bwImage.length * bwImage[0].length];
        boolean[] height = TextMessage.intToBitArray(bwImage.length, 32);
        boolean[] width = TextMessage.intToBitArray(bwImage[0].length, 32);
        for(int i = 0; i < 32; i++)
            array[i] = height[i];
        for(int i = 0; i < 32; i++)
            array[32 + i] = width[i];
        int i = 0;
        for(int line = 0; line < bwImage.length; line++)
        {
            for(int row = 0; row < bwImage[0].length; row++)
            {
                array[32 * 2 + i] = bwImage[line][row];
                i++;
            }
        }
        return array;
    }

    /**
     * Converts a bit array back to a black and white image
     * @param bitArray A boolean array containing the binary representation of the image's height and width (32 bits each), followed by the image's pixel values
     * @return The reconstructed image
     * @see ImageMessage#bwImageToBitArray(boolean[][])
     */
    public static boolean[][] bitArrayToImage(boolean[] bitArray) {
        if(bitArray.length == 0)
            return new boolean[0][0];

        boolean[] bitsHeight = new boolean[32], bitsWidth = new boolean[32];
        for(int i = 0; i < 32; i++)
            bitsHeight[i] = bitArray[i];
        for(int i = 0; i < 32; i++)
            bitsWidth[i] = bitArray[32 + i];
        final int height = TextMessage.bitArrayToInt(bitsHeight), width = TextMessage.bitArrayToInt(bitsWidth);

        boolean[][] array = new boolean[height][width];
        int i = 0;
        for(int line = 0; line < height; line++)
        {
            for(int row = 0; row < width; row++)
            {
                array[line][row] = bitArray[64 + i];
                i++;
            }
        }
        return array;
    }
}
