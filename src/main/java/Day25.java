import util.Util;

import java.util.List;

public class Day25 {

    public static void main(String[] args) {
        var input = Util.readStrings();
        Util.submitPart1(part1(input));
        Util.submitPart2(0);
    }

    private static String part1(List<String> input) {
        long num = 0;
        for (String s : input) {
            num += convert(s);
        }
        String ans = convert(num);
        if (convert(ans) != num) {
            throw new IllegalStateException(num + " " + ans);
        }
        return ans;
    }

    private static String convert(long num) {
        var sb = new StringBuilder();
        int plus = 0;
        while (true) {
            var v = num % 5 + plus;
            num /= 5;
            if (v < 3) {
                sb.append((char)('0' + v));
                plus = 0;
            } else if (v == 3) {
                sb.append('=');
                plus = 1;
            } else if (v == 4) {
                sb.append('-');
                plus = 1;
            } else {
                sb.append('0');
                plus = 1;
            }
            if (num == 0 && plus == 0) {
                return sb.reverse()
                        .toString();
            }
        }
    }

    private static long convert(String s) {
        long n = 0;
        long v = 1;
        for (int i = s.length()-1; i >= 0; --i) {
            long vv = switch(s.charAt(i)) {
                case '0', '1', '2' -> s.charAt(i)-'0';
                case '-' -> -1;
                case '=' -> -2;
                default -> throw new IllegalStateException();
            };
            n += v * vv;
            v *= 5;
        }
        return n;
    }
}
