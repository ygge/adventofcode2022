import util.MultiDimPos;
import util.Util;

import java.util.*;

public class Day18 {

    public static void main(String[] args) {
        var input = Util.readStrings();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static int part2(List<String> input) {
        Set<MultiDimPos> positions = readPositions(input);
        int count = 0;
        for (MultiDimPos position : positions) {
            for (MultiDimPos pos : neighbours(position)) {
                if (!positions.contains(pos) && !covered(pos, positions)) {
                    ++count;
                }
            }
        }
        return count;
    }

    private static boolean covered(MultiDimPos pos, Set<MultiDimPos> positions) {
        Set<MultiDimPos> seen = new HashSet<>();
        Deque<MultiDimPos> queue = new LinkedList<>();
        queue.add(pos);
        while (!queue.isEmpty()) {
            var node = queue.poll();
            if (!seen.add(node)) {
                continue;
            }
            if (seen.size() >= 10000) {
                return false;
            }
            for (MultiDimPos pos2 : neighbours(node)) {
                if (!seen.contains(pos2) && !positions.contains(pos2)) {
                    queue.add(pos2);
                }
            }
        }
        return true;
    }

    private static int part1(List<String> input) {
        Set<MultiDimPos> positions = readPositions(input);
        int count = 0;
        for (MultiDimPos position : positions) {
            for (MultiDimPos pos : neighbours(position)) {
                if (!positions.contains(pos)) {
                    ++count;
                }
            }
        }
        return count;
    }

    private static Set<MultiDimPos> readPositions(List<String> input) {
        Set<MultiDimPos> positions = new HashSet<>();
        for (String s : input) {
            var split = s.split(",");
            positions.add(new MultiDimPos(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2])));
        }
        return positions;
    }

    private static List<MultiDimPos> neighbours(MultiDimPos pos) {
        List<MultiDimPos> neigh = new ArrayList<>();
        for (int dx = -1; dx <= 1; ++dx) {
            for (int dy = -1; dy <= 1; ++dy) {
                for (int dz = -1; dz <= 1; ++dz) {
                    if (Math.abs(dx) + Math.abs(dy) + Math.abs(dz) == 1) {
                        neigh.add(pos);
                    }
                }
            }
        }
        return neigh;
    }
}
