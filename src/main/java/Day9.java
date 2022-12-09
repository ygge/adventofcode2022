import util.Direction;
import util.Pos;
import util.Util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day9 {

    public static void main(String[] args) {
        var input = Util.readStrings();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static int part2(List<String> input) {
        List<Pos> positions = new ArrayList<>();
        var head = new Pos(0, 0);
        for (int i = 0; i < 9; ++i) {
            positions.add(new Pos(0, 0));
        }
        Set<Pos> tails = new HashSet<>();
        tails.add(positions.get(8));
        for (String s : input) {
            var split = s.split(" ");
            char d = split[0].charAt(0);
            Direction dd = switch (d) {
                case 'R' -> Direction.RIGHT;
                case 'U' -> Direction.UP;
                case 'L' -> Direction.LEFT;
                case 'D' -> Direction.DOWN;
                default -> throw new IllegalStateException(d + "");
            };
            int num = Integer.parseInt(split[1]);
            for (int i = 0; i < num; ++i) {
                head = head.move(dd);
                var last = head;
                for (int j = 0; j < positions.size(); ++j) {
                    var pos = positions.get(j);
                    if (Math.abs(last.x-pos.x) > 1 || Math.abs(last.y-pos.y) > 1) {
                        Pos t = null;
                        int best = 10;
                        for (int x = -1; x <= 1; ++x) {
                            for (int y = -1; y <= 1; ++y) {
                                Pos tt = new Pos(pos.x + x, pos.y + y);
                                int ddd = last.dist(tt);
                                if (ddd < best) {
                                    t = tt;
                                    best = ddd;
                                }
                            }
                        }
                        positions.set(j, t);
                    }
                    last = positions.get(j);
                }
                tails.add(positions.get(8));
            }
        }
        return tails.size();
    }

    private static int part1(List<String> input) {
        Pos head = new Pos(0, 0);
        Pos tail = new Pos(0, 0);
        Set<Pos> tails = new HashSet<>();
        tails.add(tail);
        for (String s : input) {
            var split = s.split(" ");
            char d = split[0].charAt(0);
            Direction dd = switch (d) {
                case 'R' -> Direction.RIGHT;
                case 'U' -> Direction.UP;
                case 'L' -> Direction.LEFT;
                case 'D' -> Direction.DOWN;
                default -> throw new IllegalStateException(d + "");
            };
            int num = Integer.parseInt(split[1]);
            for (int i = 0; i < num; ++i) {
                head = head.move(dd);
                if (Math.abs(head.x-tail.x) > 1 || Math.abs(head.y- tail.y) > 1) {
                    Pos t = null;
                    int best = 10;
                    for (int x = -1; x <= 1; ++x) {
                        for (int y = -1; y <= 1; ++y) {
                            Pos tt = new Pos(tail.x + x, tail.y + y);
                            int ddd = head.dist(tt);
                            if (ddd < best) {
                                t = tt;
                                best = ddd;
                            }
                        }
                    }
                    tail = t;
                    tails.add(tail);
                }
            }
        }
        return tails.size();
    }
}
