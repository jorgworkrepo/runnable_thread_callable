import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class MyCallable implements Callable {

    public List<String> counter() {
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            stringList.add("Count: " + i + " Thread: " + Thread.currentThread().getName());
        }
        return stringList;
    }

    @Override
    public Object call() throws Exception {
        return counter();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // create a thread pool
        ExecutorService es = Executors.newFixedThreadPool(5);

        // create a list of Future<List<String>>
        List<Future<List<String>>> list = new ArrayList<>();

        Callable callable = new MyCallable();

        //submit tasks to be executed by thread pool
        for (int i = 0; i < 50; i++) {
            Future<List<String>> future = es.submit( () -> new MyCallable().counter());
            list.add(future);
        }

        // print the return value for each future
        for (Future<List<String>> fs : list) {
            System.out.println(new Date() + "::" + fs.get());
        }

        es.shutdown();
    }
}
