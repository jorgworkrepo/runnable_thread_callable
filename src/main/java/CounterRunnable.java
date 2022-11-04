import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CounterRunnable implements Runnable {

    private int threadNumber;

    public CounterRunnable(int threadNumber) {
        this.threadNumber = threadNumber;
    }

    public void counter() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            System.out.println("Count: " + i + " Thread: " + this.threadNumber);
            Thread.sleep(1000);
        }
    }

    @Override
    public void run() {
        try {
            counter();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }


    public static void main(String[] args) {

        for (int i = 0; i < 5; i++) {
            CounterRunnable counter = new CounterRunnable(i);
            Thread thread = new Thread(counter);
            thread.start();
        }

        ExecutorService es = Executors.newCachedThreadPool();

        for (int i = 0; i < 5; i++) {
            Runnable worker = new CounterRunnable(i);
            es.execute(worker);
        }
        es.shutdown();
    }
}