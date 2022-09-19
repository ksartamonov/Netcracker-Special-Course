package dice.game;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A class that implements the Dice game
 * @author Kirill Artamonov
 */
public class DiceGame {

    private final int gamesToWin = 7;
    private final int nDices;
    /** A list of all players of the game */
    private final List<Player> players;
    /** List of winners */
    private List<Player> winners;

    /**
     * Constructor
     * @param nDices Amount of thrown dices
     * @param players List of players
     */
    public DiceGame(int nDices, List<Player> players) {
        this.players = players;
        this.nDices = nDices;
        play();
        printResults();
    }

    /**
     * Method implementing the logics of the Dice game
     *
     * @return a List of winners of the game
     */
    public List<Player> play() {
        if (!(winners = getWinners()).isEmpty()) { // if there is a winner
            return winners;
        }

        players.forEach(player -> player.roll(nDices)); // all players make a throw

        (players.stream().max(Comparator // finding the player moth most points
                .comparing(Player::getCurrentThrow))
                .stream().collect(Collectors.toList()))// Putting all throw winners to List
                .forEach(Player::winThrow); // incrementing won games amount

        play(); // continue recursively
        return null;
    }

    /**
     * Service method to find winners.
     * @return a list of winners
     */
    private List<Player> getWinners() {

        return players.stream()
                .filter(player -> player.getGamesWon() == gamesToWin) // finding players who have enough wins
                .collect(Collectors.toList()); // collecting them all into returned list
    }

    /**
     * Method printing results of the Dice game.
     */
    private void printResults() {
        System.out.println("List of winners:");
        if (!winners.isEmpty()) {
            winners.forEach(player -> System.out.println(player.getName()));
        }
    }

}
