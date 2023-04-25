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
                manager.getProducerAccess();
                manager.storage.add("item " + i);
                System.out.println("Producer " + producerId +" Added item " + i);

                manager.releaseProducer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}