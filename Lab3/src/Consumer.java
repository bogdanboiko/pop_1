public class Consumer implements Runnable {
    private final int consumerId;
    private final int itemNumbers;
    private final Manager manager;

    public Consumer(int consumerId, int itemNumbers, Manager manager) {
        this.itemNumbers = itemNumbers;
        this.manager = manager;
        this.consumerId = consumerId;

        new Thread(this).start();
    }

    @Override
    public void run() {
        for (int i = 0; i < itemNumbers; i++) {
            String item;
            try {
                manager.getConsumerAccess();

                item = manager.storage.get(0);
                manager.storage.remove(0);
                System.out.println("Consumer " + consumerId + " took " + item);

                manager.releaseConsumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}