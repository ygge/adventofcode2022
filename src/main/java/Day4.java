import util.Util;

import java.util.List;

public class Day4 {

    public static void main(String[] args) {
        var inputs = Util.readStrings();
        Util.submitPart1(part1(inputs));
        Util.submitPart2(part2(inputs));
    }

    private static int part2(List<String> inputs) {
        int count = 0;
        for (String input : inputs) {
            var split = input.split(",");
            var a = split[0].split("-");
            var b = split[1].split("-");
            int aa = Integer.parseInt(a[0]);
            int bb = Integer.parseInt(a[1]);
            int cc = Integer.parseInt(b[0]);
            int dd = Integer.parseInt(b[1]);
            if ((aa <= cc && bb >= cc) || (aa <= dd && bb >= dd)
                    || (cc <= aa && dd >= aa) || (cc <= bb && dd >= bb)) {
                ++count;
            }
        }
        return count;
    }

    private static int part1(List<String> inputs) {
        int count = 0;
        for (String input : inputs) {
            var split = input.split(",");
            var a = split[0].split("-");
            var b = split[1].split("-");
            int aa = Integer.parseInt(a[0]);
            int bb = Integer.parseInt(a[1]);
            int cc = Integer.parseInt(b[0]);
            int dd = Integer.parseInt(b[1]);
            if ((aa <= cc && bb >= dd) || (cc <= aa && dd >= bb)) {
                ++count;
            }
        }
        return count;
    }
}
