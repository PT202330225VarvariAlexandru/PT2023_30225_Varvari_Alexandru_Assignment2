package strategii;


import java.util.List;
import java.util.Optional;

class ConcreteStrategyQueue implements Strategy {
    @Override
    public void addTask(List<Server> servers, Task t) {
        Optional<Server> serverWithShortestQueue = servers.stream()
                .min((s1, s2) -> Integer.compare(s1.getTasks().length, s2.getTasks().length));

        serverWithShortestQueue.ifPresent(server -> server.addTask(t));
    }
}