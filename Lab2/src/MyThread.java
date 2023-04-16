public class MyThread extends Thread {
    private final int start;
    private final int end;
    private final int[] array;
    private final CounterCallback minElementCallback;

    public MyThread(int[] array, int start, int end, CounterCallback minElementCallback) {
        this.start = start;
        this.end = end;
        this.minElementCallback = minElementCallback;
        this.array = array;
    }

    @Override
    public void run() {
        double minValue = array[start];
        int minIndex = start;

        for(int i = start; i < end; i++) {
            if (array[i] < minValue) {
                minValue = array[i];
                minIndex = i;
            }
        }

        minElementCallback.addMinValue(minValue, minIndex);
    }
}
