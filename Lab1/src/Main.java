import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ArrayList<Timer> timerList = new ArrayList<>();

        int quantity = 2;
        int[] steps = {1, 1, 1, 1, 1, 1};
        ThreadManager manager = new ThreadManager(quantity, steps);

        double[] duration = {5.0, 6.0, 4.0, 8.0, 10.0, 2.0};

        for(int i = 0; i < quantity; i++) {
            timerList.add(new Timer((int) Math.round(duration[i]),  manager.getThreadList().get(i)));
        }

        manager.startThreads();

        BreakThread breakThread = new BreakThread(timerList);

        new Thread(breakThread).start();
    }
}
