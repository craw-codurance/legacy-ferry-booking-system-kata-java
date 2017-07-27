package ferry.booking;

import ferry.booking.timetable.TimeTable;
import ferry.booking.timetable.TimeTableEntry;
import ferry.booking.timetable.TimeTableRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class JourneyBookingService {

    private TimeTableRepository timeTableRepository;
    private Bookings bookings;
    private final FerryAvailabilityService ferryService;

    public JourneyBookingService(TimeTableRepository timeTableRepository, Bookings bookings, FerryAvailabilityService ferryService) {
        this.timeTableRepository = timeTableRepository;
        this.bookings = bookings;
        this.ferryService = ferryService;
    }

    public boolean canBook(int journeyId, int passengers) {
        List<TimeTable> timetables = timeTableRepository.all();
        List<TimeTableEntry> allEntries = new ArrayList<TimeTableEntry>();
        for (TimeTable tt : timetables) {
            allEntries.addAll(tt.getEntries());
        }
        Collections.sort(allEntries, new Comparator<TimeTableEntry>() {

            @Override
            public int compare(TimeTableEntry tte1, TimeTableEntry tte2) {
                return Long.compare(tte1.getTime(), tte2.getTime());
            }
        });

        for (TimeTableEntry timetable : allEntries) {
            Ferry ferry = ferryService.nextFerryAvailableFrom(timetable.getOriginId(), timetable.getTime());

            if (timetable.getId() == journeyId) {
                List<Booking> journeyBookings = new ArrayList<>();
                for (Booking x : bookings.all()) {
                    if (x.journeyId == journeyId) {
                        journeyBookings.add(x);
                    }
                }
                int pax = 0;
                for (Booking x : bookings.all()) {
                    pax += x.passengers;
                }
                int seatsLeft = ferry.passengers - pax;
                return seatsLeft >= passengers;
            }
        }
        return false;
    }

    public void book(Booking booking) {
        bookings.add(booking);
    }

    public List<Booking> getAllBookings() {
        return bookings.all();
    }
}
