import util.Direction;
import util.Pos;
import util.Util;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Day12 {

    public static void main(String[] args) {
        var input = Util.readBoard();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static int part2(char[][] board) {
        Pos start = findStart(board);
        int best = run(board, start);
        for (int y = 0; y < board.length; ++y) {
            for (int x = 0; x < board[y].length; ++x) {
                if (board[y][x] == 'a') {
                    int s = run(board, new Pos(x, y));
                    if (s < best) {
                        best = s;
                    }
                }
            }
        }
        return best;
    }

    private static long part1(char[][] board) {
        Pos start = findStart(board);
        return run(board, start);
    }

    private static int run(char[][] board, Pos start) {
        Deque<Node> nodes = new LinkedList<>();
        nodes.add(new Node(start, 0, 'a'));
        Set<Pos> seen = new HashSet<>();
        while (!nodes.isEmpty()) {
            var node = nodes.poll();
            if (!seen.add(node.pos)) {
                continue;
            }
            for (Direction dir : Direction.values()) {
                var pos = node.pos.move(dir);
                if (pos.y >= 0 && pos.x >= 0 && pos.y < board.length && pos.x < board[pos.y].length) {
                    char c = board[pos.y][pos.x];
                    if (c == 'E') {
                        c = 'z';
                    }
                    if (c <= node.height + 1) {
                        if (board[pos.y][pos.x] == 'E') {
                            return node.steps + 1;
                        }
                        nodes.add(new Node(pos, node.steps + 1, c));
                    }
                }
            }
        }
        return Integer.MAX_VALUE;
    }

    private static Pos findStart(char[][] board) {
        for (int y = 0; y < board.length; ++y) {
            for (int x = 0; x < board[y].length; ++x) {
                if (board[y][x] == 'S') {
                    return new Pos(x, y);
                }
            }
        }
        throw new IllegalStateException();
    }

    public static class Node {
        private final Pos pos;
        private final int steps;
        private final char height;

        public Node(Pos pos, int steps, char height) {
            this.pos = pos;
            this.steps = steps;
            this.height = height;
        }
    }
}
