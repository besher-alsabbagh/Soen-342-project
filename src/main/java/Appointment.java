import java.time.LocalDateTime;

public class Appointment {
    private String appointmentId;
    private LocalDateTime requestedAt;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private AppointmentStatus status;
    private Patient patient;
    private HealthCareProfessional professional;
    private AdministrativeStaff requestedBy;

    public Appointment(String appointmentId, Patient patient,
                       HealthCareProfessional professional,
                       AdministrativeStaff staff, TimeSlot slot) {
        this.appointmentId = appointmentId;
        this.patient = patient;
        this.professional = professional;
        this.requestedBy = staff;
        this.startDateTime = LocalDateTime.of(slot.getDate(), slot.getStartTime());
        this.endDateTime = LocalDateTime.of(slot.getDate(), slot.getEndTime());
        this.requestedAt = LocalDateTime.now();
        this.status = AppointmentStatus.REQUESTED;
    }

    public void confirm() {
        if (status != AppointmentStatus.REQUESTED) {
            throw new IllegalStateException("Only requested appointments can be confirmed");
        }
        status = AppointmentStatus.CONFIRMED;
    }

    public TimeSlot getTimeSlot() {
        return new TimeSlot(professional.getProfessionalId(),
                startDateTime.toLocalDate(),
                startDateTime.toLocalTime(),
                endDateTime.toLocalTime());
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public AppointmentStatus getStatus() {
        return status;
    }
}
