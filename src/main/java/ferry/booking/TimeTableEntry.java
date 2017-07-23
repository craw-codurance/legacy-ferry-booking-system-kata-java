package ferry.booking;

public class TimeTableEntry {

    private int id;
    private int timeTableId;
    private int originId;
    private int destinationId;
    private long time;
    private long journeyTime;

    public TimeTableEntry(int id, int timeTableId, int originId, int destinationId, long time, long journeyTime) {
        this.id = id;
        this.timeTableId = timeTableId;
        this.originId = originId;
        this.destinationId = destinationId;
        this.time = time;
        this.journeyTime = journeyTime;
    }

    public int getOriginId() {
        return originId;
    }

    public void setOriginId(int originId) {
        this.originId = originId;
    }

    public int getTimeTableId() {
        return timeTableId;
    }

    public int getDestinationId() {
        return destinationId;
    }

    public long getTime() {
        return time;
    }

    public long getJourneyTime() {
        return journeyTime;
    }

    public int getId() {
        return id;
    }
}
