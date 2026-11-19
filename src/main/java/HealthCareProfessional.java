import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;

public class HealthCareProfessional {
    private String professionalId;
    private String name;
    private String licenseNumber;
    private String professionalType;
    private boolean isExternal;
    private Schedule schedule = new Schedule();
    private List<MedicalSpecialty> specialties = new ArrayList<>();

        public HealthCareProfessional(String professionalId) {
        this.professionalId = professionalId;
    }

    public HealthCareProfessional(String professionalId, String name, String professionalType) {
        this.professionalId = professionalId;
        this.name = name;
        this.professionalType = professionalType;
    }

    public void addAvailability(LocalDate startDate, LocalDate endDate,
                                RepetitionPattern repetitionPattern,
                                List<DayOfWeek> daysOfWeek,
                                LocalTime startTime, LocalTime endTime) {
        schedule.addBlocks(startDate, endDate, repetitionPattern,
                           daysOfWeek, startTime, endTime);
    }

    public void addSpecialty(MedicalSpecialty specialty) {
        for (MedicalSpecialty s : specialties) {
            if (s.getName().equalsIgnoreCase(specialty.getName())) return;
        }
        specialties.add(specialty);
    }

    public String getProfessionalId() {
        return professionalId;
    }

    public int getAvailabilityCount() {
    return schedule.getBlockCount();
}
    public List<TimeSlot> getAllSlots() {
        return schedule.getAllSlots();
    }

    public String getName() { return name; }
    public String getProfessionalType() { return professionalType; }
    public List<MedicalSpecialty> getSpecialties() { return specialties; }

}
