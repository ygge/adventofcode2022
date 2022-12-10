import util.Util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day10 {

    public static void main(String[] args) {
        var input = Util.readStrings();
        Util.submitPart1(part1(input));
        part2(input);
    }

    private static void part2(List<String> input) {
        int x = 1;
        int cycle = 0;
        for (String s : input) {
            printChar(x, cycle);
            ++cycle;
            if (cycle%40 == 0) {
                System.out.println();
            }
            if (!s.equals("noop")) {
                int dx = Integer.parseInt(s.split(" ")[1]);
                printChar(x, cycle);
                x += dx;
                ++cycle;
                if (cycle%40 == 0) {
                    System.out.println();
                }
            }
        }
    }

    private static void printChar(int x, int cycle) {
        cycle %= 40;
        if (cycle == x -1 || cycle == x || cycle == x +1) {
            System.out.print("#");
        } else {
            System.out.print(".");
        }
    }

    private static int part1(List<String> input) {
        Set<Integer> num = new HashSet<>(Arrays.asList(20, 60, 100, 140, 180, 220));
        int sum = 0;
        int x = 1;
        int cycle = 1;
        for (String s : input) {
            ++cycle;
            if (num.contains(cycle)) {
                sum += x*cycle;
            }
            if (!s.equals("noop")) {
                int dx = Integer.parseInt(s.split(" ")[1]);
                x += dx;
                ++cycle;
                if (num.contains(cycle)) {
                    sum += x*cycle;
                }
            }
        }
        return sum;
    }
}
