import util.Util;

import java.util.*;

public class Day19 {

    public static void main(String[] args) {
        Util.verifySubmission();
        var input = Util.readStrings();
        Util.submitPart1(part1(input));
    }

    private static int part1(List<String> input) {
        List<Blueprint> blueprints = new ArrayList<>();
        for (String row : input) {
            var r = row.split(" ");
            var b = new Blueprint();
            b.id = Integer.parseInt(r[1].substring(0, r[1].length()-1));
            b.ore = new Robot();
            b.ore.ore = Integer.parseInt(r[6]);
            b.clay = new Robot();
            b.clay.ore = Integer.parseInt(r[12]);
            b.obsidian = new Robot();
            b.obsidian.ore = Integer.parseInt(r[18]);
            b.obsidian.clay = Integer.parseInt(r[21]);
            b.geode = new Robot();
            b.geode.ore = Integer.parseInt(r[27]);
            b.geode.obsidian = Integer.parseInt(r[30]);
            blueprints.add(b);
        }
        int result = 0;
        for (Blueprint blueprint : blueprints) {
            int res = calc(blueprint);
            System.out.println(blueprint.id + " " + res);
            result += blueprint.id * res;
        }
        return result;
    }

    private static int calc(Blueprint blueprint) {
        var start = new Node();
        Deque<Node> nodes = new LinkedList<>();
        nodes.add(start);
        int maxOre = Math.max(Math.max(blueprint.ore.ore, blueprint.clay.ore), Math.max(blueprint.obsidian.ore, blueprint.geode.ore));
        int maxClay = Math.max(Math.max(blueprint.ore.clay, blueprint.clay.clay), Math.max(blueprint.obsidian.clay, blueprint.geode.clay));
        int best = 0;
        while (!nodes.isEmpty()) {
            var node = nodes.poll();
            var res = node.geode + node.robots[3] * (24-node.time);
            if (res > best) {
                best = res;
            } else if (node.geode + (node.robots[3] + 1) * (24-node.time) < res) {
                continue;
            }
            if (node.time == 23) {
                continue;
            }
            var n = new Node(node);
            if (n.canBuild(blueprint.ore) && n.robots[0] < maxOre) {
                var nn = new Node(n);
                nn.build(blueprint.ore);
                nn.tick();
                ++nn.robots[0];
                nodes.add(nn);
            }
            if (n.canBuild(blueprint.clay) && n.robots[1] < maxClay) {
                var nn = new Node(n);
                nn.build(blueprint.clay);
                nn.tick();
                ++nn.robots[1];
                nodes.add(nn);
            }
            if (n.canBuild(blueprint.geode)) {
                var nn = new Node(n);
                nn.build(blueprint.geode);
                nn.tick();
                ++nn.robots[3];
                nodes.add(nn);
            } else if (n.canBuild(blueprint.obsidian)) {
                var nn = new Node(n);
                nn.build(blueprint.obsidian);
                nn.tick();
                ++nn.robots[2];
                nodes.add(nn);
            }
            if (n.ore <= maxOre) {
                n.tick();
                nodes.add(n);
            }
        }
        return best;
    }

    private static class Node {
        int ore, clay, obsidian, geode, time;
        int[] robots = new int[4];

        public Node() {
            robots[0] = 1;
        }

        public Node(Node node) {
            this.ore = node.ore;
            this.clay = node.clay;
            this.obsidian = node.obsidian;
            this.geode = node.geode;
            this.time = node.time;
            System.arraycopy(node.robots, 0, robots, 0, robots.length);
        }

        public boolean canBuild(Robot robot) {
            return robot.ore <= ore && robot.clay <= clay && robot.obsidian <= obsidian;
        }

        public void tick() {
            ++time;
            ore += robots[0];
            clay += robots[1];
            obsidian += robots[2];
            geode += robots[3];
        }

        public void build(Robot robot) {
            ore -= robot.ore;
            clay -= robot.clay;
            obsidian -= robot.obsidian;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "ore=" + ore +
                    ", clay=" + clay +
                    ", obsidian=" + obsidian +
                    ", geode=" + geode +
                    ", time=" + time +
                    ", robots=" + Arrays.toString(robots) +
                    '}';
        }
    }

    private static class Blueprint {
        int id;
        Robot ore, clay, obsidian, geode;
    }

    private static class Robot {
        int ore, clay, obsidian;
    }
}
