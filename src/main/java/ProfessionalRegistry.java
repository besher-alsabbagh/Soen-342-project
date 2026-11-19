import java.util.ArrayList;
import java.util.List;

public class ProfessionalRegistry {
    private List<HealthCareProfessional> professionals = new ArrayList<>();

    public HealthCareProfessional findProfessional(String professionalId) {
        for (HealthCareProfessional professional : professionals) {
            if (professionalId.equals(professional.getProfessionalId())) {
                return professional;
            }
        }
        return null;
    }

public void addProfessional(HealthCareProfessional professional) {
    professionals.add(professional);
}

public List<HealthCareProfessional> getAllProfessionals() {
        return professionals;
    }

}
