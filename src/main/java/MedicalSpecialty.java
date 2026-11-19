import java.util.Objects;

public class MedicalSpecialty {
    private final String name;

    public MedicalSpecialty(String name) { this.name = name; }

    public String getName() { return name; }

    @Override
    public boolean equals(Object o) {
        return o instanceof MedicalSpecialty && name.equalsIgnoreCase(((MedicalSpecialty) o).name);
    }

    @Override
    public int hashCode() { return Objects.hash(name.toLowerCase()); }
}
