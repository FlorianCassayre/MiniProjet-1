package main;


public class TextMessage {

    /*
     * ********************************************
     * Part 2a: prepare text message (text <-> bit array)
     * ********************************************
     */

    /**
     * In case this needs to be changed.
     */
    private static final int CHARACTER_LENGTH = Character.SIZE;

    /**
     * Converts an integer to its binary representation
     * @param value The integer to be converted
     * @param bits The number of bits for {@code value}'s binary representation
     * @return A boolean array corresponding to {@code value}'s {@code bits}-bit binary representation
     */
    public static boolean[] intToBitArray(int value, int bits) {
        boolean[] array = new boolean[bits];
        for(int i = 0; i < bits; i++)
        {
            array[i] = ((value >> i) & 1) == 1; // Applying a mask and shifting
        }
        return array;
    }

    /**
     * Converts a bit array to it's integer value
     * @param bitArray A boolean array corresponding to an integer's binary representation
     * @return The integer that the array represented
     */
    public static int bitArrayToInt(boolean[] bitArray) {
        assert bitArray != null;

        int value = 0;
        for(int i = 0; i < bitArray.length; i++)
        {
            value |= bitArray[i] ? (1 << i) : 0; // Shifting and encoding
        }
        return value;
    }

    /**
     * Converts a String to its binary representation, i.e. the sequence of the 16-bit binary representations of its chars' integer values
     * @param message The String to be converted
     * @return A boolean array corresponding to the String's binary representation
     */
    public static boolean[] stringToBitArray(String message) {
        assert message != null;

        boolean[] array = new boolean[message.length() * CHARACTER_LENGTH];
        for(int i = 0; i < message.length(); i++) // For every characters in message
        {
            final char c = message.charAt(i);
            final boolean[] bits = intToBitArray(c, CHARACTER_LENGTH);
            System.arraycopy(bits, 0, array, i * CHARACTER_LENGTH, CHARACTER_LENGTH);
        }
        return array;
    }

    /**
     * Converts a boolean array to the String of which it is the representation
     * @param bitArray A boolean array representing a String
     * @return The String that the array represented
     * @see TextMessage#stringToBitArray(String)
     */
    public static String bitArrayToString(boolean[] bitArray) {
        assert bitArray != null;

        char[] characters = new char[bitArray.length / CHARACTER_LENGTH];

        for(int i = 0; i < characters.length; i++) // For every 16-bit array in bitArray
        {
            boolean[] array = new boolean[CHARACTER_LENGTH];
            System.arraycopy(bitArray, i * CHARACTER_LENGTH, array, 0, CHARACTER_LENGTH);
            characters[i] = (char) bitArrayToInt(array);
        }
        return new String(characters); //.replace("\r", ""); // Formerly used to remove unwanted characters that prevented the message to be read
    }

}
