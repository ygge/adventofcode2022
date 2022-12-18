import util.Util;

import java.util.*;

public class Day16 {

    public static void main(String[] args) {
        var input = Util.readStrings();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static int part2(List<String> input) {
        Map<String, Node> nodes = new HashMap<>();
        for (String row : input) {
            var s = row.split(";");
            var ss = s[0].split(" ");
            var name = ss[1];
            var p = Integer.parseInt(ss[4].split("=")[1]);
            var t = s[1].substring("tunnels lead to valves ".length()).split(", ");
            Node node = new Node();
            node.name = name;
            node.pressure = p;
            for (String tt : t) {
                node.next.add(tt.trim());
            }
            nodes.put(name, node);
        }
        Set<State2> seen = new HashSet<>();
        Deque<State2> state = new LinkedList<>();
        state.add(new State2("AA"));
        int best = 0;
        int num = (int)nodes.values().stream()
                .filter(n -> n.pressure > 0)
                .count();
        while (!state.isEmpty()) {
            var s = state.poll();
            if (!seen.add(s)) {
                continue;
            }
            if (s.total > best) {
                System.out.println(best);
                best = s.total;
            }
            if (s.time == 0 && s.opened.size() < num) {
                continue;
            }
            Integer totalNotOpened = nodes.values()
                    .stream()
                    .filter(n -> !s.opened.contains(n.name))
                    .map(n -> n.pressure)
                    .reduce(0, Integer::sum);
            if (best >= s.total + totalNotOpened * (s.time-1)) {
                continue;
            }
            if (s.moved1) {
                if (!s.opened.contains(s.pos2) && nodes.get(s.pos2).pressure > 0) {
                    var ns = new State2(s);
                    --ns.time;
                    ns.opened.add(s.pos2);
                    ns.total += nodes.get(s.pos2).pressure * ns.time;
                    state.add(ns);
                }
                for (String next : nodes.get(s.pos2).next) {
                    var ns = new State2(s);
                    --ns.time;
                    ns.pos2 = next;
                    state.add(ns);
                }
            } else {
                if (!s.opened.contains(s.pos1) && nodes.get(s.pos1).pressure > 0) {
                    var ns = new State2(s);
                    ns.moved1 = true;
                    ns.opened.add(s.pos1);
                    ns.total += nodes.get(s.pos1).pressure * (ns.time - 1);
                    state.add(ns);
                }
                for (String next : nodes.get(s.pos1).next) {
                    var ns = new State2(s);
                    ns.moved1 = true;
                    ns.pos1 = next;
                    state.add(ns);
                }
            }
        }
        return best;
    }

    private static int part1(List<String> input) {
        Map<String, Node> nodes = new HashMap<>();
        for (String row : input) {
            var s = row.split(";");
            var ss = s[0].split(" ");
            var name = ss[1];
            var p = Integer.parseInt(ss[4].split("=")[1]);
            var t = s[1].substring("tunnels lead to valves ".length()).split(", ");
            Node node = new Node();
            node.name = name;
            node.pressure = p;
            for (String tt : t) {
                node.next.add(tt.trim());
            }
            nodes.put(name, node);
        }
        Set<State> seen = new HashSet<>();
        Deque<State> state = new LinkedList<>();
        state.add(new State("AA"));
        int best = 0;
        while (!state.isEmpty()) {
            var s = state.poll();
            if (!seen.add(s)) {
                continue;
            }
            best = Math.max(best, s.total);
            if (!s.opened.contains(s.pos) && nodes.get(s.pos).pressure > 0) {
                State ns = new State(s);
                ns.opened.add(s.pos);
                ns.total += nodes.get(s.pos).pressure * ns.time;
                state.add(ns);
            }
            for (String next : nodes.get(s.pos).next) {
                State ns = new State(s);
                ns.pos = next;
                state.add(ns);
            }
        }
        return best;
    }

    private static class State2 {
        private String pos1, pos2;
        private boolean moved1 = false;
        private int time, total;
        private Set<String> opened = new HashSet<>();

        public State2(String pos) {
            this.pos1 = pos;
            this.pos2 = pos;
            time = 26;
            total = 0;
        }

        public State2(State2 state) {
            this.pos1 = state.pos1;
            this.pos2 = state.pos2;
            this.moved1 = false;
            this.time = state.time;
            this.total = state.total;
            this.opened = new HashSet<>(state.opened);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            var state = (State2) o;
            return Objects.equals(pos1, state.pos1) && Objects.equals(pos2, state.pos2)
                    && this.total == state.total && this.time == state.time;
        }

        @Override
        public int hashCode() {
            return Objects.hash(pos1, pos2, total, time);
        }
    }

    private static class State {
        private String pos;
        private int time, total;
        private Set<String> opened = new HashSet<>();

        public State(String pos) {
            this.pos = pos;
            time = 30;
            total = 0;
        }

        public State(State state) {
            this.pos = state.pos;
            this.time = state.time - 1;
            this.total = state.total;
            this.opened = new HashSet<>(state.opened);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            var state = (State) o;
            return Objects.equals(pos, state.pos) && this.total == state.total;
        }

        @Override
        public int hashCode() {
            return Objects.hash(pos, total);
        }
    }

    private static class Node {
        private String name;
        private int pressure;
        private List<String> next = new ArrayList<>();
    }
}
