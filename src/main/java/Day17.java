import util.Util;

import java.util.*;

public class Day17 {

    private static final List<char[][]> SHAPES = Arrays.asList(
        new char[][] {
                new char[]{'#', '#', '#', '#'}
        },
        new char[][] {
                new char[]{'.', '#', '.'},
                new char[]{'#', '#', '#'},
                new char[]{'.', '#', '.'}
        },
        new char[][] {
                new char[]{'.', '.', '#'},
                new char[]{'.', '.', '#'},
                new char[]{'#', '#', '#'}
        },
        new char[][] {
                new char[]{'#'},
                new char[]{'#'},
                new char[]{'#'},
                new char[]{'#'},
        },
        new char[][] {
                new char[]{'#', '#'},
                new char[]{'#', '#'},
        }
    );

    public static void main(String[] args) {
        Util.verifySubmission();
        var input = Util.readString();
        Util.submitPart1(part1(input));
    }

    private static int part1(String input) {
        int wIndex = 0;
        int rIndex = 0;
        boolean[][] taken = new boolean[10000][7];
        int max = 0;

        for (int i = 0; i < 2022; ++i) {
            int x = 2;
            int y = max + 4;
            char[][] rock = SHAPES.get(rIndex++);
            if (rIndex == SHAPES.size()) {
                rIndex = 0;
            }
            while (true) {
                char w = input.charAt(wIndex++);
                if (wIndex == input.length()) {
                    wIndex = 0;
                }
                if (w == '<') {
                    if (x > 0) {
                        boolean ok = true;
                        for (int yy = 0; yy < rock.length && ok; ++yy) {
                            for (int xx = 0; xx < rock[yy].length; ++xx) {
                                if (rock[yy][xx] == '#' && taken[y+rock.length-yy-1][x+xx-1]) {
                                    ok = false;
                                    break;
                                }
                            }
                        }
                        if (ok) {
                            --x;
                        }
                    }
                } else if (w == '>') {
                    if (x + rock[0].length < 7) {
                        boolean ok = true;
                        for (int yy = 0; yy < rock.length && ok; ++yy) {
                            for (int xx = 0; xx < rock[yy].length; ++xx) {
                                if (rock[yy][xx] == '#' && taken[y+rock.length-yy-1][x+xx+1]) {
                                    ok = false;
                                    break;
                                }
                            }
                        }
                        if (ok) {
                            ++x;
                        }
                    }
                }
                --y;
                boolean ok = y > 0;
                for (int yy = 0; yy < rock.length && ok; ++yy) {
                    for (int xx = 0; xx < rock[yy].length; ++xx) {
                        if (rock[yy][xx] == '#' && taken[y+rock.length-1-yy][x+xx]) {
                            ok = false;
                            break;
                        }
                    }
                }
                if (!ok) {
                    ++y;
                    break;
                }
            }
            for (int yy = 0; yy < rock.length; ++yy) {
                for (int xx = 0; xx < rock[yy].length; ++xx) {
                    if (rock[yy][xx] == '#') {
                        taken[y+rock.length-1-yy][x+xx] = true;
                        max = Math.max(max, y+rock.length-1-yy);
                    }
                }
            }
        }
        return max;
    }
}
