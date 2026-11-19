import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.io.IOException;

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

    // return the number of slots imported 
    public int importFromCSV(String filePath) throws IOException {
        return new AvailabilityCsvImporter(professionalRegistry).importFrom(filePath);
    }

    // return the number of slots exported 
    public int exportToCSV(String filePath) throws IOException {
        return new AvailabilityCsvExporter(professionalRegistry).exportTo(filePath);
    }
}
