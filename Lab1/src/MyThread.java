public class MyThread extends Thread implements Breaker {
    private final int id;
    private final int step;
    private volatile boolean canBreak = false;

    public MyThread(int id, int step) {
        this.id = id;
        this.step = step;
    }

    @Override
    public void run() {
        long sum = 0;
        do{
            sum += step;
        } while (!canBreak);
        System.out.println(id + " - " + sum);
    }

    @Override
    public void terminateThread(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        canBreak = true;
    }
}
