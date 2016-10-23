
package main;

public class MainChocolate {

    public static void main(String[] args) {

        // A  SECRET TEST OF PART 2 :-)

        // first test case
        String coverName = "EasterEggs-hidden";

        // A cover with a hidden image
        int [][] coverWithSecret =  Helper.read("images/EasterEggs/" + coverName + ".png");

        testRevealText(coverWithSecret);
    }

    public static void testRevealText(int[][] coverWithEmbedding) {
        Helper.show(coverWithEmbedding, "Cover with a secret hidden text");
        String decode = Steganography.revealText(coverWithEmbedding);
        System.out.println("Secret Message:  " + decode);
    }
}