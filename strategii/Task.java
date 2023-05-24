package strategii;


public class Task {
    private int id;
    private int arrivalTime;
    private int serviceTime;
    private int remainingServiceTime;

    public Task(int id, int arrivalTime, int serviceTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.remainingServiceTime = serviceTime;

    }

    public int getId() {
        return id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }
    
    public int getRemainingServiceTime() {
        return remainingServiceTime;
    }

    public void decrementRemainingServiceTime() {
        if (this.remainingServiceTime > 0)
            this.remainingServiceTime--;
    }
        @Override
        public String toString() {
            return "(" + id + "," + arrivalTime + "," + serviceTime + "," + remainingServiceTime + ")";
    }
}
