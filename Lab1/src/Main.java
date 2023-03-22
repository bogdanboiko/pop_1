import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ArrayList<Timer> timerList = new ArrayList<>();

        int quantity = 4;
        int[] steps = {1, 4, 5, 1};
        ThreadManager manager = new ThreadManager(quantity, steps);

        double[] duration = {1.0, 5.0, 2.0, 6.0};

        for(int i = 0; i < quantity; i++) {
            timerList.add(new Timer((int) Math.round(duration[i]),  manager.getThreadList().get(i)));
        }

        manager.startThreads();

        BreakThread breakThread = new BreakThread(timerList);

        new Thread(breakThread).start();
    }
}
