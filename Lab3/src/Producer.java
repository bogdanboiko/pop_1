public class Producer implements Runnable {
    private final int producerId;
    private final int itemNumbers;
    private final Manager manager;

    public Producer(int producerId, int itemNumbers, Manager manager) {
        this.itemNumbers = itemNumbers;
        this.manager = manager;
        this.producerId = producerId;

        new Thread(this).start();
    }

    @Override
    public void run() {
        for (int i = 0; i < itemNumbers; i++) {
            try {
                manager.full.acquire();
                manager.access.acquire();

                manager.storage.add("item " + i);
                System.out.println("Producer " + producerId +" Added item " + i);

                manager.access.release();
                manager.empty.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}