package dice.game;

import java.util.Random;

/**
 * A class implementing a player in the Dice game
 */
public class Player {
    /** Sum of points dropped on the dice  in the current throw */
    private int totalPoints;
    /** Number of games won */
    private int gamesWon;
    /** Score on all dices in one cast */
    private int currentThrow;
    /** Player's name */
    private String name;

    // ************************************************************************
    // Getters
    // ************************************************************************

    public int getTotalPoints() {
        return totalPoints;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public int getCurrentThrow() {
        return currentThrow;
    }

    public String getName() {
        return name;
    }

    /** Method incrementing a <b>gamesWon</b> - called in case of winning a throw */
    public void winThrow() {
        gamesWon++;
    }


    /** Constructor */
    public Player(String name) {
        this.name = name;
        totalPoints = 0;
        gamesWon = 0;
    }

    /**
     * Implements throwing of dices.
     * @param nDice Amount of throwing dices.
     */
    public void roll(int nDice) {
        Random rand = new Random();
        currentThrow = nDice * (rand.nextInt(6) + 1); // value is in bound [nDice ; 6*nDice]
    }
}
