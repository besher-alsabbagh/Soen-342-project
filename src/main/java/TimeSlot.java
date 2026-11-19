import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class TimeSlot {
    private String professionalId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    public TimeSlot(String professionalId, LocalDate date,
                    LocalTime startTime, LocalTime endTime) {
        this.professionalId = professionalId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getProfessionalId() {
        return professionalId;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TimeSlot)) {
            return false;
        }
        TimeSlot other = (TimeSlot) o;
        return Objects.equals(professionalId, other.professionalId)
                && date.equals(other.date)
                && startTime.equals(other.startTime)
                && endTime.equals(other.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(professionalId, date, startTime, endTime);
    }

    @Override
    public String toString() {
        return professionalId + " " + date + " " + startTime + "-" + endTime;
    }
}
