package Tack1;

public class TimeWorkThread implements Runnable {
    @Override
    public void run() {
    try {
        while (true) {
            Thread.sleep(5000);
            System.out.println("5 seconds have passed");
        }
    } catch (InterruptedException e) {
        throw new RuntimeException(e);
    }
    }
}

