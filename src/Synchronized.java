import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Synchronized {
    private static List<String> numbers = Arrays.asList("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine");

    private static int expensiveParsing(String number) {
        return numbers.indexOf(number);
    }

    private static int result = 0;

    private static synchronized void addResult(String value) {
        result += expensiveParsing(value);
    }

    public static void main(String[] args) throws InterruptedException {
        String[] numbers = new String[] {"two", "seven", "one", "five"};
        List<Thread> threads = new ArrayList<>();
        for (String number : numbers) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    addResult(number);
                }
            });
            thread.start();
            threads.add(thread);
        }
        for (Thread thread : threads) {
            thread.join();
        }
        System.out.println(result);
    }
}
