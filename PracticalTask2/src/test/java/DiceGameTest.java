import dice.game.*;

import java.util.ArrayList;
import java.util.List;

public class DiceGameTest {
    public static void main(String[] args) {
        List<Player> players = new ArrayList<>();
        players.add(new Human("Player1"));
        players.add(new Human("Player2"));
        players.add(new Human("Player3"));
        Game gm = new Game(2, players);
    }
}
