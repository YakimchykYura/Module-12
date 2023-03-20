package Tack1;

public class Main {
    public static void main(String[] args) {
        Runnable object = new TimeWorkThread();
        Thread thread = new Thread(object);
        thread.start();
        int count = 0;

        try {
            while (true) {
                Thread.sleep(1000);
                count++;
                System.out.println(count + " seconds passed");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}