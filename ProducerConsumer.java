public class ProducerConsumer{
    static int[] array;
    static int count;
    private static final Object lock = new Object();
    public static void main(String[] args) throws InterruptedException {
        array = new int [10];
        count = 0;
        Producer producer = new Producer();
        Consumer consumer = new Consumer();

        Runnable prodcerRunnable = () -> {
            for (int i = 0; i < 50; i++) {
                try {
                    producer.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable consumRunnable = () -> {
            for (int i = 0; i < 50; i++) {
                try {
                    consumer.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread producThread = new Thread(prodcerRunnable);
        Thread consumThread = new Thread(consumRunnable);

        producThread.join();
        consumThread.join();
        System.out.println("Thread data in buffer after execution: "+count);

    }

    static class Producer{
        public void produce() throws InterruptedException{
           synchronized(lock){
            if(isFull(array)){
                lock.wait();
            }
            array[count++] = 1;
            lock.notifyAll();
           }
        }
    }
    static class Consumer{
        public void consume() throws InterruptedException{
            synchronized(lock){
                if(isEmpty()){
                    lock.wait();
                }
            array[count--] = 0;
            lock.notifyAll();
            }
        }
    }

    public static boolean isFull(int[] buffer) {
        return count == buffer.length;
    }
    public static boolean isEmpty() {
        return count == 0;
    }
}