import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Comparator;

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
    // Every concrete slot of this schedule, sorted by date and time (used by the CSV export).
    public List<TimeSlot> getAllSlots() {
        List<TimeSlot> slots = new ArrayList<>();
        for (AvailabilityBlock block : blocks) {
            slots.addAll(generateSlots(block, block.getStartDate(), block.getEndDate()));
        }
        slots.sort(Comparator.comparing(TimeSlot::getDate).thenComparing(TimeSlot::getStartTime));
        return slots;
    }

    private List<TimeSlot> generateSlots(AvailabilityBlock block, LocalDate startDate, LocalDate endDate) {
        List<TimeSlot> slots = new ArrayList<>();
        LocalDate first = startDate.isAfter(block.getStartDate()) ? startDate : block.getStartDate();
        LocalDate last = endDate.isBefore(block.getEndDate()) ? endDate : block.getEndDate();
        for (LocalDate d = first; !d.isAfter(last); d = d.plusDays(1)) {
            if (d.getDayOfWeek() != block.getDayOfWeek()) continue;
            // MONTHLY: only the first occurrence of that weekday in each month
            boolean occurs = block.getRepetitionPattern() == RepetitionPattern.WEEKLY
                    || d.getDayOfMonth() <= 7;
            if (occurs) {
                slots.add(new TimeSlot(d, block.getStartTime(), block.getEndTime()));
            }
        }
        return slots;
    }

    // All slots (booked or not) between startDate and endDate, sorted by date then time
    public List<TimeSlot> getSlots(String professionalId,
                                   LocalDate startDate, LocalDate endDate) {
        List<TimeSlot> slots = new ArrayList<>();
        for (AvailabilityBlock block : blocks) {
            slots.addAll(generateSlots(professionalId, block, startDate, endDate));
        }
        slots.sort(Comparator.comparing(TimeSlot::getDate)
                .thenComparing(TimeSlot::getStartTime));
        return slots;
    }

    // WEEKLY: every matching day of week in range
    // MONTHLY: first matching day of week of each month in range
    private List<TimeSlot> generateSlots(String professionalId, AvailabilityBlock block,
                                         LocalDate startDate, LocalDate endDate) {
        List<TimeSlot> slots = new ArrayList<>();

        LocalDate from = startDate.isAfter(block.getStartDate()) ? startDate : block.getStartDate();
        LocalDate to = endDate.isBefore(block.getEndDate()) ? endDate : block.getEndDate();

        for (LocalDate date = from; !date.isAfter(to); date = date.plusDays(1)) {
            if (date.getDayOfWeek() != block.getDayOfWeek()) {
                continue;
            }
            if (block.getRepetitionPattern() == RepetitionPattern.MONTHLY
                    && date.getDayOfMonth() > 7) {
                continue;
            }
            slots.add(new TimeSlot(professionalId, date,
                    block.getStartTime(), block.getEndTime()));
        }
        return slots;
    }

}
