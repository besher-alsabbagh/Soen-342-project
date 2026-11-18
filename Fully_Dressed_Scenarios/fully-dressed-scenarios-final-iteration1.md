# Fully Dressed Scenarios

## UC1: Book / Confirm Appointment

**Primary Actor:** Administrative Staff

**Stakeholders and Interests:**
- The staff wants fast and accurate booking.
- The patient wants a valid appointment.
- The healthcare professional wants no double bookings.

**Preconditions:** The staff is identified and authenticated.

**Postconditions:** The appointment is confirmed and is linked to the patient and the healthcare professional. The slot is no longer available at the end.

**Main Success Scenario:**
1. Staff searches for the patient with their info.
2. The system finds the matching patient.
3. Staff selects the healthcare professional and slot and requests the appointment.
4. The system verifies availability, scheduling constraints and referral if required, then it creates the appointment.
5. The staff confirms the appointment.
6. The system then sets the status to "confirmed" and returns an appointment ID.

**Extensions:**
- 4a. Slot is unavailable, so the system shows an error and doesn't create an appointment. Staff selects another slot.

**Special Requirements:** The system is a command line interface. Actions must be logged.

**Open Issues:** Can patients book by themselves without the need of staff?


## UC2: Register Patient Arrival

**Primary Actor:** Administrative Staff

**Stakeholders and Interests:**
- Administrative staff want a fast check-in system.
- The patient wants to be checked in without being delayed.
- The healthcare professional wants to know who (patient) has arrived.

**Preconditions:** The staff is identified and authenticated. The patient has a confirmed appointment today.

**Postconditions:** Arrival is recorded with its time.

**Main Success Scenario:**
1. Staff searches for the patient.
2. The system presents the patient.
3. The staff asks for the patient's appointment today.
4. The system presents the appointment.
5. Staff registers the arrival of the patient.
6. The system records the arrival time and confirms.

**Extensions:**
- 4a. There is no appointment today, the system shows that arrival is not registered. Staff may book an appointment (as shown in UC1).

**Special Requirements:** The system is a command line interface. Staff can't see clinical information.

**Open Issues:** How are walk-ins (people without a prior appointment) handled?


## UC3: Record Consultation

**Primary Actor:** Healthcare Professional

**Stakeholders and Interests:**
- The healthcare professional wants quick and complete documentation.
- The patient wants an accurate and confidential record.
- The clinic wants accountability.

**Preconditions:** The professional is identified, authenticated and authorized for clinical data. The patient has arrived.

**Postconditions:** The consultation is saved in the patient's clinical record, and it is linked to that specific appointment. The action is logged.

**Main Success Scenario:**
1. The healthcare professional opens the appointment.
2. The system presents the appointment details.
3. The healthcare professional enters observations, diagnosis, treatment and follow-up requirements.
4. The system saves the consultation, adds it to the clinical record and confirms.

**Extensions:**
- 3a. Access is denied. The system does not save the consultation. The system indicates "access denied" and logs the attempt.

**Special Requirements:** The system is a command line interface. Clinical data is only visible to healthcare professionals who are authorized.

**Open Issues:** Can a saved consultation be modified in any way?
