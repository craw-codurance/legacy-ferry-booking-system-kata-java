package ferry.booking;

import ferry.booking.timetable.TimeTableEntry;
import ferry.booking.timetable.TimeTableEntryRepository;

import java.util.ArrayList;
import java.util.List;

public class JourneyBookingService {

    private TimeTableEntryRepository timeTableRepository;
    private Bookings bookings;
    private final FerryAvailabilityService ferryService;

    public JourneyBookingService(TimeTableEntryRepository timeTableRepository, Bookings bookings, FerryAvailabilityService ferryService) {
        this.timeTableRepository = timeTableRepository;
        this.bookings = bookings;
        this.ferryService = ferryService;
    }

    public boolean canBook(int journeyId, int passengers) {

        for (TimeTableEntry timetableEntry : timeTableRepository.all()) {
            Ferry ferry = ferryService.nextFerryAvailableFrom(timetableEntry.getOriginId(), timetableEntry.getTime());

            if (timetableEntry.getId() == journeyId) {
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
