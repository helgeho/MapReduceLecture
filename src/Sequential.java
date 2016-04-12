import java.util.Arrays;
import java.util.List;

public class Sequential {
    private static List<String> numbers = Arrays.asList("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine");

    private static int expensiveParsing(String number) {
        return numbers.indexOf(number);
    }

    public static void main(String[] args) {
        String[] numbers = new String[] {"two", "seven", "one", "five"};
        int result = 0;
        for (String number : numbers) {
            result += expensiveParsing(number);
        }
        System.out.println(result);
    }

}
