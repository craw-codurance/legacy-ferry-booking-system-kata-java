package ferry.booking;

import ferry.booking.timetable.TimeTableEntry;

import java.util.List;

public class FerryManager {

    public static FerryJourney createFerryJourney(List<PortModel> ports, TimeTableEntry timetable) {
        if (ports == null) {
            return null;
        }

        if (timetable == null) {
            return null;
        }

        FerryJourney fj = new FerryJourney();
        for (PortModel port : ports) {
            if (port.id == timetable.getOriginId()) {
                fj.origin = port;
            }
            if (port.id == timetable.getDestinationId()) {
                fj.destination = port;
            }
        }
        return fj;
    }

    public static void addFerry(TimeTableEntry timetable, FerryJourney journey) {
        journey.ferry = journey.origin.getNextAvailable(timetable.getTime());
    }

    public static int getFerryTurnaroundTime(PortModel destination) {
        if (destination.id == 3) {
            return 25;
        }
        if (destination.id == 2) {
            return 20;
        }
        return 15;
    }
}
