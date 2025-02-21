
### **Workflow of the Hospital Information System (HIS)**

---
<a href="https://www.youtube.com/watch?v=r6TfQtHvqX4&t=1640s">Demo App</a>

#### **1. User Authentication & Access Control**
- User logs in using unique credentials.
- System verifies role-based access and permissions.

<img src="https://github.com/rohitsunilsharma2000/SmartHospital/blob/main/HIMS%20WorkFlow.png?raw=true"/>

---

#### **2. Outpatient Department (OPD) Workflow**
1. **Appointment Booking**  
    - Patient books an appointment via:  
        - Website or mobile app  
        - IVR system  
        - Hospital helpline (staff-assisted)  
    - System sends SMS/email confirmation to the patient.

2. **Patient Arrival & Registration**  
    - Staff verifies if the patient is registered.  
    - If not, staff registers the patient by entering demographic details.  
    - System generates a unique hospital ID.

3. **Billing & Queue Management**  
    - Staff selects the appointment and creates a consultation bill.  
    - Payment mode chosen (cash, credit card, insurance).  
    - System marks the patient as “arrived” and adds them to the doctor’s queue.

4. **Doctor Consultation (EMR)**  
    - Doctor selects the patient from the queue and creates an EMR.  
    - Doctor records chief complaint, vitals, medical history, allergies, and examination findings.  
    - Doctor prescribes tests and medications.

5. **Lab Tests**  
    - Billing staff creates a test bill based on the doctor’s prescription.  
    - Lab technician collects samples and enters results manually or via lab machines.  
    - Authorized lab report is printed and uploaded to EMR for doctor’s review.

6. **Pharmacy**  
    - Prescription automatically appears in the pharmacy queue.  
    - Pharmacist selects batch numbers and quantities, processes the bill, and prints the receipt.  
    - System updates pharmacy stock in real time.

7. **Follow-Up & Discharge**  
    - Doctor sets follow-up dates, triggering automatic SMS reminders.  
    - After consultation, the EMR is saved and printed for the patient.

---

#### **3. Inpatient Department (IP) Workflow**
1. **Cost Estimation**  
    - Staff creates a cost estimate based on room type, treatment packages, and expected duration.  
    - Estimate is printed and shared with the patient.

2. **Bed Reservation**  
    - Staff reserves a bed in advance, collecting an advance payment.

3. **Patient Admission**  
    - Staff selects the patient using the hospital ID.  
    - Admission type (Normal/MLC) and attending doctor are selected.  
    - Insurance details are entered if applicable.  
    - System assigns the reserved bed and adds the advance payment to the IP bill.

4. **Treatment & Medical Records (Nursing Station)**  
    - Doctor records clinical data, nursing staff logs vital signs and procedures.  
    - Investigations and medications are ordered through the system.  
    - Pharmacy indents are sent for medication requests.  
    - Input-output charts are updated manually or automatically (IV fluids).

5. **Lab Tests & Pharmacy**  
    - Lab technician processes samples and enters results, which become available in EMR.  
    - Pharmacy dispenses medicines based on indents, updating the IP bill.

6. **Bed Transfer**  
    - If needed, patient is transferred to another bed using “Bed Transfer” or “Assign and Release” options.  
    - System ensures no double assignments of occupied beds.

7. **Billing & Finalization**  
    - Room charges are added based on the stay duration.  
    - Additional charges (consultations, oxygen, nebulization) are added from nursing station entries.  
    - IP packages with predefined service limits are applied, with additional costs added as needed.

8. **Patient Discharge**  
    - Staff initiates discharge, locking further billing entries.  
    - Billing staff finalizes the bill, collecting any remaining payments.  
    - Insurance payments are processed if applicable.  
    - Final bill is printed and provided to the patient.  
    - Discharge summary is generated, printed, and given to the patient.  
    - System releases the occupied bed, marking it as “Available.”

---

#### **4. Reporting & Analytics Workflow**
1. **OPD Reports**  
    - Appointment statistics by day, week, and month.  
    - Doctor in/out reports, SMS logs, and waiting time analysis.  
    - Payments summary and user-wise collections.

2. **Pharmacy Reports**  
    - Expiring stock, low stock, vendor payments, GST sales, and profit reports.  
    - Current stock status and opening/closing balances.

3. **IP Reports**  
    - Bed occupancy, room charges, and prescription reports.  
    - Discharged patient reports and quarterly patient data exports.

4. **EMR Reports for Research**  
    - Download selected EMR sections based on clinical criteria (e.g., weight > 60 kg).  
    - Supports data extraction for clinical research and paper publication.

---

#### **5. System Integrations**
- Web portal and mobile app integration using APIs.  
- Automated IVR system for multi-language appointment booking.  
- Lab machine interface for direct test result population.  
- Pharmacy integration with real-time stock updates.

---

This workflow ensures a seamless and efficient process across OPD, IP, pharmacy, and lab modules, enhancing patient care and operational efficiency.
