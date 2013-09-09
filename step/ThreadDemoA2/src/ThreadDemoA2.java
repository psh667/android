public class ThreadDemoA2 {
    public static void main(String[] args) {
        Thread t1 = new Thread(new MyThread("thread1", 1000));
        Thread t2 = new Thread(new MyThread("thread2", 1000));
        Thread t3 = new Thread(new MyThread("thread3", 1000));

        t1.start();
        t2.start();
        t3.start();
    }
}

class MyThread implements Runnable {
    private String name;
    private int max;    

    public MyThread(String name, int max) {
        this.name = name;
        this.max = max;
    }

    public void run() {
        for (int i = 0; i < max; i++)
            System.out.println(name + " : " + i);
    }
}