package strategii;


import java.util.List;
import java.util.Optional;

class ConcreteStrategyTime implements Strategy {
    @Override
    public void addTask(List<Server> servers, Task t) {
        Optional<Server> serverWithShortestTime = servers.stream()
                .min((s1, s2) -> Integer.compare(s1.getWaitingPeriod().get(), s2.getWaitingPeriod().get()));

        serverWithShortestTime.ifPresent(server -> server.addTask(t));
    }
}	