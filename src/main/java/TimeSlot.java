import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class TimeSlot implements Comparable<TimeSlot> {
    private final LocalDate date;
    private final LocalTime startTime;
    private final LocalTime endTime;

    public TimeSlot(LocalDate date, LocalTime startTime, LocalTime endTime) {
        if (date == null || startTime == null || endTime == null) {
            throw new IllegalArgumentException("Date, start and end time are required");
        }
        if (!startTime.isBefore(endTime)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalDate getDate() { return date; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }
    public LocalDateTime getStartDateTime() { return date.atTime(startTime); }
    public LocalDateTime getEndDateTime() { return date.atTime(endTime); }

    /** True if the other slot is on the same day and lies entirely inside this one. */
    public boolean contains(TimeSlot other) {
        return date.equals(other.date)
                && !other.startTime.isBefore(startTime)
                && !other.endTime.isAfter(endTime);
    }

    @Override
    public int compareTo(TimeSlot o) {
        int c = date.compareTo(o.date);
        if (c != 0) return c;
        c = startTime.compareTo(o.startTime);
        if (c != 0) return c;
        return endTime.compareTo(o.endTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeSlot)) return false;
        TimeSlot t = (TimeSlot) o;
        return date.equals(t.date) && startTime.equals(t.startTime) && endTime.equals(t.endTime);
    }

    @Override
    public int hashCode() { return Objects.hash(date, startTime, endTime); }

    @Override
    public String toString() { return date + " " + startTime + "-" + endTime; }
}
