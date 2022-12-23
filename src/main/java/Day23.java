import util.Direction;
import util.Pos;
import util.Util;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day23 {

    public static void main(String[] args) {
        var board = Util.readBoard();
        Util.submitPart1(part1(board));
        Util.submitPart2(part2(board));
    }

    private static int part2(char[][] board) {
        List<Pos> elves = readElves(board);
        List<Direction> dirs = Arrays.asList(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT);
        for (int t = 0; ; ++t) {
            var prev = new ArrayList<Pos>(elves);
            simulateMove(elves, dirs, t);
            if (prev.equals(elves)) {
                return t + 1;
            }
        }
    }

    private static long part1(char[][] board) {
        List<Pos> elves = readElves(board);
        List<Direction> dirs = Arrays.asList(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT);
        for (int t = 0; t < 10; ++t) {
            simulateMove(elves, dirs, t);
        }
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;
        for (Pos elf : elves) {
            minX = Math.min(minX, elf.x);
            maxX = Math.max(maxX, elf.x);
            minY = Math.min(minY, elf.y);
            maxY = Math.max(maxY, elf.y);
        }
        int count = 0;
        for (int y = minY; y <= maxY; ++y) {
            for (int x = minX; x <= maxX; ++x) {
                if (!elves.contains(new Pos(x, y))) {
                    ++count;
                }
            }
        }
        return count;
    }

    private static void simulateMove(List<Pos> elves, List<Direction> dirs, int t) {
        List<Pos> moves = new ArrayList<>();
        for (Pos elf : elves) {
            boolean move = false;
            for (int dy = -1; dy <= 1 && !move; ++dy) {
                for (int dx = -1; dx <= 1; ++dx) {
                    if (dy != 0 || dx != 0) {
                        if (elves.contains(new Pos(elf.x + dx, elf.y + dy))) {
                            move = true;
                            break;
                        }
                    }
                }
            }
            Pos newPos = null;
            if (move) {
                for (int i = 0; i < 4; ++i) {
                    var dd = dirs.get((t +i) % dirs.size());
                    var pp = elf.move(dd);
                    var ppl = pp.move(dd.turnLeft());
                    var ppr = pp.move(dd.turnRight());
                    if (!elves.contains(pp) && !elves.contains(ppl) && !elves.contains(ppr)) {
                        newPos = pp;
                        break;
                    }
                }
            }
            moves.add(newPos);
        }
        var count = new HashMap<Pos, Integer>();
        for (Pos move : moves) {
            var v = count.getOrDefault(move, 0);
            count.put(move, v + 1);
        }
        for (int i = 0; i < elves.size(); ++i) {
            var move = moves.get(i);
            if (count.get(move) == 1) {
                elves.set(i, move);
            }
        }
    }

    private static List<Pos> readElves(char[][] board) {
        List<Pos> elves = new ArrayList<>();
        for (int y = 0; y < board.length; ++y) {
            for (int x = 0; x < board[y].length; ++x) {
                if (board[y][x] == '#') {
                    elves.add(new Pos(x, y));
                }
            }
        }
        return elves;
    }
}
