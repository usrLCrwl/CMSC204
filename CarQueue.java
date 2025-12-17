package threadLab;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CarQueue {
    private Queue<Integer> q;
    private Lock qLock;
    private Random rng;
    
    public CarQueue() {
        q = new LinkedList<>();
        qLock = new ReentrantLock();
        rng = new Random();

        for (int i = 0; i < 6; i++) {
            q.add(rng.nextInt(4));
        }
    }
    
    public void addToQueue() {
        class QueueRunnable implements Runnable {
            public void run() {
                try {
                    while (true) {
                        qLock.lock();
                        try {
                            q.add(rng.nextInt(4));
                        } finally {
                            qLock.unlock();
                        }
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {}
            }
        }

        Runnable r = new QueueRunnable();
        Thread t = new Thread(r);
        t.start();
    }
    
    public int deleteQueue() {
        qLock.lock();
        try {
            if (!q.isEmpty()) { return q.remove(); }
            else { return 2; }
        } finally { qLock.unlock(); }
    }
}
