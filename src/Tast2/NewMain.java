package Tast2;

import java.util.concurrent.ConcurrentLinkedQueue;

public class NewMain {
    static int current = 1;
    static int max = 30;
    static private ConcurrentLinkedQueue<String> result = new ConcurrentLinkedQueue<>();
    static boolean stop = false;


    static Thread threadFizz = new Thread(() -> fizz());
    static public void fizz() {
        while (!stop) {
            waitThread(threadFizz);
            if (current % 3 == 0) {
                shouldStop();
                result.add("fizz");
                continue;
            }
            hasNext = false;
        }
    }
    static Thread threadBuzz = new Thread(() -> buzz());
    static public void buzz() {
        while (!stop) {
            waitThread(threadBuzz);
            if (current % 5 == 0) {
                shouldStop();
                result.add("buzz");
                continue;
            }
            hasNext = false;
        }
    }
    static Thread threadFizzBuzz = new Thread(() -> fizzbuzz());
    static public void fizzbuzz() {
        while (!stop) {
            waitThread(threadFizzBuzz);
            if (current % 3 == 0 && current % 5 == 0) {
                shouldStop();
                result.add("fizzbuzz");
                continue;
            }
            hasNext = false;
        }
    }

    private static void waitThread(Thread thread) {
        try {
            synchronized (thread) {
                thread.wait();
            }
        } catch (InterruptedException e) {
            stop = true;
        }
    }

    static Thread ThreadNumber = new Thread(() -> number());
    static int counter = 0;
    static boolean hasNext = false;
    static public void number() {
        while (!stop) {
            var next = result.poll();

            if (next == null) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    stop = true;
                }
                if (hasNext) {
                    continue;
                }
                if (counter == 0) {
                    synchronized (threadFizzBuzz) {
                        threadFizzBuzz.notify();
                    }
                    hasNext = true;
                } else if (counter == 1) {
                    synchronized (threadFizz) {
                        threadFizz.notify();
                    }
                    hasNext = true;
                } else if (counter == 2) {
                    synchronized (threadBuzz) {
                        threadBuzz.notify();
                    }
                    hasNext = true;
                } else {
                    result.add(Integer.toString(current));
                    shouldStop();
                    hasNext = false;
                }
                counter++;
                continue;
            }
            hasNext = false;
            counter = 0;
            System.out.print(next + ", ");
        }
    }
    static public void shouldStop() {
        if (current > max) {
            stop = true;
            synchronized (threadFizzBuzz) {
                threadFizzBuzz.notify();
            }
            synchronized (threadFizz) {
                threadFizz.notify();
            }
            synchronized (threadBuzz) {
                threadBuzz.notify();
            }
        }
        current++;
    }

    public static void main(String[] args) {
        ThreadNumber.start();
        threadFizzBuzz.start();
        threadBuzz.start();
        threadFizz.start();
    }
}
