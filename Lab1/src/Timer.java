public class Timer {
    private int secs;
    private final Breaker threadTerminator;

    public Timer(int secs, Breaker threadTerminator) {
        this.secs = secs;
        this.threadTerminator = threadTerminator;
    }

    public int getSecs() {
        return secs;
    }

    public Breaker getThreadTerminator() {
        return threadTerminator;
    }
}
