package dice.game;

public class Human implements Player {

    private String name;
    private int gamesWon;
    private int currentThrow;

    /**
     * Constructor
     * @param name name of a player
     */
    public Human(String name) {
        this.name = name;
        gamesWon = 0;
        currentThrow = 0;
    }

    // Getters

    public String getName() {
        return name;
    }

    @Override
    public int getGamesWon() {
        return gamesWon;
    }

    @Override
    public int getCurrentThrow() {
        return currentThrow;
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
