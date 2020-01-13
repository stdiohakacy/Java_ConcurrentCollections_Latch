package _11;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Worker implements Runnable {
	private int id;
	private CountDownLatch count;

	public Worker(int id, CountDownLatch count) {
		this.id = id;
		this.count = count;
	}

	@Override
	public void run() {
		doSomeThing();
		count.countDown();
	}

	private void doSomeThing() {
		System.out.println("Thread with id " + this.id + " starts working ...");
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
		}
	}

}

public class App {
	public static void main(String[] args) {
		ExecutorService service = Executors.newSingleThreadExecutor();

		CountDownLatch latch = new CountDownLatch(5);
		for (int i = 0; i < 5; i++) {
			service.execute(new Worker(i, latch));
		}

		try {
			latch.await();
		} catch (Exception e) {
			// TODO: handle exception
		}

		service.shutdown();
	}
}
