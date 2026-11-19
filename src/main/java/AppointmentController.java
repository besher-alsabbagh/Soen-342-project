import java.util.Optional;

public class AppointmentController {
    private PatientRegistry patientRegistry;
    private ProfessionalRegistry professionalRegistry;
    private AppointmentRegistry appointmentRegistry;
    private AdministrativeStaff currentStaff;

    public AppointmentController(PatientRegistry patientRegistry,
                                 ProfessionalRegistry professionalRegistry,
                                 AppointmentRegistry appointmentRegistry,
                                 AdministrativeStaff currentStaff) {
        this.patientRegistry = patientRegistry;
        this.professionalRegistry = professionalRegistry;
        this.appointmentRegistry = appointmentRegistry;
        this.currentStaff = currentStaff;
    }

    // Returns empty if the slot is not available
    public Optional<Appointment> requestAppointment(String patientId,
                                                    String professionalId,
                                                    TimeSlot slot) {
        Patient patient = patientRegistry.findPatient(patientId);
        if (patient == null) {
            throw new IllegalArgumentException("Patient not found");
        }

        HealthCareProfessional professional = professionalRegistry.findProfessional(professionalId);
        if (professional == null) {
            throw new IllegalArgumentException("Professional not found");
        }

        if (!professional.isAvailable(slot)) {
            return Optional.empty();
        }

        Appointment appointment = appointmentRegistry.createAppointment(
                patient, professional, currentStaff, slot);
        professional.addAppointment(appointment);
        return Optional.of(appointment);
    }

    public void confirmAppointment(String appointmentId) {
        Appointment appointment = appointmentRegistry.findAppointment(appointmentId);
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment not found");
        }
        appointment.confirm();
    }
}
