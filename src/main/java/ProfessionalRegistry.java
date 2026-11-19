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

    // Pass null for professionalId or specialty to ignore that filter
    public List<HealthCareProfessional> findProfessionals(String professionalId, String specialty) {
        List<HealthCareProfessional> result = new ArrayList<>();
        for (HealthCareProfessional professional : professionals) {
            boolean idMatches = professionalId == null
                    || professionalId.equals(professional.getProfessionalId());
            boolean specialtyMatches = specialty == null
                    || professional.hasSpecialty(specialty);
            if (idMatches && specialtyMatches) {
                result.add(professional);
            }
        }
        return result;
    }

}
