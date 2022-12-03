import util.Util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day3 {

    public static void main(String[] args) {
        var inputs = Util.readStrings();
        Util.submitPart1(part1(inputs));
        Util.submitPart2(part2(inputs));
    }

    private static int part2(List<String> inputs) {
        int sum = 0;
        for (int j = 0; j < inputs.size(); j += 3) {
            Set<Character> aa = new HashSet<>();
            for (int i = 0; i < inputs.get(j).length(); ++i) {
                aa.add(inputs.get(j).charAt(i));
            }
            Set<Character> bb = new HashSet<>();
            for (int i = 0; i < inputs.get(j+1).length(); ++i) {
                bb.add(inputs.get(j+1).charAt(i));
            }
            Set<Character> cc = new HashSet<>();
            for (int i = 0; i < inputs.get(j+2).length(); ++i) {
                cc.add(inputs.get(j+2).charAt(i));
            }
            aa.retainAll(bb);
            aa.retainAll(cc);
            char s = aa.stream().findAny().orElseThrow();
            if (s >= 'a' && s <= 'z') {
                sum += s-'a'+1;
            } else {
                sum += s-'A'+27;
            }
        }
        return sum;
    }

    private static int part1(List<String> inputs) {
        int sum = 0;
        for (String input : inputs) {
            var a = input.substring(0, input.length()/2);
            var b = input.substring(input.length()/2);
            Set<Character> aa = new HashSet<>();
            Set<Character> bb = new HashSet<>();
            for (int i = 0; i < a.length(); ++i) {
                aa.add(a.charAt(i));
                bb.add(b.charAt(i));
            }
            aa.retainAll(bb);
            char s = aa.stream().findAny().orElseThrow();
            if (s >= 'a' && s <= 'z') {
                sum += s-'a'+1;
            } else {
                sum += s-'A'+27;
            }
        }
        return sum;
    }
}
