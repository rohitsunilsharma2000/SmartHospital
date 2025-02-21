"use client"
import React, { useState } from 'react';
import MultiSelectSearch from '../../../../utils/MultiSelectSearch';
import StatusMessage from '../../../../utils/StatusMessage';
import { registerDoctor } from '@/utils/restClient';

const DoctorRegistrationForm = () => {
  const [formData, setFormData] = useState({
    startTime: '',
    endTime: '',
    days: [],
    dates: ['', ''], // Index 0: startDate, Index 1: endDate
  });
  
  const [errors, setErrors] = useState({});
  const [formErrors, setFormErrors] = useState({});//track if any form validation error

  const [status, setStatus] = useState(''); // State to track status (loading, success, error) from api response 
  const [errorMessage, setErrorMessage] = useState('');//api error

  const handleChange = (e) => {
    const { name, value, type, checked, dataset } = e.target;
    
    if (name === "dates" && dataset.index !== undefined) {
      const index = Number(dataset.index);
      setFormData((prevState) => {
        const newDates = [...prevState.dates];
        newDates[index] = value;
        return { ...prevState, dates: newDates };
      });
      const errorKey = index === 0 ? 'startDate' : 'endDate';
      if (errors[errorKey]) {
        setErrors((prevErrors) => ({ ...prevErrors, [errorKey]: null }));
      }
    } else {
      setFormData((prevState) => ({
        ...prevState,
        [name]:
          type === "checkbox" ? checked :
          type === "file" ? e.target.files[0] :
          value,
      }));
      if (errors[name]) {
        setErrors((prevErrors) => ({ ...prevErrors, [name]: null }));
      }
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    let valid = true;
    const newErrors = {};

    if (!formData.startTime) {
      newErrors.startTime = "Start Time is required";
      valid = false;
    }

    if (!formData.endTime) {
      newErrors.endTime = "End Time is required";
      valid = false;
    }

    if (!formData.days || formData.days.length === 0) {
      newErrors.days = "Please select at least one day";
      valid = false;
    }

    if (!formData.dates[0]) {
      newErrors.startDate = "Start Date is required";
      valid = false;
    }

    if (!formData.dates[1]) {
      newErrors.endDate = "End Date is required";
      valid = false;
    }

    if (!valid) {
      setErrors(newErrors);
      const firstErrorField = Object.keys(newErrors)[0];
      if (firstErrorField) {
        document.getElementById(firstErrorField)?.focus();
      }
      console.log(
        "❌ Form has missing inputs fields. Fix errors before submitting."
      );
      console.log("Form has validation errors.");
      return;
    }
    else{
      createDoctor(payload)
      alert("✅ Form is valid and ready to submit!");
      console.log("Form Payload for submission:", payload);
      console.log("Form submitted successfully!", formData);
    }

    console.log("Form submitted with data:", formData);
    // Further processing (e.g., API call) goes here
  };

  const clearSelection = (name) => {
    setFormData((prevState) => ({
      ...prevState,
      [name]: [],
    }));
  };

  const daysOfWeek = [
    { id: 1, value: "Monday", label: "Monday" },
    { id: 2, value: "Tuesday", label: "Tuesday" },
    { id: 3, value: "Wednesday", label: "Wednesday" },
    { id: 4, value: "Thursday", label: "Thursday" },
    { id: 5, value: "Friday", label: "Friday" },
    { id: 6, value: "Saturday", label: "Saturday" },
    { id: 7, value: "Sunday", label: "Sunday" },
    { id: 8, value: "All", label: "All" }
  ];

  const payload = {
    doctorData: {
      name: "Dr. Alice Brown",
      department: "Neurology",
      outPatient: true,
      busyTime: "10:00 - 14:00",
      notificationSchedules: ["Hourly"],
    },
    queryParams: {
      startTime: "07:00",
      endTime: "08:00",
      days: ["Monday", "Wednesday"],
      dates: ["2025-02-20", "2025-02-22"],
    }
  };
  

  async function createDoctor(payload) {
    // Set the status to loading before starting the API call
    setStatus('loading');
    try {
      // Call the registerDoctor API with the provided doctor data and query parameters.
      // Assumes payload contains:
      //  - doctorData: the JSON body for the API call
      //  - queryParams: an object with query parameters (e.g., startTime, endTime, days, dates)
      const registerDoctorResponse = await registerDoctor(payload.doctorData, payload.queryParams);
      
      // Update status to indicate success and log the response
      setStatus('success - Doctor registered successfully!');
      console.log("Register Doctor Response: ", registerDoctorResponse);
  
      // Clear form data after a successful registration
      setFormData((prev) => ({
        ...prev,
        name: "",
        department: "",
        outPatient: false,
        busyTime: "",
        notificationSchedules: [],
        // Add additional fields as needed
      }));
  
    } catch (error) {
      // Extract a user-friendly error message
      const errorMessage =
        error.response && error.response.data && error.response.data.message
          ? error.response.data.message
          : 'An unexpected error occurred';
  
      console.error("Failed to register doctor:", errorMessage);
      setStatus('error');
      setErrorMessage(errorMessage);
    } finally {
      // Optionally, you might want to clear status and error message after some time
      // setTimeout(() => {
      //   setStatus('');
      //   setErrorMessage('');
      // }, 10000);
    }
  }
  

  return (
    <section className="section">
       {/* Use the StatusMessage component */}
       <StatusMessage status={status} errorMessage={errorMessage} />
      <div className="row">
        <div className="col-lg-12">
          <div className="card p-4">
            <form onSubmit={handleSubmit}>
              {/* Time Section */}
              <div className="row mb-3">
                <div className="col-md-6">
                  <div className="d-flex align-items-center justify-content-start">
                    <label htmlFor="startTime" className="form-label">
                      Start Time
                    </label>
                    {errors.startTime && (
                      <div className="text-danger ms-2 mb-2">{errors.startTime}</div>
                    )}
                  </div>
                  <input
                    type="time"
                    className="form-control"
                    id="startTime"
                    name="startTime"
                    value={formData.startTime}
                    onChange={handleChange}
                  />
                </div>
                <div className="col-md-6">
                  <div className="d-flex align-items-center justify-content-start">
                    <label htmlFor="endTime" className="form-label">
                      End Time
                    </label>
                    {errors.endTime && (
                      <div className="text-danger ms-2 mb-2">{errors.endTime}</div>
                    )}
                  </div>
                  <input
                    type="time"
                    className="form-control"
                    id="endTime"
                    name="endTime"
                    value={formData.endTime}
                    onChange={handleChange}
                  />
                </div>
              </div>

              {/* Days Multi-select */}
              <div className="mb-3">
                <div className="d-flex align-items-center justify-content-start">
                  <label htmlFor="days" className="form-label">
                    Days
                  </label>
                  {errors.days && (
                    <div className="text-danger ms-2 mb-2">{errors.days}</div>
                  )}
                </div>
                <MultiSelectSearch 
                  id="days"
                  options={daysOfWeek} 
                  name="days" 
                  formData={formData} 
                  handleChange={handleChange} 
                  clearSelection={clearSelection}
                />
              </div>

              {/* Dates Section */}
              <div className="row mb-3">
                <div className="col-md-6">
                  <div className="d-flex align-items-center justify-content-start">
                    <label htmlFor="startDate" className="form-label">
                      Start Date
                    </label>
                    {errors.startDate && (
                      <div className="text-danger ms-2 mb-2">{errors.startDate}</div>
                    )}
                  </div>
                  <input
                    type="date"
                    className="form-control"
                    id="startDate"
                    name="dates"
                    data-index="0"
                    value={formData.dates[0]}
                    onChange={handleChange}
                  />
                </div>
                <div className="col-md-6">
                  <div className="d-flex align-items-center justify-content-start">
                    <label htmlFor="endDate" className="form-label">
                      End Date
                    </label>
                    {errors.endDate && (
                      <div className="text-danger ms-2 mb-2">{errors.endDate}</div>
                    )}
                  </div>
                  <input
                    type="date"
                    className="form-control"
                    id="endDate"
                    name="dates"
                    data-index="1"
                    value={formData.dates[1]}
                    onChange={handleChange}
                  />
                </div>
              </div>

              <button type="submit" className="btn btn-primary">
                Register
              </button>
            </form>
          </div>
        </div>
      </div>
    </section>
  );
};

export default DoctorRegistrationForm;
