package dice.game;

import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A class that implements the Dice game
 * @author Kirill Artamonov
 */
public class Game {
    private final int GAMES_TO_WIN = 7;
    private final int nDices;
    /** A list of all players of the game */
    private List<Player> players;

    /** a winner of the game */
    private Player winner;

    /**
     * Constructor
     * @param nDices Amount of thrown dices
     * @param players List of players
     */
    public Game(int nDices, List<Player> players) {
        this.players = players;
        this.players.add(new Computer());
        this.nDices = nDices;
        play();
        printResults();
    }

    /**
     * Method implementing the main logics of the Dice game
     */
    public void play() {
        players.forEach(player -> player.setCurrentThrow(Dice.roll(nDices))); // all players make a throw

        (players.stream().max(Comparator // finding the player with most points
                        .comparing(Player::getCurrentThrow))
                .stream().collect(Collectors.toList()))// Putting all throw winners to List
                .forEach(Player::winThrow); // incrementing won games amount

        if ((winner = getWinner()) != null) { // checking if we have a winner already
            return;
        }

        players = players.stream()
                .sorted(Comparator.comparingInt(Player::getCurrentThrow).reversed()) // sort players for next Throw
                .collect(Collectors.toList());

        play(); // continue recursively
    }

    /**
     * Service method to find a winner.
     * @return a <b>Player </b>winner< if there is no winner - null
     */
    private Player getWinner() {
        return players.stream()
                .filter(player -> player.getGamesWon() == GAMES_TO_WIN) // finding the winner if we have
                .findFirst().orElse(null);
    }

    /**
     * Method printing results of the Dice game.
     */
    private void printResults() {
        System.out.println("Winner of the game: " + winner.getName());
    }
}
