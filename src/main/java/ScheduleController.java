import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ScheduleController {
    private ProfessionalRegistry professionalRegistry;
    private HealthCareProfessional selectedProfessional;

    public ScheduleController(ProfessionalRegistry professionalRegistry) {
        this.professionalRegistry = professionalRegistry;
    }

    public void selectProfessional(String professionalId) {
        selectedProfessional = professionalRegistry.findProfessional(professionalId);

        if (selectedProfessional == null) {
            throw new IllegalArgumentException("Professional not found");
        }
    }

    public void makeAvailability(LocalDate startDate, LocalDate endDate,
                                 RepetitionPattern repetitionPattern,
                                 List<DayOfWeek> daysOfWeek,
                                 LocalTime startTime, LocalTime endTime) {
        if (selectedProfessional == null) {
            throw new IllegalStateException("No professional selected");
        }

        selectedProfessional.addAvailability(startDate, endDate,
                repetitionPattern, daysOfWeek, startTime, endTime);
    }
}
