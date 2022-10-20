package dice.game;

/**
 * Public class implementing Computer Player int the Dice Game.
 * Computer is participating in all games and added to them automatically.
 */
public class Computer implements Player {
    private int gamesWon;
    private int currentThrow;

    /**
     * Constructor
     */
    public Computer() {
        gamesWon = 0;
        currentThrow = 0;
    }

    // Getters
    @Override
    public int getGamesWon() {
        return gamesWon;
    }

    @Override
    public int getCurrentThrow() {
        return currentThrow;
    }

    @Override
    public String getName() {
        return "Computer"; //computers name is set
    }


    // Setters
    @Override
    public void winThrow() {
        gamesWon++;
    }

    @Override
    public void setCurrentThrow(int currentThrow) {
        this.currentThrow = currentThrow;
    }


}
