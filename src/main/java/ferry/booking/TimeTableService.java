package ferry.booking;

import ferry.booking.timetable.TimeTableEntry;
import ferry.booking.timetable.TimeTableEntryRepository;

import java.util.ArrayList;
import java.util.List;

public class TimeTableService {

    private final TimeTableEntryRepository timeTableRepository;
    private final Bookings bookings;
    private final FerryAvailabilityService ferryService;

    public TimeTableService(TimeTableEntryRepository timeTableRepository, Bookings bookings, FerryAvailabilityService ferryService) {
        this.timeTableRepository = timeTableRepository;
        this.bookings = bookings;
        this.ferryService = ferryService;
    }

    public List<TimeTableViewModelRow> getTimeTable(List<Port> ports) {

        List<TimeTableViewModelRow> rows = new ArrayList<TimeTableViewModelRow>();

        for (TimeTableEntry timetableEntry : timeTableRepository.all()) {
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
            String destinationName = destination.name;
            String originName = origin.name;
            Ferry ferry = ferryService.nextFerryAvailableFrom(origin.id, timetableEntry.getTime());
            long arrivalTime = timetableEntry.getTime() + timetableEntry.getJourneyTime();
            TimeTableViewModelRow row = new TimeTableViewModelRow();
            row.destinationPort = destinationName;
            row.ferryName = ferry == null ? "" : ferry.name;
            row.journeyLength = String.format("%02d:%02d", timetableEntry.getJourneyTime() / 60, timetableEntry.getJourneyTime() % 60);
            row.originPort = originName;
            row.startTime = String.format("%02d:%02d", timetableEntry.getTime() / 60, timetableEntry.getTime() % 60);
            row.arrivalTime = String.format("%02d:%02d", arrivalTime / 60, arrivalTime % 60);
            rows.add(row);
        }
        return rows;
    }

    public List<AvailableCrossing> getAvailableCrossings(long time, int fromPort, int toPort) {
        List<Port> ports = new Ports().all();

        List<AvailableCrossing> result = new ArrayList<AvailableCrossing>();

        for (TimeTableEntry timetableEntry : timeTableRepository.all()) {
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
                    List<Booking> journeyBookings = new ArrayList<>();
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
