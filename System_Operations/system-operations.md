# System Operations

| Use Case | Operation | Changes Data? |
|---|---|---|
| UC1, UC2 | `searchPatient(patientInfo)` | No |
| UC1 | `requestAppointment(patientId, doctorId, slot)` | Yes |
| UC1 | `confirmAppointment(appointmentId)` | Yes |
| UC2 | `findTodayAppointment(patientId)` | No |
| UC2 | `registerArrival(appointmentId)` | Yes |
| UC3 | `openAppointment(appointmentId)` | No |
| UC3 | `recordConsultation(appointmentId, observations, diagnosis, treatment, followUp)` | Yes |
