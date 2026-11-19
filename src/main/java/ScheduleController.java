import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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

    // Pass null for professionalId or specialty to search all
    public List<TimeSlot> searchAvailability(String professionalId, String specialty,
                                             LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Invalid date range");
        }

        List<TimeSlot> freeSlots = new ArrayList<>();
        List<HealthCareProfessional> professionals =
                professionalRegistry.findProfessionals(professionalId, specialty);

        for (HealthCareProfessional professional : professionals) {
            freeSlots.addAll(professional.getFreeSlots(startDate, endDate));
        }
        return freeSlots;
    }

    // Returns the number of slots imported
    public int importFromCSV(String filePath) throws IOException {
        return new AvailabilityCsvImporter(professionalRegistry).importFrom(filePath);
    }

    // Returns the number of slots exported
    public int exportToCSV(String filePath) throws IOException {
        return new AvailabilityCsvExporter(professionalRegistry).exportTo(filePath);
    }
}