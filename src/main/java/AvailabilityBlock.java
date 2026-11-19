import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class AvailabilityBlock {
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate startDate;
    private LocalDate endDate;
    private RepetitionPattern repetitionPattern;

    public AvailabilityBlock(DayOfWeek dayOfWeek, LocalTime startTime,
                             LocalTime endTime, LocalDate startDate,
                             LocalDate endDate, RepetitionPattern repetitionPattern) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.repetitionPattern = repetitionPattern;
    }

    public DayOfWeek getDayOfWeek() { return dayOfWeek; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public RepetitionPattern getRepetitionPattern() { return repetitionPattern; }
}
