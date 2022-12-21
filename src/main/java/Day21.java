import util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day21 {

    public static void main(String[] args) {
        var input = Util.readStrings();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static long part2(List<String> input) {
        Map<String, Monkey> monkeys = parseInput(input);
        var monkey = monkeys.get("root");
        List<String> path = new ArrayList<>();
        findMonkey(monkey, "humn", monkeys, path);
        var p = path.get(0);
        if (p.equals(monkey.opp1)) {
            var v = calc(monkey.opp2, monkeys);
            return calc(v, monkey.opp1, path, monkeys, 1);
        } else {
            var v = calc(monkey.opp1, monkeys);
            return calc(v, monkey.opp2, path, monkeys, 1);
        }
    }

    private static long part1(List<String> input) {
        Map<String, Monkey> monkeys = parseInput(input);
        return calc("root", monkeys);
    }

    private static long calc(long v, String name, List<String> path, Map<String, Monkey> monkeys, int index) {
        if (index == path.size()) {
            return v;
        }
        var monkey = monkeys.get(name);
        var p = path.get(index);
        if (p.equals(monkey.opp1)) {
            var vv = calc(monkey.opp2, monkeys);
            var vvv = calcValue(v, vv, monkey, true);
            return calc(vvv, monkey.opp1, path, monkeys, index + 1);
        } else {
            var vv = calc(monkey.opp1, monkeys);
            var vvv = calcValue(v, vv, monkey, false);
            return calc(vvv, monkey.opp2, path, monkeys, index + 1);
        }
    }

    private static long calcValue(long v, long vv, Monkey monkey, boolean first) {
        return switch (monkey.operation) {
            case '+' -> v - vv;
            case '-' -> first ? (v + vv) : (vv - v);
            case '*' -> v / vv;
            case '/' -> first ? (v * vv) : (vv / v);
            default -> throw new IllegalStateException("" + monkey.operation);
        };
    }

    private static boolean findMonkey(Monkey monkey, String test, Map<String, Monkey> monkeys, List<String> path) {
        if (monkey.name.equals(test)) {
            return true;
        }
        if (monkey.number != null) {
            return false;
        }
        path.add(monkey.opp1);
        if (findMonkey(monkeys.get(monkey.opp1), test, monkeys, path)) {
            return true;
        }
        path.remove(path.size() - 1);
        path.add(monkey.opp2);
        if (findMonkey(monkeys.get(monkey.opp2), test, monkeys, path)) {
            return true;
        }
        path.remove(path.size() - 1);
        return false;
    }

    private static Map<String, Monkey> parseInput(List<String> input) {
        Map<String, Monkey> monkeys = new HashMap<>();
        for (String row : input) {
            var split = row.split(": ");
            var monkey = new Monkey();
            monkey.name = split[0];
            if (split[1].contains(" ")) {
                var s = split[1].split(" ");
                monkey.opp1 = s[0];
                monkey.operation = s[1].charAt(0);
                monkey.opp2 = s[2];
            } else {
                monkey.number = Long.parseLong(split[1]);
            }
            monkeys.put(monkey.name, monkey);
        }
        return monkeys;
    }

    private static long calc(String name, Map<String, Monkey> monkeys) {
        var monkey = monkeys.get(name);
        if (monkey.number != null) {
            return monkey.number;
        }
        var a = calc(monkey.opp1, monkeys);
        var b = calc(monkey.opp2, monkeys);
        return switch (monkey.operation) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> a / b;
            default -> throw new IllegalStateException(monkey.operation + "");
        };
    }

    private static class Monkey {
        String name, opp1, opp2;
        Long number;
        char operation;
    }
}
