package ferry.booking;

import ferry.booking.timetable.TimeTableEntry;
import ferry.booking.timetable.TimeTableEntryRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TimeTableService {

    private final TimeTableEntryRepository timeTableEntryRepository;
    private final FerryAvailabilityService ferryService;

    public TimeTableService(TimeTableEntryRepository timeTableEntryRepository, FerryAvailabilityService ferryService) {
        this.timeTableEntryRepository = timeTableEntryRepository;
        this.ferryService = ferryService;
    }

    public List<TimeTableViewModelRow> getTimeTable(List<Port> ports) {

        List<TimeTableViewModelRow> rows = new ArrayList<TimeTableViewModelRow>();

        for (TimeTableEntry timetableEntry : getEntriesSortedByTime()) {
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

    public List<TimeTableEntry> getEntriesSortedByTime() {
        List<TimeTableEntry> timeTableEntries = timeTableEntryRepository.all();
        Collections.sort(timeTableEntries, new Comparator<TimeTableEntry>() {

            @Override
            public int compare(TimeTableEntry tte1, TimeTableEntry tte2) {
                return Long.compare(tte1.getTime(), tte2.getTime());
            }
        });
        return timeTableEntries;
    }
}
