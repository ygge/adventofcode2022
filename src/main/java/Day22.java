import util.Direction;
import util.Pos;
import util.Util;

import java.util.List;

public class Day22 {

    private static final int SIDE = "#........#.................#............#.........".length();

    public static void main(String[] args) {
        //Util.verifySubmission();
        var input = Util.readStrings();
        //Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static long part2(List<String> input) {
        var ins = input.get(input.size() - 1);
        var board = input.subList(0, input.size() - 2);
        Pos pos = null;
        for (int i = 0; i < input.get(0).length(); ++i) {
            if (input.get(0).charAt(i) == '.') {
                pos = new Pos(i, 0);
                break;
            }
        }
        if (pos == null) {
            throw new IllegalStateException();
        }
        var dir = Direction.RIGHT;
        for (int c = 0; c < ins.length(); ) {
            System.out.println(pos + " " + dir);
            char cc = ins.charAt(c);
            if (cc == 'R') {
                dir = dir.turnRight();
                ++c;
            } else if (cc == 'L') {
                dir = dir.turnLeft();
                ++c;
            } else {
                int n = cc - '0';
                ++c;
                while (c < ins.length() && ins.charAt(c) >= '0' && ins.charAt(c) <= '9') {
                    n = n * 10 + ins.charAt(c) - '0';
                    ++c;
                }
                for (int j = 0; j < n; ++j) {
                    var p = pos.move(dir);
                    var d = dir;
                    if (p.x < 0 || p.y < 0 || p.y == board.size() || p.x >= board.get(p.y).length() || board.get(p.y).charAt(p.x) == ' ') {
                        if (dir == Direction.RIGHT) {
                            if (p.y < SIDE) {
                                p = new Pos(0, 2 * SIDE + SIDE - p.y - 1);
                                d = Direction.LEFT;
                                for (int x = board.get(p.y).length() - 1; ; --x) {
                                    if (board.get(p.y).charAt(x) == '.') {
                                        pos = new Pos(x, p.y);
                                        dir = d;
                                        break;
                                    } else if (board.get(p.y).charAt(x) == '#') {
                                        break;
                                    }
                                }
                            } else if (p.y - SIDE < SIDE) {
                                p = new Pos(p.y - SIDE + SIDE * 2, 0);
                                d = Direction.UP;
                                for (int y = board.size() - 1; ; --y) {
                                    if (board.get(y).length() <= p.x) {
                                        continue;
                                    }
                                    if (board.get(y).charAt(p.x) == '.') {
                                        pos = new Pos(p.x, y);
                                        dir = d;
                                        break;
                                    } else if (board.get(y).charAt(p.x) == '#') {
                                        break;
                                    }
                                }
                            } else if (p.y - SIDE * 2 < SIDE) {
                                p = new Pos(0, SIDE - (p.y - SIDE * 2) - 1);
                                d = Direction.LEFT;
                                for (int x = board.get(p.y).length() - 1; ; --x) {
                                    if (board.get(p.y).charAt(x) == '.') {
                                        pos = new Pos(x, p.y);
                                        dir = d;
                                        break;
                                    } else if (board.get(p.y).charAt(x) == '#') {
                                        break;
                                    }
                                }
                            } else {
                                p = new Pos(p.y - SIDE * 2, 0);
                                d = Direction.UP;
                                for (int y = board.size() - 1; ; --y) {
                                    if (board.get(y).length() <= p.x) {
                                        continue;
                                    }
                                    if (board.get(y).charAt(p.x) == '.') {
                                        pos = new Pos(p.x, y);
                                        dir = d;
                                        break;
                                    } else if (board.get(y).charAt(p.x) == '#') {
                                        break;
                                    }
                                }
                            }
                        } else if (dir == Direction.LEFT) {
                            if (p.y < SIDE) {
                                p = new Pos(0, 2 * SIDE + SIDE - p.y - 1);
                                d = Direction.RIGHT;
                                for (int x = 0; ; ++x) {
                                    if (board.get(p.y).charAt(x) == '.') {
                                        pos = new Pos(x, p.y);
                                        dir = d;
                                        break;
                                    } else if (board.get(p.y).charAt(x) == '#') {
                                        break;
                                    }
                                }
                            } else if (p.y - SIDE < SIDE) {
                                p = new Pos(p.y - SIDE, 0);
                                d = Direction.DOWN;
                                for (int y = 0; y < board.size(); ++y) {
                                    if (board.get(y).charAt(p.x) == '.') {
                                        pos = new Pos(p.x, y);
                                        dir = d;
                                        break;
                                    } else if (board.get(y).charAt(p.x) == '#') {
                                        break;
                                    }
                                }
                            } else if (p.y - SIDE * 2 < SIDE) {
                                p = new Pos(0, SIDE - (p.y - SIDE * 2) - 1);
                                d = Direction.RIGHT;
                                for (int x = 0; ; ++x) {
                                    if (board.get(p.y).charAt(x) == '.') {
                                        pos = new Pos(x, p.y);
                                        dir = d;
                                        break;
                                    } else if (board.get(p.y).charAt(x) == '#') {
                                        break;
                                    }
                                }
                            } else {
                                p = new Pos(p.y - SIDE * 2, 0);
                                d = Direction.DOWN;
                                for (int y = 0; y < board.size(); ++y) {
                                    if (board.get(y).length() <= p.x) {
                                        continue;
                                    }
                                    if (board.get(y).charAt(p.x) == '.') {
                                        pos = new Pos(p.x, y);
                                        dir = d;
                                        break;
                                    } else if (board.get(y).charAt(p.x) == '#') {
                                        break;
                                    }
                                }
                            }
                        } else if (dir == Direction.UP) {
                            if (p.x < SIDE) {
                                p = new Pos(0, p.x + SIDE);
                                d = Direction.RIGHT;
                                for (int x = 0; ; ++x) {
                                    if (board.get(p.y).charAt(x) == '.') {
                                        pos = new Pos(x, p.y);
                                        dir = d;
                                        break;
                                    } else if (board.get(p.y).charAt(x) == '#') {
                                        break;
                                    }
                                }
                            } else if (p.x - SIDE < SIDE) {
                                p = new Pos(0, p.x + SIDE * 2);
                                d = Direction.RIGHT;
                                for (int x = 0; ; ++x) {
                                    if (board.get(p.y).charAt(x) == '.') {
                                        pos = new Pos(x, p.y);
                                        dir = d;
                                        break;
                                    } else if (board.get(p.y).charAt(x) == '#') {
                                        break;
                                    }
                                }
                            } else {
                                p = new Pos(p.x - SIDE * 2, 0);
                                d = Direction.UP;
                                for (int y = board.size() - 1; ; --y) {
                                    if (board.get(y).length() <= p.x) {
                                        continue;
                                    }
                                    if (board.get(y).charAt(p.x) == '.') {
                                        pos = new Pos(p.x, y);
                                        dir = d;
                                        break;
                                    } else if (board.get(y).charAt(p.x) == '#') {
                                        break;
                                    }
                                }
                            }
                        } else {
                            if (p.x < SIDE) {
                                p = new Pos(p.x + SIDE * 2, 0);
                                d = Direction.DOWN;
                                for (int y = 0; y < board.size(); ++y) {
                                    if (board.get(y).length() <= p.x) {
                                        continue;
                                    }
                                    if (board.get(y).charAt(p.x) == '.') {
                                        pos = new Pos(p.x, y);
                                        dir = d;
                                        break;
                                    } else if (board.get(y).charAt(p.x) == '#') {
                                        break;
                                    }
                                }
                            } else if (p.x - SIDE < SIDE) {
                                p = new Pos(0, p.x + SIDE * 2);
                                d = Direction.LEFT;
                                for (int x = board.get(p.y).length() - 1; ; --x) {
                                    if (board.get(p.y).charAt(x) == '.') {
                                        pos = new Pos(x, p.y);
                                        dir = d;
                                        break;
                                    } else if (board.get(p.y).charAt(x) == '#') {
                                        break;
                                    }
                                }
                            } else {
                                p = new Pos(0, p.x - SIDE);
                                d = Direction.LEFT;
                                for (int x = board.get(p.y).length() - 1; ; --x) {
                                    if (board.get(p.y).charAt(x) == '.') {
                                        pos = new Pos(x, p.y);
                                        dir = d;
                                        break;
                                    } else if (board.get(p.y).charAt(x) == '#') {
                                        break;
                                    }
                                }
                            }
                        }
                    } else if (board.get(p.y).charAt(p.x) == '#') {
                        break;
                    } else {
                        pos = p;
                    }
                }
            }
        }
        System.out.println(pos + " " + dir);
        return (pos.y + 1) * 1000 + (pos.x + 1) * 4 + switch (dir) {
            case RIGHT -> 0;
            case DOWN -> 1;
            case LEFT -> 2;
            case UP -> 3;
        };
    }

    private static long part1(List<String> input) {
        var ins = input.get(input.size()-1);
        var board = input.subList(0, input.size()-2);
        Pos pos = null;
        for (int i = 0; i < input.get(0).length(); ++i) {
            if (input.get(0).charAt(i) == '.') {
                pos = new Pos(i, 0);
                break;
            }
        }
        if (pos == null) {
            throw new IllegalStateException();
        }
        var dir = Direction.RIGHT;
        for (int c = 0; c < ins.length();) {
            System.out.println(pos + " " + dir);
            char cc = ins.charAt(c);
            if (cc == 'R') {
                dir = dir.turnRight();
                ++c;
            } else if (cc == 'L') {
                dir = dir.turnLeft();
                ++c;
            } else {
                int n = cc-'0';
                ++c;
                while (c < ins.length() && ins.charAt(c) >= '0' && ins.charAt(c) <= '9') {
                    n = n * 10 + ins.charAt(c) - '0';
                    ++c;
                }
                for (int j = 0; j < n; ++j) {
                    var p = pos.move(dir);
                    if (p.x < 0 || p.y < 0 || p.y == board.size() || p.x >= board.get(p.y).length() || board.get(p.y).charAt(p.x) == ' ') {
                        if (dir == Direction.RIGHT) {
                            for (int x = 0; ; ++x) {
                                if (board.get(p.y).charAt(x) == '.') {
                                    pos = new Pos(x, p.y);
                                    break;
                                } else if (board.get(p.y).charAt(x) == '#') {
                                    break;
                                }
                            }
                        } else if (dir == Direction.LEFT) {
                            for (int x = board.get(p.y).length()-1; ; --x) {
                                if (board.get(p.y).charAt(x) == '.') {
                                    pos = new Pos(x, p.y);
                                    break;
                                } else if (board.get(p.y).charAt(x) == '#') {
                                    break;
                                }
                            }
                        } else if (dir == Direction.UP) {
                            for (int y = board.size()-1; ; --y) {
                                if (board.get(y).length() <= p.x) {
                                    continue;
                                }
                                if (board.get(y).charAt(p.x) == '.') {
                                    pos = new Pos(p.x, y);
                                    break;
                                } else if (board.get(y).charAt(p.x) == '#') {
                                    break;
                                }
                            }
                        } else if (dir == Direction.DOWN) {
                            for (int y = 0; ; ++y) {
                                if (board.get(y).length() <= p.x) {
                                    continue;
                                }
                                if (board.get(y).charAt(p.x) == '.') {
                                    pos = new Pos(p.x, y);
                                    break;
                                } else if (board.get(y).charAt(p.x) == '#') {
                                    break;
                                }
                            }
                        }
                    } else if (board.get(p.y).charAt(p.x) == '#') {
                        break;
                    } else {
                        pos = p;
                    }
                }
            }
        }
        System.out.println(pos + " " + dir);
        return (pos.y + 1) * 1000 + (pos.x + 1) * 4 + switch (dir) {
            case RIGHT -> 0;
            case DOWN -> 1;
            case LEFT -> 2;
            case UP -> 3;
        };
    }
}
