import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import strategii.Scheduler;
import strategii.SelectionPolicy;
import strategii.Server;
import strategii.Task;

import java.util.Arrays;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationManager implements Runnable {

	 public int numberOfClients;
	    public int numberOfServers;
	    public int timeLimit;
	    public int minArrivalTime;
	    public int maxArrivalTime;
	    public int minServiceTime;
	    public int maxServiceTime;
	    private int totalNumberOfClients;

    public SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME;

    private Scheduler scheduler;
    private SimulationFrame frame;
    private List<Task> generatedTasks;

    public SimulationManager(SimulationFrame frame) {
        this.frame = frame;
        this.numberOfClients = frame.getNumberOfClients();
        this.numberOfServers = frame.getNumberOfServers();
        this.timeLimit = frame.getTimeLimit();
        this.minArrivalTime = frame.getMinArrivalTime();
        this.maxArrivalTime = frame.getMaxArrivalTime();
        this.minServiceTime = frame.getMinServiceTime();
        this.maxServiceTime = frame.getMaxServiceTime();
        this.scheduler = new Scheduler(numberOfServers, numberOfClients);
        this.scheduler.changeStrategy(selectionPolicy);
        this.generatedTasks = new ArrayList<>();
        this.totalNumberOfClients = this.numberOfClients;
        generateNRandomTasks();
    }

    private void generateNRandomTasks() {
        Random rand = new Random();
        for (int i = 0; i < numberOfClients; i++) {
            int processingTime = rand.nextInt(maxServiceTime - minServiceTime + 1) + minServiceTime;
            int arrivalTime = rand.nextInt(maxArrivalTime - minArrivalTime + 1) + minArrivalTime;
            generatedTasks.add(new Task(i, arrivalTime, processingTime));
        }
        generatedTasks = generatedTasks.stream()
                .sorted((t1, t2) -> Integer.compare(t1.getArrivalTime(), t2.getArrivalTime()))
                .collect(Collectors.toList());
    }


    public void run() {
        int currentTime = 0;
        int totalWaitingTime = 0;
        int totalServiceTime = 0;
        int maxTasksInQueues = 0;
        int peakHour = 0;

        while (currentTime < timeLimit) {
            for (Task t : new ArrayList<>(generatedTasks)) {
                if (t.getArrivalTime() == currentTime) {
                    scheduler.dispatchTask(t);
                    generatedTasks.remove(t);
                }
            }

            frame.appendLog("Time " + currentTime);
            frame.appendLog("Waiting clients: " + generatedTasks);
            boolean allServersEmpty = true;
            for (int i = 0; i < numberOfServers; i++) {
                Server server = scheduler.getServers().get(i);
                int numTasksInQueue = server.getTasks().length;
                if (numTasksInQueue > 0) {
                    allServersEmpty = false;
                }
                if (numTasksInQueue > maxTasksInQueues) {
                    maxTasksInQueues = numTasksInQueue;
                    peakHour = currentTime;
                }
                frame.appendLog("Queue " + (i + 1) + ": " + numTasksInQueue + ":" + Arrays.toString(server.getTasks()));

                Task currentTask = server.getCurrentTask();
                if (currentTask != null) {
                    totalServiceTime += (currentTask.getServiceTime() - currentTask.getRemainingServiceTime());
                    totalWaitingTime += currentTime - currentTask.getArrivalTime() - (currentTask.getServiceTime() - currentTask.getRemainingServiceTime());
                }
            }
            if (allServersEmpty && generatedTasks.isEmpty()) {
                break;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            currentTime++;
        }

        double avgWaitingTime = (double) totalWaitingTime / totalNumberOfClients; // Change this line
        double avgServiceTime = (double) totalServiceTime / totalNumberOfClients; // Change this line

        System.out.println("Total waiting time: " + totalWaitingTime);
        System.out.println("Total service time: " + totalServiceTime);
        System.out.println("Average waiting time: " + avgWaitingTime);
        System.out.println("Average service time: " + avgServiceTime);
        System.out.println("Peak hour: " + peakHour);
        System.out.println("Maximum tasks in queues: " + maxTasksInQueues);
    }


    public static void main(String[] args) {
        SimulationFrame frame = new SimulationFrame();
        frame.setStartButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimulationManager simManager = new SimulationManager(frame);
                Thread t = new Thread(simManager);
                t.start();
            }
        });
    }
}

