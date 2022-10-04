import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Mihran Galstyan
 * All rights reserved
 */
public class Main {
    public static void main(final String[] args) {
        final int MILLION = 1_000_000;
        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        ExecutorService executorService = Executors.newFixedThreadPool(3);

        CountDownLatch countDownLatch = new CountDownLatch(3);
        long before = System.currentTimeMillis();

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                long sum = 0;
                for (int i = 0; i <= MILLION; i++) {
                    if (i % 2 == 0) {
                        sum += i;
                    }
                }
                System.out.println("Sum of even numbers from 0 to 1 000 000: " + sum);
                countDownLatch.countDown();
            }
        });

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                long sum = 0;
                for (int i = 0; i <= MILLION; i++) {
                    if (i % 7 == 0) {
                        sum += i;
                    }
                }
                System.out.println("Sum of 7 div numbers from 0 to 1 000 000: " + sum);
                countDownLatch.countDown();
            }
        });

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                List<Integer> numbers = new ArrayList<>();
                for (int i = 0; i < MILLION / 1000; i++) {
                    numbers.add((int) (Math.random() * 1000));
                }
                long sum = 0;
                for (final Integer number : numbers) {
                    if (number % 2 == 0) {
                        sum += number;
                    }
                }
                System.out.println("Sum of even numbers in collection: " + sum);
                countDownLatch.countDown();
            }
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        executorService.shutdown();
        long after = System.currentTimeMillis();
        System.out.printf("Finished in %s milliseconds", (after - before));
    }
}
