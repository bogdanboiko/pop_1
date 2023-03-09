import java.util.ArrayList;
import java.util.List;

public class ThreadManager {
    private int threadQuantity;
    private List<MyThread> threadList;

    public ThreadManager(int threadQuantity) {
        this.threadQuantity = threadQuantity;
        ArrayList<MyThread> threadList = new ArrayList<>();

        for(int i = 0; i < threadQuantity; i++) {
            threadList.add(new MyThread(i));
        }

        this.threadList = threadList;
    }

    public void startThreads() {
        threadList.forEach(Thread::start);
    }

    public List<MyThread> getThreadList() {
        return threadList;
    }
}
