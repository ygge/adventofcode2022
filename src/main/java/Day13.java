import util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day13 {

    public static void main(String[] args) {
        var input = Util.readStrings();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static int part2(List<String> input) {
        List<IntList> list = new ArrayList<>();
        for (String s : input) {
            if (s.length() != 0) {
                var a = new IntList();
                parse(s, 1, a);
                list.add(a);
            }
        }
        IntList a = new IntList(new IntList(new IntList(2)));
        list.add(a);
        IntList b = new IntList(new IntList(new IntList(6)));
        list.add(b);

        Collections.sort(list);

        int ai = -1;
        int bi = -1;
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i) == a) {
                ai = i +1;
            } else if (list.get(i) == b) {
                bi = i + 1;
            }
        }

        return ai*bi;
    }

    private static int part1(List<String> input) {
        int sum = 0;
        List<IntList> list = new ArrayList<>();
        int index = 1;
        for (String s : input) {
            if (s.length() == 0) {
                if (compare(list)) {
                    sum += index;
                    System.out.println(index);
                }
                ++index;
                list = new ArrayList<>();
            } else {
                var a = new IntList();
                parse(s, 1, a);
                list.add(a);
            }
        }
        return sum;
    }

    private static boolean compare(List<IntList> list) {
        IntList a = list.get(0);
        IntList b = list.get(1);
        return a.compareTo(b) < 0;
    }

    private static int parse(String s, int index, IntList list) {
        while (s.charAt(index) != ']') {
            if (s.charAt(index) == '[') {
                var a = new IntList();
                index = parse(s, index + 1, a);
                list.list.add(a);
            } else {
                int b = 0;
                while (s.charAt(index) >= '0' && s.charAt(index) <= '9') {
                    b = b*10 + (s.charAt(index)-'0');
                    ++index;
                }
                list.list.add(new IntList(b));
            }
            if (s.charAt(index) == ',') {
                ++index;
            }
        }
        return index + 1;
    }

    private static class IntList implements Comparable<IntList> {
        Integer value;
        List<IntList> list = new ArrayList<>();

        public IntList() { }

        public IntList(int v) {
            this.value = v;
        }

        public IntList(IntList v) {
            list.add(v);
        }

        @Override
        public int compareTo(IntList o) {
            if (o.value != null && value != null) {
                return value - o.value;
            }
            if (value != null) {
                List<IntList> a = new ArrayList<>();
                a.add(this);
                return compare(a, o.list);
            }
            if (o.value != null) {
                List<IntList> a = new ArrayList<>();
                a.add(o);
                return compare(list, a);
            }
            return compare(list, o.list);
        }

        private int compare(List<IntList> a, List<IntList> b) {
            for (int i = 0; i < a.size() && i < b.size(); ++i) {
                var v = a.get(i).compareTo(b.get(i));
                if (v != 0) {
                    return v;
                }
            }
            return a.size() - b.size();
        }
    }
}
