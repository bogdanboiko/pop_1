public class MyThread extends Thread implements Breaker {
    private final int id;
    private volatile boolean canBreak = false;

    public MyThread(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        long sum = 0;
        do{
            sum++;
        } while (!canBreak);
        System.out.println(id + " - " + sum);
    }

    @Override
    public void terminateThread(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        canBreak = true;
    }
}
