import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ImportExportTest {
    public static void main(String[] args) throws IOException {
        ProfessionalRegistry registry = new ProfessionalRegistry();
        ScheduleController controller = new ScheduleController(registry);

        // 1. Import the sample file
        int imported = controller.importFromCSV("sample_availability.csv");
        check(imported == 8, "Expected 8 slots imported but got " + imported);
        check(registry.getAllProfessionals().size() == 5, "Expected 5 professionals");

        HealthCareProfessional amelie = registry.findProfessional("amélie-tremblay");
        check(amelie != null && amelie.getName().equals("Amélie Tremblay"), "Amélie not found");
        check(amelie.getAllSlots().size() == 2, "Amélie should have 2 slots");
        check(amelie.getSpecialties().size() == 1, "Amélie should have 1 specialty");

        // 2. Importing the same file again must not add anything
        check(controller.importFromCSV("sample_availability.csv") == 0, "Duplicates were imported");

        // 3. Export, then import the exported file into an empty registry
        int exported = controller.exportToCSV("export_test.csv");
        check(exported == 8, "Expected 8 slots exported but got " + exported);
        ProfessionalRegistry fresh = new ProfessionalRegistry();
        int reimported = new ScheduleController(fresh).importFromCSV("export_test.csv");
        check(reimported == 8, "Round trip should give 8 slots but got " + reimported);

        // 4. Availability made with makeAvailability is exported one slot per row
        ProfessionalRegistry registry2 = new ProfessionalRegistry();
        registry2.addProfessional(new HealthCareProfessional("P1", "Ada Lovelace", "Dr."));
        ScheduleController controller2 = new ScheduleController(registry2);
        controller2.selectProfessional("P1");
        controller2.makeAvailability(
            LocalDate.of(2026, 11, 20), LocalDate.of(2026, 12, 20),
            RepetitionPattern.WEEKLY, List.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY),
            LocalTime.of(9, 0), LocalTime.of(17, 0));
        int exported2 = controller2.exportToCSV("export_test2.csv");
        check(exported2 == 8, "Expected 8 slots (4 Mondays + 4 Wednesdays) but got " + exported2);

        // 5. A bad row must not stop the import
        Files.writeString(Path.of("bad_rows_test.csv"),
            "First Name,Last Name,Title,Specialty,Date,Start,End\n"
            + "Ann,Lee,Dr.,Cardiology,2026-11-02,09:00,10:00\n"
            + "Bob,Ray,Dr.,Dermatology,not-a-date,09:00,10:00\n"
            + "Cal,Wu,Dr.,Dermatology,2026-11-02,10:00,09:00\n");
        ProfessionalRegistry registry3 = new ProfessionalRegistry();
        int good = new ScheduleController(registry3).importFromCSV("bad_rows_test.csv");
        check(good == 1, "Expected only the good row to be imported but got " + good);
        check(registry3.getAllProfessionals().size() == 1, "Bad rows should not create professionals");

        // 6. Missing file
        try {
            controller.importFromCSV("does_not_exist.csv");
            throw new AssertionError("Missing file was accepted");
        } catch (IOException e) {
            System.out.println("Missing file rejected");
        }

        System.out.println("Import/Export completed");
    }

    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}