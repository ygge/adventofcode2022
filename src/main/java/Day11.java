import util.Util;

import java.util.*;
import java.util.stream.Collectors;

public class Day11 {

    public static void main(String[] args) {
        var input = Util.readStrings();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static long part2(List<String> input) {
        return run(input, 10000);
    }

    private static long part1(List<String> input) {
        return run(input, 20);
    }

    private static long run(List<String> input, int num) {
        List<Monkey> monkeys = readMonkeys(input);
        List<Long> times = new ArrayList<>();
        for (Monkey ignored : monkeys) {
            times.add(0L);
        }
        long divisible = monkeys.stream()
                .map(m -> m.divisible)
                .reduce(1L, (a, b) -> a * b);
        for (int i = 0; i < num; ++i) {
            for (int j = 0; j < monkeys.size(); ++j) {
                var monkey = monkeys.get(j);
                times.set(j, times.get(j) + monkey.items.size());
                for (long item : monkey.items) {
                    long level;
                    if (monkey.operation == '+') {
                        level = item + (monkey.opp == -1 ? item : monkey.opp);
                    } else if (monkey.operation == '*') {
                        level = item * (monkey.opp == -1 ? item : monkey.opp);
                    } else {
                        throw new RuntimeException();
                    }
                    if (num == 20) {
                        level /= 3;
                    } else {
                        level %= divisible;
                    }
                    long m = level % monkey.divisible == 0 ? monkey.throwT : monkey.throwF;
                    for (Monkey other : monkeys) {
                        if (other.id == m) {
                            other.items.add(level);
                        }
                    }
                }
                monkey.items.clear();
            }
        }
        Collections.sort(times);
        return times.get(times.size() - 2) * times.get(times.size() - 1);
    }

    private static List<Monkey> readMonkeys(List<String> input) {
        List<Monkey> monkeys = new ArrayList<>();
        for (int i = 0; i < input.size(); ++i) {
            if (input.get(i).startsWith("Monkey")) {
                var m = new Monkey();
                m.id = input.get(i++).charAt(7)-'0';
                m.items = Arrays.stream(input.get(i++).substring("  Starting items: ".length()).split(", "))
                        .map(Long::parseLong)
                        .collect(Collectors.toList());
                var split = input.get(i++).substring("  Operation: new = old ".length()).split(" ");
                m.operation = split[0].charAt(0);
                if (split[1].equals("old")) {
                    m.opp = -1;
                } else {
                    m.opp = Integer.parseInt(split[1]);
                }
                m.divisible = Integer.parseInt(input.get(i++).substring("  Test: divisible by ".length()));
                m.throwT = Integer.parseInt(input.get(i++).substring("    If true: throw to monkey ".length()));
                m.throwF = Integer.parseInt(input.get(i++).substring("    If false: throw to monkey ".length()));
                monkeys.add(m);
            }
        }
        return monkeys;
    }

    private static class Monkey {
        long id, divisible, throwT, throwF, opp;
        List<Long> items = new ArrayList<>();
        char operation;
    }
}
