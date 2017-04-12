package edu.utdallas.taskExecutorImpl;

import java.util.ArrayList;
import java.util.List;

import edu.utdallas.blockingFIFO.BlockingFIFO;
import edu.utdallas.blockingFIFO.BlockingFIFOImpl;
import edu.utdallas.taskExecutor.Task;
import edu.utdallas.taskExecutor.TaskExecutor;

public class TaskExecutorImpl implements TaskExecutor {

	List<TaskRunner> runnerPool;
	BlockingFIFO blockingQueue;

	public TaskExecutorImpl(int numOfThreads) {
		runnerPool = new ArrayList<>();
		blockingQueue = new BlockingFIFOImpl();
		runThreads(numOfThreads);

	}

	@Override
	public void addTask(Task task) {
		try {
			blockingQueue.put(task);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void runThreads(int totalThreads) {
		for (int i = 0; i < totalThreads; i++) {
			runnerPool.add(new TaskRunner(blockingQueue));
		}

		for (int i = 0; i < runnerPool.size(); i++) {
			TaskRunner tr=runnerPool.get(i);
			Thread th = new Thread(tr);
			th.start();

		}
	}
}
