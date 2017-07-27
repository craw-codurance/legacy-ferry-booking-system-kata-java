package ferry.booking.timetable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ferry.booking.Util.readFileToString;

public class TimeTableJsonFileRepository implements TimeTableRepository {

    private final Map<Integer, TimeTable> timeTables = new HashMap<>();

    public TimeTableJsonFileRepository() {
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
                TimeTable timeTable = timeTables.get(obj.getInt("TimeTableId"));
                if (timeTable == null) {
                    timeTable = new TimeTable(obj.getInt("TimeTableId"));
                    timeTables.put(obj.getInt("TimeTableId"), timeTable);
                }
                timeTable.add(tte);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<TimeTable> all() {
        return new ArrayList<>(timeTables.values());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeTableJsonFileRepository that = (TimeTableJsonFileRepository) o;

        return timeTables != null ? timeTables.equals(that.timeTables) : that.timeTables == null;
    }

    @Override
    public int hashCode() {
        return timeTables != null ? timeTables.hashCode() : 0;
    }
}
