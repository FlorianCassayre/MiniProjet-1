package main;


public class TextMessage {

    /*
     * ********************************************
     * Part 2a: prepare text message (text <-> bit array)
     * ********************************************
     */
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

        boolean[] array = new boolean[message.length() * Character.SIZE];
        for(int i = 0; i < message.length(); i++) // For every characters in message
        {
            final char c = message.charAt(i);
            final boolean[] bits = intToBitArray(c, Character.SIZE);
            for(int j = 0; j < Character.SIZE; j++) // Encode the 16-bit character
            {
                array[i * Character.SIZE + j] = bits[j];
            }
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

        char[] characters = new char[bitArray.length >> 4];

        for(int i = 0; i < (bitArray.length >> 4); i++) // For every 16-bit array in bitArray
        {
            boolean[] array = new boolean[16];
            for(int j = 0; j < 16; j++) // Decode the array and converts it into a character
            {
                array[j] = bitArray[i * 16 + j];
            }
            characters[i] = (char) bitArrayToInt(array);
        }
        return new String(characters);//.replace("\r", ""); // Formerly used to remove unwanted characters that prevented the message to be read
    }

}
