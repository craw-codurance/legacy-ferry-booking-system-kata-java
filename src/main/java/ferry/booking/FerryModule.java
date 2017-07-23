package ferry.booking;

public class FerryModule {

    public static long timeReady(TimeTableEntry timetable, PortModel destination) {
        if (timetable == null) {
            return 0;
        }
        if (destination == null) {
            throw new NullPointerException("destination");
        }

        long arrivalTime = timetable.getTime() + timetable.getJourneyTime();
        int turnaroundTime = FerryManager.getFerryTurnaroundTime(destination);
        long timeReady = arrivalTime + turnaroundTime;
        return timeReady;
    }
}
