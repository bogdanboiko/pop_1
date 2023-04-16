public class Main {
    public static void main(String[] args) {
        int storageSize = 8;
        int itemNumbers = 30;

        int producerQuantity = 6;
        int consumerQuantity = 2;

        Manager manager = new Manager(storageSize);


        int producerStep = itemNumbers / producerQuantity;
        int producerIdCounter = 1;

        for (int i = 0; i < itemNumbers; i += producerStep) {
            if (i + producerStep > itemNumbers) {
                new Producer(producerIdCounter, itemNumbers - i, manager);
            } else {
                new Producer(producerIdCounter, producerStep, manager);
            }
            producerIdCounter++;
        }

        int consumerStep = itemNumbers / consumerQuantity;
        int consumerIdCounter = 1;

        for (int i = 0; i < itemNumbers; i += consumerStep) {
            if (i + consumerStep > itemNumbers) {
                new Consumer(consumerIdCounter, itemNumbers - i, manager);
            } else {
                new Consumer(consumerIdCounter, consumerStep, manager);
            }
            consumerIdCounter++;
        }
    }
}
