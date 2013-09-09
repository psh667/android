public class ThreadDemoA1 {
    public static void main(String[] args) {
        MyThread t1 = new MyThread("thread1", 1000);
        MyThread t2 = new MyThread("thread2", 1000);
        MyThread t3 = new MyThread("thread3", 1000);

        t1.start();
        t2.start();
        t3.start();
    }
}

class MyThread extends Thread {
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