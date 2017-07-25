package ferry.booking;

import ferry.booking.timetable.TimeTables;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TimeTableService {

    private final TimeTables timeTables;
    private final Bookings bookings;
    private final FerryAvailabilityService ferryService;

    public TimeTableService(TimeTables timeTables, Bookings bookings, FerryAvailabilityService ferryService) {
        this.timeTables = timeTables;
        this.bookings = bookings;
        this.ferryService = ferryService;
    }

    public List<TimeTableViewModelRow> getTimeTable(List<Port> ports) {
        List<TimeTable> timetables = timeTables.all();
        List<TimeTableEntry> allEntries = new ArrayList<TimeTableEntry>();
        for (TimeTable tt : timetables) {
            allEntries.addAll(tt.entries);
        }
        Collections.sort(allEntries, new Comparator<TimeTableEntry>() {

            @Override
            public int compare(TimeTableEntry tte1, TimeTableEntry tte2) {
                return Long.compare(tte1.getTime(), tte2.getTime());
            }
        });

        List<TimeTableViewModelRow> rows = new ArrayList<TimeTableViewModelRow>();

        for (TimeTableEntry timetable : allEntries) {
            Port origin = null;
            Port destination = null;
            for (Port x : ports) {
                if (x.id == timetable.getOriginId()) {
                    origin = x;
                }
                if (x.id == timetable.getDestinationId()) {
                    destination = x;
                }
            }
            String destinationName = destination.name;
            String originName = origin.name;
            Ferry ferry = ferryService.nextFerryAvailableFrom(origin.id, timetable.getTime());
            long arrivalTime = timetable.getTime() + timetable.getJourneyTime();
            TimeTableViewModelRow row = new TimeTableViewModelRow();
            row.destinationPort = destinationName;
            row.ferryName = ferry == null ? "" : ferry.name;
            row.journeyLength = String.format("%02d:%02d", timetable.getJourneyTime() / 60, timetable.getJourneyTime() % 60);
            row.originPort = originName;
            row.startTime = String.format("%02d:%02d", timetable.getTime() / 60, timetable.getTime() % 60);
            row.arrivalTime = String.format("%02d:%02d", arrivalTime / 60, arrivalTime % 60);
            rows.add(row);
        }
        return rows;
    }

    public List<AvailableCrossing> getAvailableCrossings(long time, int fromPort, int toPort) {
        List<Port> ports = new Ports().all();
        List<TimeTable> timetables = timeTables.all();
        List<TimeTableEntry> allEntries = new ArrayList<TimeTableEntry>();
        for (TimeTable tt : timetables) {
            allEntries.addAll(tt.entries);
        }
        Collections.sort(allEntries, new Comparator<TimeTableEntry>() {

            @Override
            public int compare(TimeTableEntry tte1, TimeTableEntry tte2) {
                return Long.compare(tte1.getTime(), tte2.getTime());
            }
        });

        List<AvailableCrossing> result = new ArrayList<AvailableCrossing>();

        for (TimeTableEntry timetable : allEntries) {
            Port origin = null;
            Port destination = null;
            for (Port x : ports) {
                if (x.id == timetable.getOriginId()) {
                    origin = x;
                }
                if (x.id == timetable.getDestinationId()) {
                    destination = x;
                }
            }
            Ferry ferry = ferryService.nextFerryAvailableFrom(timetable.getOriginId(), timetable.getTime());

            if (toPort == destination.id && fromPort == origin.id) {
                if (timetable.getTime() >= time) {
                    List<Booking> journeyBookings = new ArrayList<>();
                    for (Booking x : bookings.all()) {
                        if (x.journeyId == timetable.getId()) {
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
                        crossing.arrive = timetable.getTime() + timetable.getJourneyTime();
                        crossing.ferryName = ferry.name;
                        int pax2 = 0;
                        for (Booking x : journeyBookings) {
                            pax2 += x.passengers;
                        }
                        crossing.seatsLeft = ferry.passengers - pax2;
                        crossing.setOff = timetable.getTime();
                        crossing.originPort = origin.name;
                        crossing.destinationPort = destination.name;
                        crossing.journeyId = timetable.getId();
                        result.add(crossing);
                    }
                }
            }
        }
        return result;
    }
}
