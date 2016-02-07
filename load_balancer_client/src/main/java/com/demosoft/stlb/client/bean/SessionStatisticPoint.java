package com.demosoft.stlb.client.bean;

/**
 * Created by Andrii Korkoshko on 07.02.2016.
 */
public class SessionStatisticPoint {
    private Long timePoint;
    private Long timeSpent;

    public SessionStatisticPoint(Long timePoint, Long timeSpent) {
        this.timePoint = timePoint;
        this.timeSpent = timeSpent;
    }

    public Long getTimePoint() {
        return timePoint;
    }

    public void setTimePoint(Long timePoint) {
        this.timePoint = timePoint;
    }

    public Long getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(Long timeSpent) {
        this.timeSpent = timeSpent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SessionStatisticPoint)) return false;

        SessionStatisticPoint that = (SessionStatisticPoint) o;

        if (timePoint != null ? !timePoint.equals(that.timePoint) : that.timePoint != null) return false;
        return timeSpent != null ? timeSpent.equals(that.timeSpent) : that.timeSpent == null;

    }

    @Override
    public int hashCode() {
        int result = timePoint != null ? timePoint.hashCode() : 0;
        result = 31 * result + (timeSpent != null ? timeSpent.hashCode() : 0);
        return result;
    }
}
