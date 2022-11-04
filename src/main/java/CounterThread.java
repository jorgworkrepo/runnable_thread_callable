import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CounterThread extends Thread {

    private int threadNumber;

    public CounterThread(int threadNumber) {
        this.threadNumber = threadNumber;
    }

    public void counter() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            System.out.println("Count: " + i + " Thread: " + this.threadNumber);
            if(threadNumber == 1) {
                throw new RuntimeException();
            }
            Thread.sleep(1000);
        }
    }

    @Override
    public void run() {
        super.run();
        try {
            counter();
        } catch (InterruptedException e) {
        }
    }

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 5; i++) {
            CounterThread counter = new CounterThread(i);
            counter.start();

            if(!counter.isAlive()){
                counter.join();
            }
        }

        ExecutorService es = Executors.newCachedThreadPool();

        for (int i = 0; i < 5; i++) {
            Runnable worker = new CounterThread(i);
            es.execute(worker);
        }
        es.shutdown();
    }

}
