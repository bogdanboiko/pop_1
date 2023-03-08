import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        MyThread myThread1 = new MyThread(1);
        MyThread myThread2 = new MyThread(2);
        MyThread myThread3 = new MyThread(3);

        myThread1.start();
        myThread2.start();
        myThread3.start();

        ArrayList<Timer> timerList = new ArrayList<>();

        timerList.add(new Timer(10, myThread1));
        timerList.add(new Timer(8, myThread2));
        timerList.add(new Timer(5, myThread3));

        BreakThread breakThread = new BreakThread(timerList);

        new Thread(breakThread).start();
    }
}
