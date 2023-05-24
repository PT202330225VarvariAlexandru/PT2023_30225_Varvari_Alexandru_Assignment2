package strategii;


import java.util.List;
import java.util.Optional;

public interface Strategy {
    void addTask(List<Server> servers, Task t);
}

