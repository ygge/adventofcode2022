import util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Day5 {

    public static void main(String[] args) {
        Util.verifySubmission();
        var inputs = Util.readStrings();
        Util.submitPart1(part1(inputs));
        Util.submitPart2(part2(inputs));
    }

    private static String part2(List<String> inputs) {
        List<LinkedList<Character>> stacks = new LinkedList<>();
        int r = parseStacks(inputs, stacks);
        for (; r < inputs.size(); ++r) {
            var split = inputs.get(r).split(" ");
            int num = Integer.parseInt(split[1]);
            int from = Integer.parseInt(split[3])-1;
            int to = Integer.parseInt(split[5])-1;
            List<Character> list = new ArrayList<>();
            for (int i = 0; i < num; ++i) {
                char c = stacks.get(from).pollLast();
                list.add(c);
            }
            Collections.reverse(list);
            stacks.get(to).addAll(list);
        }
        return getResult(stacks);
    }

    private static String part1(List<String> inputs) {
        List<LinkedList<Character>> stacks = new LinkedList<>();
        int r = parseStacks(inputs, stacks);
        for (; r < inputs.size(); ++r) {
            var split = inputs.get(r).split(" ");
            int num = Integer.parseInt(split[1]);
            int from = Integer.parseInt(split[3])-1;
            int to = Integer.parseInt(split[5])-1;
            for (int i = 0; i < num; ++i) {
                char c = stacks.get(from).pollLast();
                stacks.get(to).add(c);
            }
        }
        return getResult(stacks);
    }

    private static String getResult(List<LinkedList<Character>> stacks) {
        var sb = new StringBuilder();
        for (LinkedList<Character> stack : stacks) {
            sb.append(stack.peekLast());
        }
        return sb.toString();
    }

    private static int parseStacks(List<String> inputs, List<LinkedList<Character>> stacks) {
        int r = 0;
        for (; r < inputs.size() && inputs.get(r).charAt(0) == '['; ++r) {
            var row = inputs.get(r);
            int c = 0;
            for (int i = 1; i < row.length(); i += 4) {
                if (c == stacks.size()) {
                    stacks.add(new LinkedList<>());
                }
                char cc = row.charAt(i);
                if (cc != ' ') {
                    stacks.get(c).add(cc);
                }
                ++c;
            }
        }
        for (LinkedList<Character> characters : stacks) {
            Collections.reverse(characters);
        }
        r += 2;
        return r;
    }
}
