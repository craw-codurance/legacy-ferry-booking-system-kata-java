package ferry.booking;

import ferry.booking.timetable.TimeTableEntry;
import ferry.booking.timetable.TimeTableRepository;

import java.util.List;

public class FerryAvailabilityService {

    private final TimeTableRepository timeTableRepository;
    private final PortManager portManager;

    public FerryAvailabilityService(TimeTableRepository timeTableRepository, PortManager portManager) {
        this.timeTableRepository = timeTableRepository;
        this.portManager = portManager;
    }

    public Ferry nextFerryAvailableFrom(int portId, long time) {
        List<PortModel> ports = portManager.PortModels();

        for (TimeTableEntry timeTableEntry : timeTableRepository.all()) {
            FerryJourney ferry = FerryManager.createFerryJourney(ports, timeTableEntry);
            if (ferry != null) {
                boatReady(timeTableEntry, ferry.destination, ferry);
            }
            if (timeTableEntry.getOriginId() == portId) {
                if (timeTableEntry.getTime() >= time) {
                    if (ferry != null) {
                        return ferry.ferry;
                    }
                }
            }
        }

        return null;
    }

    private static void boatReady(TimeTableEntry timetable, PortModel destination, FerryJourney ferryJourney) {
        if (ferryJourney.ferry == null) {
            FerryManager.addFerry(timetable, ferryJourney);
        }
        Ferry ferry = ferryJourney.ferry;

        long time = FerryModule.timeReady(timetable, destination);
        destination.addBoat(time, ferry);
    }
}
