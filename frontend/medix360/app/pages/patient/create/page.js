"use client"
import React, { useState } from 'react';
import StatusMessage from '../../../../utils/StatusMessage';
import { registerPatient } from '@/utils/restClient';

const PatientRegistrationForm = () => {
  // Define the initial state for all fields
  const initialState = {
    // Personal Information
    firstName: '',
    lastName: '',
    age: '',
    sex: '',
    dateOfBirth: '',
    motherTongue: '',
    maritalStatus: '',
    bloodGroup: '',
    education: '',
    occupation: '',
    religion: '',
    birthWeight: '',
    nationality: '',
    // Contact Information
    mobile: '',
    landline: '',
    email: '',
    alternateContact: '',
    address: '',
    area: '',
    city: '',
    pinCode: '',
    state: '',
    country: '',
    // Identification Information
    govtId: '',
    govtIdNumber: '',
    fileNumber: '',
    // Hospital/Patient Information
    hospId: '',
    patientTag: '',
    registered: false,
    otherHospitalIds: '',
    patientPhoto: '',
    notes: '',
    // Referrer Information
    referrerType: '',
    referrerName: '',
    referrerNumber: '',
    referrerEmail: '',
    // Family Information
    fatherName: '',
    motherName: '',
    spouseName: '',
    // Additional Information
    ivrLanguage: '',
  };

  const [formData, setFormData] = useState(initialState);
  const [errors, setErrors] = useState({});
  const [status, setStatus] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  // Handle input changes
  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : value,
    }));
    if (errors[name]) {
      setErrors(prev => ({ ...prev, [name]: null }));
    }
  };

  // Basic validation: here we validate a few key fields. You can add validations for others as needed.
  const handleSubmit = async (e) => {
    e.preventDefault();
    let valid = true;
    const newErrors = {};

    // For demonstration, require firstName, lastName, mobile, email, and dateOfBirth.
    if (!formData.firstName) {
      newErrors.firstName = "First Name is required";
      valid = false;
    }
    if (!formData.lastName) {
      newErrors.lastName = "Last Name is required";
      valid = false;
    }
    if (!formData.mobile) {
      newErrors.mobile = "Mobile number is required";
      valid = false;
    }
    if (!formData.email) {
      newErrors.email = "Email is required";
      valid = false;
    }
    if (!formData.dateOfBirth) {
      newErrors.dateOfBirth = "Date of Birth is required";
      valid = false;
    }

    if (!valid) {
      setErrors(newErrors);
      const firstErrorField = Object.keys(newErrors)[0];
      if (firstErrorField) {
        document.getElementById(firstErrorField)?.focus();
      }
      console.log("❌ Form has validation errors.");
      return;
    }

    setStatus('loading');
    try {
      const response = await registerPatient(formData);
      setStatus('success - Patient registered successfully!');
      console.log("Patient Registration Response:", response);
      setFormData(initialState);
    } catch (error) {
      const msg = error.response && error.response.data && error.response.data.message 
        ? error.response.data.message 
        : 'An unexpected error occurred';
      console.error("Failed to register patient:", msg);
      setStatus('error');
      setErrorMessage(msg);
    }
    
  };

  // Helper: Render a field with label and error in one line
  const renderField = (field, label, type = "text", options = null) => (
    <div className="col-md-4 mb-3" key={field}>
      <div className="d-flex align-items-center justify-content-start">
        <label htmlFor={field} className="form-label">{label}</label>
        {errors[field] && <div className="text-danger ms-2 mb-2">{errors[field]}</div>}
      </div>
      {options ? (
        <select
          className="form-control"
          id={field}
          name={field}
          value={formData[field]}
          onChange={handleChange}
        >
          <option value="">Select {label}</option>
          {options.map((opt, idx) => (
            <option key={idx} value={opt}>{opt}</option>
          ))}
        </select>
      ) : (
        <input
          type={type}
          className="form-control"
          id={field}
          name={field}
          value={formData[field]}
          onChange={handleChange}
        />
      )}
    </div>
  );

  return (
    <section className="section">
      <StatusMessage status={status} errorMessage={errorMessage} />
      <div className="row">
        <div className="col-lg-12">
          <div className="card p-4">
            <form onSubmit={handleSubmit}>
              {/* Personal Information */}
              <h3>Personal Information</h3>
              <div className="row">
                {renderField("firstName", "First Name")}
                {renderField("lastName", "Last Name")}
                {renderField("age", "Age", "number")}
              </div>
              <div className="row">
                {renderField("sex", "Sex", "text", ["Male", "Female", "Other"])}
                {renderField("dateOfBirth", "Date of Birth", "date")}
                {renderField("motherTongue", "Mother Tongue")}
              </div>
              <div className="row">
                {renderField("maritalStatus", "Marital Status", "text", ["Single", "Married", "Divorced", "Widowed"])}
                {renderField("bloodGroup", "Blood Group")}
                {renderField("education", "Education")}
              </div>
              <div className="row">
                {renderField("occupation", "Occupation")}
                {renderField("religion", "Religion")}
                {renderField("birthWeight", "Birth Weight")}
              </div>
              <div className="row">
                {renderField("nationality", "Nationality")}
              </div>

              {/* Contact Information */}
              <h3>Contact Information</h3>
              <div className="row">
                {renderField("mobile", "Mobile")}
                {renderField("landline", "Landline")}
                {renderField("email", "Email", "email")}
              </div>
              <div className="row">
                {renderField("alternateContact", "Alternate Contact")}
                {renderField("address", "Address")}
                {renderField("area", "Area")}
              </div>
              <div className="row">
                {renderField("city", "City")}
                {renderField("pinCode", "Pin Code")}
                {renderField("state", "State")}
              </div>
              <div className="row">
                {renderField("country", "Country")}
              </div>

              {/* Identification Information */}
              <h3>Identification Information</h3>
              <div className="row">
                {renderField("govtId", "Government ID Type", "text", ["Aadhar", "Passport", "Driver License"])}
                {renderField("govtIdNumber", "Government ID Number")}
                {renderField("fileNumber", "File Number")}
              </div>

              {/* Hospital/Patient Information */}
              <h3>Hospital/Patient Information</h3>
              <div className="row">
                {renderField("hospId", "Hospital ID")}
                {renderField("patientTag", "Patient Tag")}
                <div className="col-md-4 mb-3">
                  <div className="d-flex align-items-center justify-content-start">
                    <label htmlFor="registered" className="form-label">Registered</label>
                    {errors.registered && <div className="text-danger ms-2 mb-2">{errors.registered}</div>}
                  </div>
                  <div className="form-check">
                    <input
                      type="checkbox"
                      className="form-check-input"
                      id="registered"
                      name="registered"
                      checked={formData.registered}
                      onChange={handleChange}
                    />
                    <label className="form-check-label" htmlFor="registered">Yes</label>
                  </div>
                </div>
              </div>
              <div className="row">
                {renderField("otherHospitalIds", "Other Hospital IDs")}
                {renderField("patientPhoto", "Patient Photo URL")}
                {renderField("notes", "Notes")}
              </div>

              {/* Referrer Information */}
              <h3>Referrer Information</h3>
              <div className="row">
                {renderField("referrerType", "Referrer Type")}
                {renderField("referrerName", "Referrer Name")}
                {renderField("referrerNumber", "Referrer Number")}
              </div>
              <div className="row">
                {renderField("referrerEmail", "Referrer Email", "email")}
              </div>

              {/* Family Information */}
              <h3>Family Information</h3>
              <div className="row">
                {renderField("fatherName", "Father's Name")}
                {renderField("motherName", "Mother's Name")}
                {renderField("spouseName", "Spouse Name")}
              </div>

              {/* Additional Information */}
              <h3>Additional Information</h3>
              <div className="row">
                {renderField("ivrLanguage", "IVR Language")}
              </div>

              <button type="submit" className="btn btn-primary">Register Patient</button>
            </form>
          </div>
        </div>
      </div>
    </section>
  );
};

export default PatientRegistrationForm;
