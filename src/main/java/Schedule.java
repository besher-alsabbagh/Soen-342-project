import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Schedule {
    private List<AvailabilityBlock> blocks = new ArrayList<>();

    public void addBlocks(LocalDate startDate, LocalDate endDate,
                          RepetitionPattern repetitionPattern,
                          List<DayOfWeek> daysOfWeek,
                          LocalTime startTime, LocalTime endTime) {

        if (startDate.isAfter(endDate) || !startTime.isBefore(endTime)) {
            throw new IllegalArgumentException("Invalid date or time range");
        }

        for (DayOfWeek day : daysOfWeek) {
            AvailabilityBlock block = new AvailabilityBlock(
                day, startTime, endTime, startDate, endDate, repetitionPattern
            );

            blocks.add(block);
        }
    }

public int getBlockCount() {
    return blocks.size();
}

}
