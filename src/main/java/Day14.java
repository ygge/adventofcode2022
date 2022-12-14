import util.Direction;
import util.Pos;
import util.Util;

import java.util.*;
import java.util.function.Function;

public class Day14 {

    public static void main(String[] args) {
        var input = Util.readStrings();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static int part2(List<String> input) {
        Set<Pos> taken = parse(input);
        int largestY = taken.stream()
                .map(p -> p.y)
                .max(Comparator.comparing(Function.identity()))
                .orElseThrow()
                + 2;
        Pos start = new Pos(500, 0);
        for (int i = 0; ; ++i) {
            if (!rest2(start, taken, largestY)) {
                return i + 1;
            }
        }
    }

    private static int part1(List<String> input) {
        Set<Pos> taken = parse(input);
        int largestY = taken.stream()
                .map(p -> p.y)
                .max(Comparator.comparing(Function.identity()))
                .orElseThrow();
        Pos start = new Pos(500, 0);
        for (int i = 0; ; ++i) {
            if (!rest(start, taken, largestY)) {
                return i;
            }
        }
    }

    private static boolean rest2(Pos pos, Set<Pos> taken, int largestY) {
        if (taken.contains(new Pos(500, 1))
                && taken.contains(new Pos(499, 1))
                && taken.contains(new Pos(501, 1))) {
            return false;
        }
        while (!taken.contains(pos) && pos.y < largestY) {
            pos = new Pos(pos.x, pos.y + 1);
        }
        if (pos.y == largestY) {
            taken.add(new Pos(pos.x, pos.y-1));
            return true;
        }
        if (!taken.contains(new Pos(pos.x-1, pos.y))) {
            return rest2(new Pos(pos.x-1, pos.y), taken, largestY);
        }
        if (!taken.contains(new Pos(pos.x+1, pos.y))) {
            return rest2(new Pos(pos.x+1, pos.y), taken, largestY);
        }
        taken.add(new Pos(pos.x, pos.y-1));
        return true;
    }

    private static boolean rest(Pos pos, Set<Pos> taken, int largestY) {
        while (!taken.contains(pos) && pos.y <= largestY) {
            pos = new Pos(pos.x, pos.y + 1);
        }
        if (pos.y > largestY) {
            return false;
        }
        if (!taken.contains(new Pos(pos.x-1, pos.y))) {
            return rest(new Pos(pos.x-1, pos.y), taken, largestY);
        }
        if (!taken.contains(new Pos(pos.x+1, pos.y))) {
            return rest(new Pos(pos.x+1, pos.y), taken, largestY);
        }
        taken.add(new Pos(pos.x, pos.y-1));
        return true;
    }

    private static Set<Pos> parse(List<String> input) {
        Set<Pos> taken = new HashSet<>();
        for (String row : input) {
            var s = row.split(" -> ");
            Pos p = createPos(s[0]);
            taken.add(p);
            for (int i = 1; i < s.length; ++i) {
                Pos p2 = createPos(s[i]);
                while (!p.equals(p2)) {
                    int dist = Integer.MAX_VALUE;
                    Pos b = null;
                    for (Direction dir : Direction.values()) {
                        var pp = p.move(dir);
                        var d = pp.dist(p2);
                        if (d < dist) {
                            b = pp;
                            dist = d;
                        }
                    }
                    assert b != null;
                    p = b;
                    taken.add(p);
                }
                taken.add(p);
            }
        }
        return taken;
    }

    private static Pos createPos(String s) {
        var ss = s.split(",");
        return new Pos(Integer.parseInt(ss[0]), Integer.parseInt(ss[1]));
    }
}
