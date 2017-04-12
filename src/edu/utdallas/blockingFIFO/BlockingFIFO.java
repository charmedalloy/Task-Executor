package edu.utdallas.blockingFIFO;

import edu.utdallas.taskExecutor.Task;

public interface BlockingFIFO {

	public void put(Task task) throws Exception;

	public Task take() throws Exception;

}
