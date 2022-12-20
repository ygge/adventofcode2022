import util.Util;

import java.util.*;

public class Day20 {

    public static void main(String[] args) {
        Util.verifySubmission();
        var input = Util.readInts();
        //var input = Arrays.asList(1, 2, -3, 3, -2, 0, 4);
        //Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static int part2(List<Integer> input) {
        List<Node> nodes = new ArrayList<>();
        Node t = null;
        for (Integer v : input) {
            long vv = v*811589153L;
            long vvv = ((v + input.size() - 1) * 811589153L) % (input.size() - 1);
            Node tt = new Node((int)vvv, vv);
            tt.prev = t;
            if (t != null) {
                t.next = tt;
            }
            nodes.add(tt);
            t = tt;
        }
        Node head = nodes.get(0);
        head.prev = t;
        t.next = head;
        for (int c = 0; c < 10; ++c) {
            for (Node node : nodes) {
                if (node.num == 0) {
                    continue;
                }
                node.next.prev = node.prev;
                node.prev.next = node.next;
                t = node;
                if (node.num > 0) {
                    for (int i = 0; i < node.num; ++i) {
                        t = t.next;
                        if (t == node) {
                            t = t.next;
                        }
                    }
                    node.prev = t;
                    node.next = t.next;
                    t.next = node;
                    node.next.prev = node;
                } else {
                    for (int i = 0; i > node.num; --i) {
                        t = t.prev;
                        if (t == node) {
                            t = t.prev;
                        }
                    }
                    node.next = t;
                    node.prev = t.prev;
                    t.prev = node;
                    node.prev.next = node;
                }
            }
        }
        t = head;
        while (t.num != 0) {
            t = t.next;
        }
        int sum = 0;
        for (int n = 0; n < 3; ++n) {
            for (int i = 0; i < 1000; ++i) {
                t = t.next;
            }
            sum += t.value;
        }
        return sum;
    }

    private static int part1(List<Integer> input) {
        Node head = new Node(input.get(0));
        Node t = head;
        List<Node> nodes = new ArrayList<>();
        nodes.add(head);
        for (int i = 1; i < input.size(); ++i) {
            Node tt = new Node(input.get(i));
            tt.prev = t;
            t.next = tt;
            nodes.add(tt);
            t = tt;
        }
        head.prev = t;
        t.next = head;
        for (Node node : nodes) {
            if (node.num == 0) {
                continue;
            }
            node.next.prev = node.prev;
            node.prev.next = node.next;
            t = node;
            if (node.num > 0) {
                for (int i = 0; i < node.num; ++i) {
                    t = t.next;
                }
                node.prev = t;
                node.next = t.next;
                t.next = node;
                node.next.prev = node;
            } else {
                for (int i = 0; i > node.num; --i) {
                    t = t.prev;
                }
                node.next = t;
                node.prev = t.prev;
                t.prev = node;
                node.prev.next = node;
            }
        }
        t = head;
        while (t.num != 0) {
            t = t.next;
        }
        int sum = 0;
        for (int n = 0; n < 3; ++n) {
            for (int i = 0; i < 1000; ++i) {
                t = t.next;
            }
            sum += t.num;
        }
        return sum;
    }

    private static class Node {
        int num;
        long value;
        Node prev, next;

        public Node(int num) {
            this.num = num;
            this.value = 0;
        }

        public Node(int num, long value) {
            this.num = num;
            this.value = value;
        }
    }
}
