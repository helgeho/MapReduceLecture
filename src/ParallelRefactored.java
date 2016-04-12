import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParallelRefactored {
    private static List<String> numbers = Arrays.asList("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine");

    private static int expensiveParsing(String number) {
        return numbers.indexOf(number);
    }

    private static class MapFunction implements Runnable {
        private String in;
        int out = -1;

        MapFunction(String in) {
            this.in = in;
        }

        @Override
        public void run() {
            out = expensiveParsing(in);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String[] numbers = new String[] {"two", "seven", "one", "five"};
        List<MapFunction> mapped = mapExpensiveParsing(numbers);
        int result = reduce(mapped);
        System.out.println(result);
    }

    private static List<MapFunction> mapExpensiveParsing(String[] numbers) throws InterruptedException {
        List<MapFunction> mapTasks = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();
        for (String number : numbers) {
            MapFunction map = new MapFunction(number);
            Thread thread = new Thread(map);
            thread.start();
            mapTasks.add(map);
            threads.add(thread);
        }
        for (Thread thread : threads) {
            thread.join();
        }
        return mapTasks;
    }

    private static int reduce(List<MapFunction> mapTasks) {
        int result = 0;
        for (MapFunction map : mapTasks) {
            result += map.out;
        }
        return result;
    }
}
