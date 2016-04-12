import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parallel {
    private static List<String> numbers = Arrays.asList("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine");

    private static int expensiveParsing(String number) {
        return numbers.indexOf(number);
    }

    private static class ExpensiveParsing implements Runnable {
        private String number;
        int result = -1;

        ExpensiveParsing(String number) {
            this.number = number;
        }

        @Override
        public void run() {
            result = expensiveParsing(number);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String[] numbers = new String[] {"two", "seven", "one", "five"};
        List<ExpensiveParsing> tasks = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();
        for (String number : numbers) {
            ExpensiveParsing task = new ExpensiveParsing(number);
            Thread thread = new Thread(task);
            thread.start();
            threads.add(thread);
            tasks.add(task);
        }
        int result = 0;
        for (int i = 0; i < threads.size(); i++) {
            threads.get(i).join();
            result += tasks.get(i).result;
        }
        System.out.println(result);
    }

}
