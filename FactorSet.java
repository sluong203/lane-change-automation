public class FactorSet {
    private Double timeNeeded;
    private Double accelerationNeeded;

    public FactorSet(double timeNeeded, double accelerationNeeded) {
        this.timeNeeded = timeNeeded;
        this.accelerationNeeded = accelerationNeeded;
    }

    public FactorSet() {
        this.timeNeeded = null;
        this.accelerationNeeded = null;
    }

    public Double getTimeNeeded() { //if return null then other cars need to change their acceleration
        return timeNeeded;
    }

    public Double getAccelerationNeeded() { //if return null then other cars need to change their acceleration
        return accelerationNeeded;
    }

    public String toString() {
        return "Time Needed: " + timeNeeded + ", Acceleration Needed: " + accelerationNeeded;
    }
}
