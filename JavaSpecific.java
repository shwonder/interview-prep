import java.util.Comparator;

/**
 * Created by Mike on 10/17/2017.
 */
public class JavaSpecific {
    // From HackerRank,
    static class ImplementComparator {
        static class Player {
            String name;
            int score;

            Player(String name, int score) {
                this.name = name;
                this.score = score;
            }
        }

        static class Checker implements Comparator<Player> {
            public int compare(Player p1, Player p2) {
                return p2.score == p1.score ? p1.name.compareTo(p2.name) : p2.score - p1.score;
            }
        }
    }
}
