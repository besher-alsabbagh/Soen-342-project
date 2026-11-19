import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.StringJoiner;


public class AvailabilityCsvExporter {
    private ProfessionalRegistry professionalRegistry;

    public AvailabilityCsvExporter(ProfessionalRegistry professionalRegistry) {
        this.professionalRegistry = professionalRegistry;
    }

    /** return the number of slots exported */
    public int exportTo(String filePath) throws IOException {
        int rows = 0;
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(filePath), StandardCharsets.UTF_8)) {
            writer.write("First Name,Last Name,Title,Specialty,Date,Start,End");
            writer.newLine();
            for (HealthCareProfessional professional : professionalRegistry.getAllProfessionals()) {
                String name = professional.getName() == null ? "" : professional.getName();
                int space = name.indexOf(' ');
                String firstName = space < 0 ? name : name.substring(0, space);
                String lastName = space < 0 ? "" : name.substring(space + 1);

                StringJoiner specialties = new StringJoiner(";");
                for (MedicalSpecialty s : professional.getSpecialties()) specialties.add(s.getName());

                for (TimeSlot slot : professional.getAllSlots()) {
                    writer.write(String.join(",",
                            escape(firstName), escape(lastName),
                            escape(professional.getProfessionalType()), escape(specialties.toString()),
                            slot.getDate().toString(), slot.getStartTime().toString(), slot.getEndTime().toString()));
                    writer.newLine();
                    rows++;
                }
            }
        }
        return rows;
    }

    // Wraps a value in quotes when it contains a comma, quote or line break. 
    private static String escape(String value) {
        if (value == null) return "";
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}