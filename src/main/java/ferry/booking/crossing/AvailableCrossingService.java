package ferry.booking.crossing;

import ferry.booking.*;
import ferry.booking.timetable.TimeTableEntry;

import java.util.ArrayList;
import java.util.List;

public class AvailableCrossingService {
    
    private final TimeTableService timeTableService;
    private final Bookings bookings;
    private final FerryAvailabilityService ferryService;


    public AvailableCrossingService(
            TimeTableService timeTableService,
            Bookings bookings,
            FerryAvailabilityService ferryService
    ) {
        this.timeTableService = timeTableService;
        this.bookings = bookings;
        this.ferryService = ferryService;
    }

    public List<AvailableCrossing> getAvailableCrossings(long time, int fromPort, int toPort) {
        List<Port> ports = new Ports().all();

        List<AvailableCrossing> result = new ArrayList<AvailableCrossing>();

        for (TimeTableEntry timetableEntry : timeTableService.getEntriesSortedByTime()) {
            Port origin = null;
            Port destination = null;
            for (Port x : ports) {
                if (x.id == timetableEntry.getOriginId()) {
                    origin = x;
                }
                if (x.id == timetableEntry.getDestinationId()) {
                    destination = x;
                }
            }
            Ferry ferry = ferryService.nextFerryAvailableFrom(timetableEntry.getOriginId(), timetableEntry.getTime());

            if (toPort == destination.id && fromPort == origin.id) {
                if (timetableEntry.getTime() >= time) {
                    List<Booking> journeyBookings = new ArrayList<Booking>();
                    for (Booking x : bookings.all()) {
                        if (x.journeyId == timetableEntry.getId()) {
                            journeyBookings.add(x);
                        }
                    }
                    int pax = 0;
                    for (Booking x : journeyBookings) {
                        pax += x.passengers;
                    }
                    int seatsLeft = ferry.passengers - pax;
                    if (seatsLeft > 0) {
                        AvailableCrossing crossing = new AvailableCrossing();
                        crossing.arrive = timetableEntry.getTime() + timetableEntry.getJourneyTime();
                        crossing.ferryName = ferry.name;
                        int pax2 = 0;
                        for (Booking x : journeyBookings) {
                            pax2 += x.passengers;
                        }
                        crossing.seatsLeft = ferry.passengers - pax2;
                        crossing.setOff = timetableEntry.getTime();
                        crossing.originPort = origin.name;
                        crossing.destinationPort = destination.name;
                        crossing.journeyId = timetableEntry.getId();
                        result.add(crossing);
                    }
                }
            }
        }
        return result;
    }
}