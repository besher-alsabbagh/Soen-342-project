
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        ProfessionalRegistry professionals = new ProfessionalRegistry();
        PatientRegistry patients = new PatientRegistry();
        AppointmentRegistry appointments = new AppointmentRegistry();

        HealthCareProfessional doctor = new HealthCareProfessional("P1");
        professionals.addProfessional(doctor);

        Patient patient = new Patient(
                "PAT1", "John", "Smith",
                LocalDate.of(2000, 1, 1), "5145551234");
        patients.addPatient(patient);

        AdministrativeStaff staff = new AdministrativeStaff("S1", "Admin");

        ScheduleController scheduleController = new ScheduleController(professionals);
        AppointmentController appointmentController =
                new AppointmentController(patients, professionals, appointments, staff);

        // Make Availability
        scheduleController.selectProfessional("P1");
        scheduleController.makeAvailability(
                LocalDate.of(2026, 11, 1),
                LocalDate.of(2026, 11, 30),
                RepetitionPattern.WEEKLY,
                Arrays.asList(DayOfWeek.MONDAY),
                LocalTime.of(9, 0),
                LocalTime.of(12, 0));

        System.out.println("Availability created");

        // Search Availability
        List<TimeSlot> freeSlots = scheduleController.searchAvailability(
                "P1", null,
                LocalDate.of(2026, 11, 1),
                LocalDate.of(2026, 11, 30));

        System.out.println("Available slots: " + freeSlots.size());

        // Book Appointment
        if (!freeSlots.isEmpty()) {
            Optional<Appointment> booking = appointmentController.requestAppointment(
                    "PAT1", "P1", freeSlots.get(0));

            if (booking.isPresent()) {
                System.out.println("Appointment booked");
            }
        }
    }
}
