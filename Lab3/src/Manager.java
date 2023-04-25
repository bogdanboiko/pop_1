import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Manager {
    private Semaphore access;
    private Semaphore full;
    private Semaphore empty;

    public ArrayList<String> storage = new ArrayList<>();

    public Manager(int storageSize) {
        access = new Semaphore(1);
        full = new Semaphore(storageSize);
        empty = new Semaphore(0);
    }

    public void getConsumerAccess() throws InterruptedException {
        empty.acquire();
        Thread.sleep(1000);
        access.acquire();
    }

    public void getProducerAccess() throws InterruptedException {
        full.acquire();
        access.acquire();
    }

    public void releaseProducer() {
        access.release();
        empty.release();
    }

    public void releaseConsumer() {
        access.release();
        full.release();
    }
}
