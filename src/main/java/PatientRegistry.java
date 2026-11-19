import java.util.ArrayList;
import java.util.List;

public class PatientRegistry {
    private List<Patient> patients = new ArrayList<>();

    public Patient findPatient(String patientId) {
        for (Patient patient : patients) {
            if (patientId.equals(patient.getPatientId())) {
                return patient;
            }
        }
        return null;
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
    }
}
