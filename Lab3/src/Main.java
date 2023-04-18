public class Main {
    public static void main(String[] args) {
        int storageSize = 3;
        int itemNumbers = 10;

        int producerQuantity = 7;
        int consumerQuantity = 5;

        Manager manager = new Manager(storageSize);

        int producerStep = itemNumbers / producerQuantity;
        int producerIdCounter = 1;


        System.out.println(producerStep);

        for (int i = 0; i < producerQuantity; i++) {
            if (i == producerQuantity - 1 && itemNumbers % producerQuantity > 0) {
                new Producer(producerIdCounter, itemNumbers - i * producerStep, manager);
            } else {
                new Producer(producerIdCounter, producerStep, manager);
            }
            producerIdCounter++;
        }

        int consumerStep = itemNumbers / consumerQuantity;
        int consumerIdCounter = 1;

        System.out.println(consumerStep);

        for (int i = 0; i < consumerQuantity; i++) {
            if (i == consumerQuantity - 1 && itemNumbers % consumerQuantity > 0) {
                new Consumer(consumerIdCounter, itemNumbers - i * consumerStep, manager);
            } else {
                new Consumer(consumerIdCounter, consumerStep, manager);
            }
            consumerIdCounter++;
        }
    }
}
