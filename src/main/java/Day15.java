import util.Direction;
import util.Pos;
import util.Util;

import java.util.*;
import java.util.function.Function;

public class Day15 {

    public static void main(String[] args) {
        var input = Util.readStrings();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static long part2(List<String> input) {
        Map<Pos, Pos> map = new HashMap<>();
        for (String row : input) {
            var split = row.split(":");
            var p = split[0].substring("Sensor at x=".length()).split(", y=");
            var s = new Pos(Integer.parseInt(p[0]), Integer.parseInt(p[1]));
            var pp = split[1].substring(" closest beacon is at x=".length()).split(", y=");
            var b = new Pos(Integer.parseInt(pp[0]), Integer.parseInt(pp[1]));
            map.put(s, b);
        }
        Set<Pos> posList = new HashSet<>();
        for (Map.Entry<Pos, Pos> entry : map.entrySet()) {
            posList.addAll(calc(entry.getKey(), entry.getValue()));
        }
        for (Pos pos : posList) {
            if (pos.x >= 0 && pos.y >= 0 && pos.x <= 4000000 && pos.y <= 4000000) {
                boolean possible = true;
                boolean same = false;
                for (Map.Entry<Pos, Pos> entry : map.entrySet()) {
                    if (entry.getKey().dist(pos) == 0 || entry.getValue().dist(pos) == 0) {
                        same = true;
                    } else if (entry.getKey().dist(pos) <= entry.getKey().dist(entry.getValue())) {
                        possible = false;
                        break;
                    }
                }
                if (possible && !same) {
                    return pos.x*4000000L + pos.y;
                }

            }
        }
        throw new IllegalStateException();
    }

    private static Set<Pos> calc(Pos sensor, Pos beacon) {
        int dist = sensor.dist(beacon) + 1;
        Set<Pos> seen = new HashSet<>();
        Deque<Pos> check = new LinkedList<>();
        for (int y = -1; y <= 1; ++y) {
            for (int x = -1; x <= 1; ++x) {
                var p = new Pos(beacon.x + x, beacon.y + y);
                if (p.dist(sensor) == dist) {
                    check.add(p);
                }
            }
        }
        while (!check.isEmpty()) {
            var pos = check.poll();
            if (!seen.add(pos)) {
                continue;
            }
            for (int y = -1; y <= 1; ++y) {
                for (int x = -1; x <= 1; ++x) {
                    var p = new Pos(pos.x + x, pos.y + y);
                    if (p.dist(sensor) == dist && !seen.contains(p)) {
                        check.push(p);
                    }
                }
            }
        }
        return seen;
    }

    private static int part1(List<String> input) {
        Map<Pos, Pos> map = new HashMap<>();
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        for (String row : input) {
            var split = row.split(":");
            var p = split[0].substring("Sensor at x=".length()).split(", y=");
            int x = Integer.parseInt(p[0]);
            minX = Math.min(x, minX);
            maxX = Math.max(x, maxX);
            var s = new Pos(x, Integer.parseInt(p[1]));
            var pp = split[1].substring(" closest beacon is at x=".length()).split(", y=");
            x = Integer.parseInt(pp[0]);
            minX = Math.min(x, minX);
            maxX = Math.min(x, maxX);
            var b = new Pos(x, Integer.parseInt(pp[1]));
            map.put(s, b);
        }
        int count = 0;
        for (int x = minX-1000000; x <= maxX+1000000; ++x) {
            var p = new Pos(x, 2000000);
            boolean possible = true;
            boolean same = false;
            for (Map.Entry<Pos, Pos> entry : map.entrySet()) {
                if (entry.getKey().dist(p) == 0 || entry.getValue().dist(p) == 0) {
                    same = true;
                } else if (entry.getKey().dist(p) <= entry.getKey().dist(entry.getValue())) {
                    possible = false;
                    break;
                }
            }
            if (!possible && !same) {
                ++count;
            }
        }
        return count;
    }
}
