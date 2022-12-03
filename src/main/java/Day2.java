import util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day2 {

    public static void main(String[] args) {
        var inputs = Util.readStrings();
        Util.submitPart1(part1(inputs));
        Util.submitPart2(part2(inputs));
    }

    private static int part2(List<String> inputs) {
        int score = 0;
        for (String input : inputs) {
            var split = input.split(" ");
            int opp = split[0].charAt(0)-'A';
            int res = split[1].charAt(0)-'X';
            score += my(opp, res) + 1;
            score += res * 3;
        }
        return score;
    }

    private static long part1(List<String> inputs) {
        int score = 0;
        for (String input : inputs) {
            var split = input.split(" ");
            int opp = split[0].charAt(0)-'A';
            int my = split[1].charAt(0)-'X';
            score += my + 1;
            score += score(opp, my);
        }
        return score;
    }

    private static int my(int opp, int res) {
        if (res == 1) {
            return opp;
        }
        if (opp == 0) {
            return res == 0 ? 2 : 1;
        }
        if (opp == 1) {
            return res == 0 ? 0 : 2;
        }
        return res == 0 ? 1 : 0;
    }

    private static int score(int opp, int my) {
        if (opp == my) {
            return 3;
        }
        if ((opp == 0 && my == 1) || (opp == 1 && my == 2) || (opp == 2 && my == 0)) {
            return 6;
        }
        return 0;
    }
}
