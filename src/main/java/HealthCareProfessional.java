import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class HealthCareProfessional {
    private String professionalId;
    private String name;
    private String licenseNumber;
    private String professionalType;
    private boolean isExternal;
    private Schedule schedule = new Schedule();

        public HealthCareProfessional(String professionalId) {
        this.professionalId = professionalId;
    }

    public void addAvailability(LocalDate startDate, LocalDate endDate,
                                RepetitionPattern repetitionPattern,
                                List<DayOfWeek> daysOfWeek,
                                LocalTime startTime, LocalTime endTime) {
        schedule.addBlocks(startDate, endDate, repetitionPattern,
                           daysOfWeek, startTime, endTime);
    }

    public String getProfessionalId() {
        return professionalId;
    }

public int getAvailabilityCount() {
    return schedule.getBlockCount();
}

}
