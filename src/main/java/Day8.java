import util.Util;

import java.util.*;

public class Day8 {

    public static void main(String[] args) {
        var inputs = Util.readIntBoard();
        Util.submitPart1(part1(inputs));
        Util.submitPart2(part2(inputs));
    }

    private static long part2(int[][] grid) {
        long score = 1;
        for (int y = 0; y < grid.length; ++y) {
            for (int x = 0; x < grid[y].length; ++x) {
                long s = score(grid, y, x);
                System.out.println(x + " " + y + " " + s);
                if (s > score) {
                    score = s;
                }
            }
        }
        return score;
    }

    private static int part1(int[][] grid) {
        int count = 0;
        for (int y = 0; y < grid.length; ++y) {
            for (int x = 0; x < grid[y].length; ++x) {
                if (visible(grid, y, x)) {
                    ++count;
                }
            }
        }
        return count;
    }

    private static long score(int[][] grid, int y, int x) {
        long score = 1;
        int count = 0;
        for (int i = y-1; i >= 0; --i) {
            ++count;
            if (grid[i][x] >= grid[y][x]) {
                break;
            }
        }
        score *= count;
        count = 0;
        for (int i = x-1; i >= 0; --i) {
            ++count;
            if (grid[y][i] >= grid[y][x]) {
                break;
            }
        }
        score *= count;
        count = 0;
        for (int i = y+1; i < grid.length; ++i) {
            ++count;
            if (grid[i][x] >= grid[y][x]) {
                break;
            }
        }
        score *= count;
        count = 0;
        for (int i = x+1; i < grid[y].length; ++i) {
            ++count;
            if (grid[y][i] >= grid[y][x]) {
                break;
            }
        }
        score *= count;
        return score;
    }

    private static boolean visible(int[][] grid, int y, int x) {
        boolean visible = true;
        for (int i = y-1; i >= 0; --i) {
            if (grid[i][x] >= grid[y][x]) {
                visible = false;
                break;
            }
        }
        if (visible) {
            return true;
        }
        visible = true;
        for (int i = x-1; i >= 0; --i) {
            if (grid[y][i] >= grid[y][x]) {
                visible = false;
                break;
            }
        }
        if (visible) {
            return true;
        }
        visible = true;
        for (int i = y+1; i < grid.length; ++i) {
            if (grid[i][x] >= grid[y][x]) {
                visible = false;
                break;
            }
        }
        if (visible) {
            return true;
        }
        visible = true;
        for (int i = x+1; i < grid[y].length; ++i) {
            if (grid[y][i] >= grid[y][x]) {
                visible = false;
                break;
            }
        }
        return visible;
    }
}
