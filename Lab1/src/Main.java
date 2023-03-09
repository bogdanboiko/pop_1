import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ArrayList<Timer> timerList = new ArrayList<>();

        int quantity = 4;
        ThreadManager manager = new ThreadManager(quantity);

        for(int i = 0; i < quantity; i++) {
            timerList.add(new Timer((int) Math.round(Math.random() * 20),  manager.getThreadList().get(i)));
        }

        manager.startThreads();

        BreakThread breakThread = new BreakThread(timerList);

        new Thread(breakThread).start();
    }
}
