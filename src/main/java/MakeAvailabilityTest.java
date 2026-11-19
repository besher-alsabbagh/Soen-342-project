import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class MakeAvailabilityTest {
    public static void main(String[] args) {
        ProfessionalRegistry registry = new ProfessionalRegistry();
        HealthCareProfessional professional = new HealthCareProfessional("P1");
        registry.addProfessional(professional);

        ScheduleController controller = new ScheduleController(registry);

        controller.selectProfessional("P1");
        controller.makeAvailability(
            LocalDate.of(2026, 11, 20),
            LocalDate.of(2026, 12, 20),
            RepetitionPattern.WEEKLY,
            List.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY),
            LocalTime.of(9, 0),
            LocalTime.of(17, 0)
        );

        if (professional.getAvailabilityCount() != 2) {
    throw new AssertionError("Expected 2 availability blocks");
}

    try {
    controller.makeAvailability(
        LocalDate.of(2026, 12, 20),
        LocalDate.of(2026, 11, 20),
        RepetitionPattern.WEEKLY,
        List.of(DayOfWeek.MONDAY),
        LocalTime.of(9, 0),
        LocalTime.of(17, 0)
    );
    throw new AssertionError("Invalid dates accepted");
} catch (IllegalArgumentException e) {
    System.out.println("Invalid dates rejected");
}        


        System.out.println("Make Availability completed");
    }
}
