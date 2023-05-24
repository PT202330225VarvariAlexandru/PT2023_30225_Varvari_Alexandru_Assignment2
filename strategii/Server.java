	package strategii;
	
	import java.util.concurrent.BlockingQueue;
	import java.util.concurrent.LinkedBlockingQueue;
	import java.util.concurrent.atomic.AtomicInteger;
	
	public class Server implements Runnable {
	
	    private BlockingQueue<Task> tasks;
	    private AtomicInteger waitingPeriod;
	
	    public Server() {
	        tasks = new LinkedBlockingQueue<Task>();
	        waitingPeriod = new AtomicInteger(0);
	    }
	
	    public void addTask(Task newTask) {
	        synchronized (waitingPeriod) {
	            try {
	                tasks.put(newTask);
	                waitingPeriod.addAndGet(newTask.getServiceTime());
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	
	    public void removeCurrentTask() {
	        synchronized (waitingPeriod) {
	            Task currentTask = tasks.poll();
	            if (currentTask != null) {
	                waitingPeriod.addAndGet(-currentTask.getServiceTime());
	            }
	        }
	    }
	
	    public Task getCurrentTask() {
	        return tasks.peek();
	    }
	    @Override
	    public void run() {
	        while (true) {
	            try {
	                Task task = tasks.peek();
	                if (task != null) {
	                    Thread.sleep(1000);
	                    task.decrementRemainingServiceTime();
	                    synchronized (waitingPeriod) {
	                        waitingPeriod.decrementAndGet();
	                    }
	                    if (task.getRemainingServiceTime() == 0) {
	                        tasks.poll();
	                    }
	                } else {
	                    Thread.sleep(1000);  // sleep if there are no tasks
	                }
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	
	    public Task[] getTasks() {
	        return tasks.toArray(new Task[0]);
	    }
	
	    public AtomicInteger getWaitingPeriod() {
	        return waitingPeriod;
	    }
	    
	    
	}
