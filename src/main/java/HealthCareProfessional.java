import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class HealthCareProfessional {
    private String professionalId;
    private String name;
    private String licenseNumber;
    private String professionalType;
    private boolean isExternal;
    private Schedule schedule = new Schedule();
    private List<MedicalSpecialty> specialties = new ArrayList<>();
    private List<Appointment> appointments = new ArrayList<>();

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

    // Ignores duplicates (case-insensitive)
    public void addSpecialty(MedicalSpecialty specialty) {
        for (MedicalSpecialty s : specialties) {
            if (s.getName().equalsIgnoreCase(specialty.getName())) return;
        }
        specialties.add(specialty);
    }

    public String getProfessionalId() {
        return professionalId;
    }

    public String getName() {
        return name;
    }

    public String getProfessionalType() {
        return professionalType;
    }

    public List<MedicalSpecialty> getSpecialties() {
        return specialties;
    }

    public int getAvailabilityCount() {
        return schedule.getBlockCount();
    }

    // Every concrete slot of this professional's schedule (used by the CSV export)
    public List<TimeSlot> getAllSlots() {
        return schedule.getAllSlots(professionalId);
    }

    public boolean hasSpecialty(String specialtyName) {
        for (MedicalSpecialty specialty : specialties) {
            if (specialty.getName().equalsIgnoreCase(specialtyName)) {
                return true;
            }
        }
        return false;
    }

    public List<TimeSlot> getFreeSlots(LocalDate startDate, LocalDate endDate) {
        List<TimeSlot> slots = schedule.getSlots(professionalId, startDate, endDate);
        return removeBookedSlots(slots);
    }

    public boolean isAvailable(TimeSlot slot) {
        return getFreeSlots(slot.getDate(), slot.getDate()).contains(slot);
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    private List<TimeSlot> removeBookedSlots(List<TimeSlot> slots) {
        List<TimeSlot> free = new ArrayList<>(slots);
        for (Appointment appointment : appointments) {
            if (appointment.getStatus() != AppointmentStatus.CANCELLED) {
                free.remove(appointment.getTimeSlot());
            }
        }
        return free;
    }
}