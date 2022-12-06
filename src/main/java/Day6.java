import util.Util;

import java.util.*;

public class Day6 {

    public static void main(String[] args) {
        var inputs = Util.readString();
        Util.submitPart1(part1(inputs));
        Util.submitPart2(part2(inputs));
    }

    private static int part1(String input) {
        for (int i = 3; i < input.length(); ++i) {
            Set<Character> seen = new HashSet<>();
            for (int j = i-3; j <= i; ++j) {
                seen.add(input.charAt(j));
            }
            if (seen.size() == 4) {
                return i+1;
            }
        }
        throw new IllegalStateException();
    }

    private static int part2(String input) {
        for (int i = 13; i < input.length(); ++i) {
            Set<Character> seen = new HashSet<>();
            for (int j = i-13; j <= i; ++j) {
                seen.add(input.charAt(j));
            }
            if (seen.size() == 14) {
                return i+1;
            }
        }
        throw new IllegalStateException();
    }
}
