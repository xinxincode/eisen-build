package org.eisen.dal.demo;

/**
 * @Author Eisen
 * @Date 2018/12/26 15:41
 * @Description:
 **/
public class DemoThread implements Runnable{
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            System.out.println(1);
        }
    }


    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new DemoThread());
        t1.start();
        Thread.sleep(10000);
        t1.interrupt();

    }

}
