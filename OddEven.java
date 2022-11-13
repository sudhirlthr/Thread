public class OddEven {
    private static int number;
    private static final Object lock = new Object();
    public static void main(String[] args) throws InterruptedException {
        number = 1;
        Runnable r1 = () -> {
            for (int i = 0; number < 100; i++) {
                printOdd();   
            }
        };

        Runnable r2 = () -> {
            for (int i = 0; number < 100; i++) {
                printEven();
            }
        };

        Thread t1 = new Thread(r1);
        t1.setName("Thread 1");
        Thread t2 = new Thread(r2);
        t2.setName("Thread 2");
        t1.start();
        t2.start();

        System.out.println("Done procesing...");
        t1.join();
        t2.join();
    }
    private static void printEven() {
        synchronized(lock){
            if(!isEven(number)){
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName()+": "+number++);
            lock.notifyAll();
        }
    }
    private static void printOdd() {
        synchronized(lock){
            if(isEven(number)){
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName()+": "+number++);
            lock.notifyAll();
        }
    }
    private static boolean isEven(int number2) {
        return number2 % 2 == 0;
    }
}
