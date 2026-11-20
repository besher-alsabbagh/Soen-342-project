import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class AvailabilityCsvImporter {
    private ProfessionalRegistry professionalRegistry;

    public AvailabilityCsvImporter(ProfessionalRegistry professionalRegistry) {
        this.professionalRegistry = professionalRegistry;
    }


    public int importFrom(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(filePath), StandardCharsets.UTF_8);
        int imported = 0;
        Map<String, Set<String>> knownSlots = new HashMap<>(); // per professional, to skip duplicates

        for (int i = 1; i < lines.size(); i++) { // line 0 is the header
            if (lines.get(i).isBlank()) continue;
            try {
                if (importRow(parseLine(lines.get(i)), knownSlots)) imported++;
            } catch (RuntimeException e) {
                System.err.println("Line " + (i + 1) + " skipped: " + e.getMessage());
            }
        }
        return imported;
    }

    /** return true if a slot was added, false if it already existed */
    private boolean importRow(List<String> f, Map<String, Set<String>> knownSlots) {
        if (f.size() < 7) {
            throw new IllegalArgumentException("expected 7 columns but found " + f.size());
        }
        String firstName = f.get(0).trim();
        String lastName = f.get(1).trim();
        String title = f.get(2).trim();
        String specialties = f.get(3).trim();
        if (firstName.isEmpty() || lastName.isEmpty()) {
            throw new IllegalArgumentException("first name and last name are required");
        }

        LocalDate date;
        LocalTime start;
        LocalTime end;
        try {
            date = LocalDate.parse(f.get(4).trim());
            start = LocalTime.parse(f.get(5).trim());
            end = LocalTime.parse(f.get(6).trim());
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("invalid date or time (expected yyyy-MM-dd and HH:mm)");
        }
        if (!start.isBefore(end)) {
            throw new IllegalArgumentException("start time must be before end time");
        }

        String id = firstName.toLowerCase() + "-" + lastName.toLowerCase();
        HealthCareProfessional professional = professionalRegistry.findProfessional(id);
        if (professional == null) {
            professional = new HealthCareProfessional(id, firstName + " " + lastName, title);
            professionalRegistry.addProfessional(professional);
        }
        for (String specialty : specialties.split(";")) {
            if (!specialty.isBlank()) professional.addSpecialty(new MedicalSpecialty(specialty.trim()));
        }

        Set<String> known = knownSlots.get(id);
        if (known == null) {
            known = new HashSet<>();
            for (TimeSlot s : professional.getAllSlots()) known.add(key(s.getDate(), s.getStartTime(), s.getEndTime()));
            knownSlots.put(id, known);
        }
        if (!known.add(key(date, start, end))) {
            return false; // duplicate
        }
        professional.addAvailability(date, date, RepetitionPattern.WEEKLY,
                List.of(date.getDayOfWeek()), start, end);
        return true;
    }

    private static String key(LocalDate date, LocalTime start, LocalTime end) {
        return date + "|" + start + "|" + end;
    }

    /** Splits one CSV line; supports "quoted, fields" and "" as an escaped quote. */
    private static List<String> parseLine(String line) {
        if (line.startsWith("\uFEFF")) line = line.substring(1);
        List<String> fields = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (inQuotes) {
                if (c == '"' && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    current.append('"');
                    i++;
                } else if (c == '"') {
                    inQuotes = false;
                } else {
                    current.append(c);
                }
            } else if (c == '"') {
                inQuotes = true;
            } else if (c == ',') {
                fields.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }
        fields.add(current.toString());
        return fields;
    }
}