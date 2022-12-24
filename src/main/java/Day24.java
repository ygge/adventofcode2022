import util.Direction;
import util.Pos;
import util.Util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day24 {

    public static void main(String[] args) {
        var board = Util.readBoard();
        Util.submitPart1(part1(board));
        Util.submitPart2(part2(board));
    }

    private static int part2(char[][] board) {
        List<Blizzard> blizzards = new ArrayList<>();
        Pos start = null;
        Pos end = null;
        for (int y = 0; y < board.length; ++y) {
            for (int x = 0; x < board[y].length; ++x) {
                if (board[y][x] == '.' && y == 0) {
                    start = new Pos(x, y);
                } else if (board[y][x] == '.' && y == board.length-1) {
                    end = new Pos(x, y);
                } else if (board[y][x] != '#' && board[y][x] != '.') {
                    blizzards.add(new Blizzard(new Pos(x, y), board[y][x]));
                }
            }
        }
        assert start != null;
        assert end != null;
        int time = 0;
        Set<Pos> states = new HashSet<>();
        states.add(start);
        int state = 0;
        while (true) {
            ++time;
            Pos resetPos = null;
            Set<Pos> newStates = new HashSet<>();
            blizzards = update(blizzards, board);
            var blizzardPos = blizzards.stream()
                    .map(b -> b.pos)
                    .collect(Collectors.toSet());
            for (Pos pos : states) {
                if (!blizzardPos.contains(pos)) {
                    newStates.add(pos);
                }
                for (Direction dir : Direction.values()) {
                    var newPos = pos.move(dir);
                    if (!blizzardPos.contains(newPos)
                            && newPos.y >= 0 && newPos.x >= 0 && newPos.y < board.length && newPos.x < board[newPos.y].length
                            && board[newPos.y][newPos.x] != '#') {
                        newStates.add(newPos);
                        if (newPos.equals(end) && state == 0) {
                            resetPos = newPos;
                            newStates = new HashSet<>();
                            newStates.add(newPos);
                            state = 1;
                            break;
                        } else if (newPos.equals(start) && state == 1) {
                            resetPos = newPos;
                            newStates = new HashSet<>();
                            newStates.add(newPos);
                            state = 2;
                            break;
                        } else if (newPos.equals(end) && state == 2) {
                            return time;
                        }
                    }
                }
                if (resetPos != null) {
                    break;
                }
            }
            states = newStates;
        }
    }

    private static long part(char[][] board) {
        List<Blizzard> blizzards = new ArrayList<>();
        Pos start = null;
        Pos end = null;
        for (int y = 0; y < board.length; ++y) {
            for (int x = 0; x < board[y].length; ++x) {
                if (board[y][x] == '.' && y == 0) {
                    start = new Pos(x, y);
                } else if (board[y][x] == '.' && y == board.length-1) {
                    end = new Pos(x, y);
                } else if (board[y][x] != '#' && board[y][x] != '.') {
                    blizzards.add(new Blizzard(new Pos(x, y), board[y][x]));
                }
            }
        }
        assert start != null;
        assert end != null;
        int time = 0;
        Set<Pos> states = new HashSet<>();
        states.add(start);
        while (true) {
            ++time;
            Set<Pos> newStates = new HashSet<>();
            blizzards = update(blizzards, board);
            var blizzardPos = blizzards.stream()
                    .map(b -> b.pos)
                    .collect(Collectors.toSet());
            for (Pos pos : states) {
                if (!blizzardPos.contains(pos)) {
                    newStates.add(pos);
                }
                for (Direction dir : Direction.values()) {
                    var newPos = pos.move(dir);
                    if (!blizzardPos.contains(newPos) && newPos.y >= 0 && newPos.x >= 0 && board[newPos.y][newPos.x] != '#') {
                        newStates.add(newPos);
                        if (newPos.equals(end)) {
                            return time;
                        }
                    }
                }
            }
            states = newStates;
        }
    }

    private static long part1(char[][] board) {
        List<Blizzard> blizzards = new ArrayList<>();
        Pos start = null;
        Pos end = null;
        for (int y = 0; y < board.length; ++y) {
            for (int x = 0; x < board[y].length; ++x) {
                if (board[y][x] == '.' && y == 0) {
                    start = new Pos(x, y);
                } else if (board[y][x] == '.' && y == board.length-1) {
                    end = new Pos(x, y);
                } else if (board[y][x] != '#' && board[y][x] != '.') {
                    blizzards.add(new Blizzard(new Pos(x, y), board[y][x]));
                }
            }
        }
        assert start != null;
        assert end != null;
        int time = 0;
        Set<Pos> states = new HashSet<>();
        states.add(start);
        while (true) {
            ++time;
            Set<Pos> newStates = new HashSet<>();
            blizzards = update(blizzards, board);
            var blizzardPos = blizzards.stream()
                    .map(b -> b.pos)
                    .collect(Collectors.toSet());
            for (Pos pos : states) {
                if (!blizzardPos.contains(pos)) {
                    newStates.add(pos);
                }
                for (Direction dir : Direction.values()) {
                    var newPos = pos.move(dir);
                    if (!blizzardPos.contains(newPos) && newPos.y >= 0 && newPos.x >= 0 && board[newPos.y][newPos.x] != '#') {
                        newStates.add(newPos);
                        if (newPos.equals(end)) {
                            return time;
                        }
                    }
                }
            }
            states = newStates;
        }
    }

    private static List<Blizzard> update(List<Blizzard> blizzards, char[][] board) {
        List<Blizzard> b = new ArrayList<>();
        for (Blizzard blizzard : blizzards) {
            var pos = blizzard.pos.move(blizzard.dir);
            if (board[pos.y][pos.x] != '#') {
                b.add(new Blizzard(pos, blizzard.dir));
            } else if (blizzard.dir == Direction.LEFT) {
                for (int x = board[pos.y].length-1; x >= 0; --x) {
                    if (board[pos.y][x] != '#') {
                        b.add(new Blizzard(new Pos(x, pos.y), blizzard.dir));
                        break;
                    }
                }
            } else if (blizzard.dir == Direction.RIGHT) {
                for (int x = 0; x < board[pos.y].length-1; ++x) {
                    if (board[pos.y][x] != '#') {
                        b.add(new Blizzard(new Pos(x, pos.y), blizzard.dir));
                        break;
                    }
                }
            } else if (blizzard.dir == Direction.UP) {
                for (int y = board.length-1; y >= 0; --y) {
                    if (board[y][pos.x] != '#') {
                        b.add(new Blizzard(new Pos(pos.x, y), blizzard.dir));
                        break;
                    }
                }
            } else if (blizzard.dir == Direction.DOWN) {
                for (int y = 0; y < board.length; ++y) {
                    if (board[y][pos.x] != '#') {
                        b.add(new Blizzard(new Pos(pos.x, y), blizzard.dir));
                        break;
                    }
                }
            } else {
                throw new IllegalStateException();
            }
        }
        return b;
    }

    private static class Blizzard {
        private final Pos pos;
        private final Direction dir;

        public Blizzard(Pos pos, Direction dir) {
            this.pos = pos;
            this.dir = dir;
        }

        private Blizzard(Pos pos, char dir) {
            this.pos = pos;
            this.dir = switch (dir) {
                case '<' -> Direction.LEFT;
                case '^' -> Direction.UP;
                case '>' -> Direction.RIGHT;
                case 'v' -> Direction.DOWN;
                default -> throw new IllegalArgumentException(dir + "");
            };
        }
    }
}
