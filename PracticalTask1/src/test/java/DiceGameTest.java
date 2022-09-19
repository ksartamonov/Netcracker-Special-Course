import adress.persons.PersonsArray;
import dice.game.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Class fot testing <b>DiceGame</b> class
 * @author Kirill Artamonov
 * @see DiceGame
 */
public class DiceGameTest {
    public static void main(String[] args) {
        List<Player> players = new ArrayList<>();
        players.add(new Player("Player1"));
        players.add(new Player("Player2"));
        players.add(new Player("Player3"));
        DiceGame dg = new DiceGame(2, players);
    }
}
