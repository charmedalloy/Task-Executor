package edu.utdallas.blockingFIFO;

import edu.utdallas.taskExecutor.Task;

public class BlockingFIFOImpl implements BlockingFIFO {

	private int Max_Size;

	// Array of Tasks to be maintained in QUEUE
	private Task itemsInQueue[];
	private int nextIntoFIFO;
	private int nextOutFIFO;
	private int count;

	// Blocking Queue size is limited to 100
	private int MaxLimit = 100;

	// Monitor objects
	private Object notEmpty = new Object();
	private Object notFull = new Object();

	// initializing MaxSize of FIFOQueue
	public BlockingFIFOImpl() {
		this.Max_Size = MaxLimit;
		this.itemsInQueue = new Task[MaxLimit];
	}

	// Adding Task in Queue
	public void put(Task x) throws InterruptedException {
		while (true) {
			if (count == Max_Size) {
				synchronized (notFull) {
					notFull.wait();
				}
			}
			synchronized (this) {
				if (count == Max_Size) {
					continue;
				}
				itemsInQueue[nextIntoFIFO] = x;
				nextIntoFIFO = (nextIntoFIFO + 1) % Max_Size;
				count++;
				synchronized (notEmpty) {
					notEmpty.notify();
				}
				return;
			}

		}

	}

	// Remove Each task in Queue
	public Task take() throws InterruptedException {
		while (true) {
			if (count == 0) {
				synchronized (notEmpty) {
					notEmpty.wait();
				}
			}
			synchronized (this) {
				if (count == 0) {
					continue;
				}
				Task task = itemsInQueue[nextOutFIFO];
				nextOutFIFO = (nextOutFIFO + 1) % Max_Size;
				count--;
				synchronized (notFull) {
					notFull.notify();
				}
				return task;
			}
		}
	}
}
