import java.time.LocalDate;

public class Patient {
    private String patientId;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String phoneNumber;

    public Patient(String patientId, String firstName, String lastName,
                   LocalDate dateOfBirth, String phoneNumber) {
        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
    }

    public String getPatientId() {
        return patientId;
    }
}
