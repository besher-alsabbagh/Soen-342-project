# Operation Contracts

## CO1: requestAppointment(patientId, doctorId, slot)

**Preconditions:** The patient and professional exist. The selected slot is free.

**Postconditions:**
- An Appointment was created.
- Status was set to requested.
- It is associated with a Patient and HealthcareProfessional.
- StartDateTime, EndDateTime and RequestedAt were set.
- The Appointment was associated with the AdministrativeStaff who made the request.


## CO2: confirmAppointment(appointmentId)

**Preconditions:** An Appointment with status "requested" exists.

**Postconditions:**
- Status was changed to "confirmed".


## CO3: registerArrival(appointmentId)

**Preconditions:** The Appointment exists, the appointment is confirmed and scheduled for today. And it has no Arrival yet.

**Postconditions:**
- An Arrival was created.
- ArrivalTime was set to the current time.
- The Arrival was associated with Appointment and AdministrativeStaff.


## CO4: recordConsultation(appointmentId, observations, diagnosis, treatment, followUp)

**Preconditions:** The Appointment exists, it belongs to the logged-in professional and has an Arrival. The user is authorized for clinical data.

**Postconditions:**
- Consultation was created.
- Observation, Diagnosis, TreatmentRecommendation and FollowUpRequired were set.
- The Consultation was associated with the appointment and added to the patient's ClinicalRecord.
