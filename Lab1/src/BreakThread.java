import java.util.ArrayList;
import java.util.Comparator;

public class BreakThread implements Runnable {
    private final ArrayList<Timer> threadTimers;

    public BreakThread(ArrayList<Timer> threadTimers) {
        threadTimers.sort(Comparator.comparingInt(Timer::getSecs));
        this.threadTimers = threadTimers;
    }
    @Override
    public void run() {
        if (threadTimers.size() > 0) {
            Timer prevTimer = threadTimers.get(0);
            int prevTime = prevTimer.getSecs();
            prevTimer.getThreadTerminator().terminateThread(prevTime);
            int skipTime = prevTime;

            for (int i = 1; i < threadTimers.size(); i++) {
                Timer curTimer = threadTimers.get(i);
                int curTime = curTimer.getSecs();
                int newTime = curTime - skipTime;
                curTimer.getThreadTerminator().terminateThread(newTime);
                skipTime += newTime;
            }
        }
    }
}
