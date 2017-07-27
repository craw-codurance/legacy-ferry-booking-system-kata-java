package ferry.booking.timetable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static ferry.booking.Util.readFileToString;

public class TimeTableEntryJsonFileRepository implements TimeTableEntryRepository {

    private final List<TimeTableEntry> timeTableEntries = new ArrayList<>();

    public TimeTableEntryJsonFileRepository() {
        try {
            String json = readFileToString("/timetable.txt");
            JSONArray arr = new JSONArray(json);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                TimeTableEntry tte = new TimeTableEntry(
                        obj.getInt("Id"),
                        obj.getInt("TimeTableId"), // OriginId = TimeTableId
                        obj.getInt("DestinationId"),
                        obj.getLong("Time"),
                        obj.getLong("JourneyTime")
                );
                timeTableEntries.add(tte);
                Collections.sort(this.timeTableEntries, new Comparator<TimeTableEntry>() {

                    @Override
                    public int compare(TimeTableEntry tte1, TimeTableEntry tte2) {
                        return Long.compare(tte1.getTime(), tte2.getTime());
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<TimeTableEntry> all() {
        return this.timeTableEntries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeTableEntryJsonFileRepository that = (TimeTableEntryJsonFileRepository) o;

        return timeTableEntries != null ? timeTableEntries.equals(that.timeTableEntries) : that.timeTableEntries == null;
    }

    @Override
    public int hashCode() {
        return timeTableEntries != null ? timeTableEntries.hashCode() : 0;
    }
}
