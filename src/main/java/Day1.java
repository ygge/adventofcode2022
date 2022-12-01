import util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day1 {

    public static void main(String[] args) {
        var inputs = Util.readStrings();
        Util.submitPart1(part1(inputs));
        Util.submitPart2(part2(inputs));
    }

    private static int part2(List<String> inputs) {
        List<Integer> w = new ArrayList<>();
        int total = 0;
        for (String input : inputs) {
            if (input.length() == 0) {
                w.add(total);
                total = 0;
            } else {
                total += Integer.parseInt(input);
            }
        }
        Collections.sort(w);
        return w.get(w.size()-1) + w.get(w.size()-2) + w.get(w.size()-3);
    }

    private static int part1(List<String> inputs) {
        int total = 0;
        int max = 0;
        for (String input : inputs) {
            if (input.length() == 0) {
                if (total > max) {
                    max = total;
                }
                total = 0;
            } else {
                total += Integer.parseInt(input);
            }
        }
        return max;
    }
}
