package dice.game;

import java.util.Random;

/**
 * A class implementing a dice.
 */
public class Dice {
    /**
     * Method implements throwing dices.
     * @param nDice amount of thrown dices
     * @return Total points on all dices
     */
    public static int roll(int nDice) {
        Random rand = new Random();
        int val = 0;
        for (int i = 0; i < nDice; i++) { // value is in bound [nDice ; 6*nDice]
            val += rand.nextInt(6) + 1;
        }
        return val;
    }
}
