package Models;

import java.util.Objects;

public class ZipTask {

    private long finishTime;
    private long plannedTime;

    public ZipTask(long finishTime, long plannedTime) {
        this.finishTime = finishTime;
        this.plannedTime = plannedTime;
    }

    public long getPlannedTime() {
        return plannedTime;
    }

    public void setPlannedTime(long plannedTime) {
        this.plannedTime = plannedTime;
    }

    public long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZipTask zipTask = (ZipTask) o;
        return finishTime == zipTask.finishTime &&
                plannedTime == zipTask.plannedTime;
    }

    @Override
    public int hashCode() {

        return Objects.hash(finishTime, plannedTime);
    }
}
