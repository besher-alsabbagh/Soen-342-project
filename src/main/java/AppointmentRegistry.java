import java.util.ArrayList;
import java.util.List;

public class AppointmentRegistry {
    private List<Appointment> appointments = new ArrayList<>();
    private int nextId = 1;

    public Appointment createAppointment(Patient patient,
                                         HealthCareProfessional professional,
                                         AdministrativeStaff staff, TimeSlot slot) {
        Appointment appointment = new Appointment("A" + nextId, patient,
                professional, staff, slot);
        nextId++;
        appointments.add(appointment);
        return appointment;
    }

    public Appointment findAppointment(String appointmentId) {
        for (Appointment appointment : appointments) {
            if (appointmentId.equals(appointment.getAppointmentId())) {
                return appointment;
            }
        }
        return null;
    }
}
