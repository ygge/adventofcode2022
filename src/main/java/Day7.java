import util.Util;

import java.util.*;

public class Day7 {

    public static void main(String[] args) {
        var inputs = Util.readStrings();
        Util.submitPart1(part1(inputs));
        Util.submitPart2(part2(inputs));
    }

    private static int part2(List<String> input) {
        Path root = new Path("/");
        parsePath(root, input, 1);

        List<Path> paths = new ArrayList<>();
        findPaths2(root, paths);
        paths.sort(Comparator.comparingInt(a -> a.totalSize));
        int req = 30000000 - (70000000 - root.totalSize);
        for (Path path : paths) {
            if (path.totalSize >= req) {
                return path.totalSize;
            }
        }
        throw new IllegalStateException();
    }

    private static int part1(List<String> input) {
        Path root = new Path("/");
        parsePath(root, input, 1);

        List<Path> paths = new ArrayList<>();
        findPaths(root, paths);
        return paths.stream()
                .map(p -> p.totalSize)
                .reduce(0, Integer::sum);
    }

    private static void findPaths2(Path path, List<Path> paths) {
        for (Path dir : path.dirs.values()) {
            findPaths2(dir, paths);
            path.totalSize += dir.totalSize;
        }
        for (Integer fileSize : path.files.values()) {
            path.totalSize += fileSize;
        }
        paths.add(path);
    }

    private static void findPaths(Path path, List<Path> paths) {
        for (Path dir : path.dirs.values()) {
            findPaths(dir, paths);
            path.totalSize += dir.totalSize;
        }
        for (Integer fileSize : path.files.values()) {
            path.totalSize += fileSize;
        }
        if (path.totalSize <= 100000) {
            paths.add(path);
        }
    }

    private static int parsePath(Path path, List<String> input, int index) {
        if (!Objects.equals(input.get(index), "$ ls")) {
            throw new RuntimeException(input.get(index));
        }
        ++index;
        for (; index < input.size(); ++index) {
            var cmd = input.get(index);
            if (Objects.equals(cmd, "$ cd ..")) {
                return index;
            }
            if (cmd.startsWith("$ cd ")) {
                var p = cmd.substring(5);
                index = parsePath(path.dirs.get(p), input, index + 1);
            } else if (cmd.startsWith("dir ")) {
                var d = cmd.substring(4);
                path.dirs.put(d, new Path(d));
            } else {
                var split = cmd.split(" ");
                path.files.put(split[1], Integer.parseInt(split[0]));
            }
        }
        return index;
    }

    private static class Path {
        private String name;
        private Map<String, Path> dirs = new HashMap<>();
        private Map<String, Integer> files = new HashMap<>();
        private int totalSize;

        private Path(String name) {
            this.name = name;
        }
    }
}
