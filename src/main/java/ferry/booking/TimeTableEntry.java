package ferry.booking;

public class TimeTableEntry {

    private int id;
    private int originId;
    private int destinationId;
    private long time;
    private long journeyTime;

    public TimeTableEntry(int id, int originId, int destinationId, long time, long journeyTime) {
        this.id = id;
        this.originId = originId;
        this.destinationId = destinationId;
        this.time = time;
        this.journeyTime = journeyTime;
    }

    public int getOriginId() {
        return originId;
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

    @Override
    public String toString() {
        return "TimeTableEntry{" +
                "id=" + id +
                ", originId=" + originId +
                ", destinationId=" + destinationId +
                ", time=" + time +
                ", journeyTime=" + journeyTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeTableEntry that = (TimeTableEntry) o;

        if (id != that.id) return false;
        if (originId != that.originId) return false;
        if (destinationId != that.destinationId) return false;
        if (time != that.time) return false;
        return journeyTime == that.journeyTime;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + originId;
        result = 31 * result + destinationId;
        result = 31 * result + (int) (time ^ (time >>> 32));
        result = 31 * result + (int) (journeyTime ^ (journeyTime >>> 32));
        return result;
    }
}
