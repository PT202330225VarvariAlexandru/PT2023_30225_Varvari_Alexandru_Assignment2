package strategii;


import java.util.List;
import java.util.ArrayList;

public class Scheduler {

    private List<Server> servers;
    private int maxNoServers;
    private int maxTasksPerServer;
    private Strategy strategy;

    public Scheduler(int maxNoServers, int maxTasksPerServer) {
        this.maxNoServers = maxNoServers;
        this.maxTasksPerServer = maxTasksPerServer;
        this.servers = new ArrayList<>();
//create thread with the object
        for (int i = 0; i < maxNoServers; i++) {
            Server server = new Server(); 
            servers.add(server);
            Thread serverThread = new Thread(server);
            serverThread.start();
        }
    }

    public void changeStrategy(SelectionPolicy policy) {
        if (policy == SelectionPolicy.SHORTEST_QUEUE) {
            strategy = new ConcreteStrategyQueue();
        }
        if (policy == SelectionPolicy.SHORTEST_TIME) {
            strategy = new ConcreteStrategyTime();
        }
    }

    public void dispatchTask(Task t) {
    	//call the strategy addTask method
        strategy.addTask(servers, t);
    }

    public List<Server> getServers() {
        return servers;
    }
}