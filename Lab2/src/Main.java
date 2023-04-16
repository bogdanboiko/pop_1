import java.util.*;

public class Main {

    public static void main(String[] args) {
        HashMap<Integer, Double> minList = new HashMap<>();
        CounterCallback callback = new CounterCallback() {
            @Override
            public synchronized void addMinValue(double minValue, int index) {
                minList.put(index, minValue);
            }
        };

        int[] array = generateArray(108000);
        int threadQuantity = 3;
        int step = array.length / threadQuantity;
        ArrayList<MyThread> threadList = new ArrayList<>();

        for (int i = 0; i < array.length; i += step) {
            MyThread thread;
            if ((i + step) < array.length) {
                thread = new MyThread(array, i, i + step, callback);
            } else {
                thread = new MyThread(array, i, array.length, callback);
            }

            threadList.add(thread);
        }

        threadList.forEach(Thread::start);

        threadList.forEach(myThread -> {
            try {
                myThread.join();
            } catch (InterruptedException e) {
                System.out.println(e.toString());
            }
        });

        System.out.println(minList.entrySet().stream().min(Map.Entry.comparingByValue()));
    }

    public static int[] generateArray(int size) {
        int[] arr = new int[size];
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(200);
        }

        int negIndex = rand.nextInt(size);
        arr[negIndex] = arr[negIndex] * -1;
        return arr;
    }
}
