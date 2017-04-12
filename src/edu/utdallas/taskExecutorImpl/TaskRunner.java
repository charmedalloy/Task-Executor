package edu.utdallas.taskExecutorImpl;

import edu.utdallas.blockingFIFO.BlockingFIFO;
import edu.utdallas.taskExecutor.Task;

public class TaskRunner implements Runnable {

	BlockingFIFO blockingQueue;

	public TaskRunner(BlockingFIFO blockingQueue) {
		this.blockingQueue = blockingQueue;
	}

	@Override
	public void run() {

		while (true) {

			try {
				Task newTask = blockingQueue.take();
				newTask.execute();

			} catch (Exception e) {
				System.out.println("Error in Task Runner " + e);
			}
		}

	}

}
