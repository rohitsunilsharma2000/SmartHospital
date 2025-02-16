"use client";

import React, { useState } from 'react';

const AppointmentAdd = () => {
  const [formData, setFormData] = useState({
    searchBy: '',
    patientName: '',
    patientNumber: '',
    visitType: 'firstTime',
    source: '',
    internationalPatient: false,
    doctor: 'Kailash Gokral',
    visitTypeDetail: 'Consultation',
    appointmentDate: '2022-05-25',
    appointmentTime: '15:00',
    appointmentDetails: '',
    markAsArrived: false,
    isWalkin: false,
    needsAttention: false,
    enableRepeats: false,
  });

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: type === 'checkbox' ? checked : value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Handle form submission (e.g., save to database or API)
    console.log(formData);
  };

  return (
    <>
    <div className="pagetitle">
      <h1>Add Appointment </h1>
      <nav>
        <ol className="breadcrumb">
          <li className="breadcrumb-item">
            <a href="index.html">Home</a>
          </li>
          <li className="breadcrumb-item">Pages</li>
          <li className="breadcrumb-item active">Doctor Availability</li>
        </ol>
      </nav>
    </div>
    <section className="section">
    <div className="row">
      <div className="col-lg-12">
      <div className="card p-4">

        <form onSubmit={handleSubmit}>
          {/* Patient Details Section */}
          <div className="row mb-3">
            <div className="col-md-4">
              <label htmlFor="searchBy" className="form-label">Search By</label>
              <select
                className="form-select"
                id="searchBy"
                name="searchBy"
                value={formData.searchBy}
                onChange={handleChange}
              >
                <option value="">Choose Name or Number</option>
                <option value="name">Name</option>
                <option value="number">Number</option>
              </select>
            </div>
            <div className="col-md-4">
              <label htmlFor="patientName" className="form-label">Full Name</label>
              <input
                type="text"
                className="form-control"
                id="patientName"
                name="patientName"
                value={formData.patientName}
                onChange={handleChange}
                placeholder="Enter Full Name"
              />
            </div>
            <div className="col-md-4">
              <label htmlFor="patientNumber" className="form-label">Number</label>
              <input
                type="text"
                className="form-control"
                id="patientNumber"
                name="patientNumber"
                value={formData.patientNumber}
                onChange={handleChange}
                placeholder="Enter Number"
              />
            </div>
          </div>

          {/* About the Patient */}
          <div className="row mb-3">
            <div className="col-md-4">
              <label className="form-label">About the Patient</label>
              <div className="form-check">
                <input
                  className="form-check-input"
                  type="radio"
                  name="visitType"
                  id="firstTimeVisit"
                  value="firstTime"
                  checked={formData.visitType === 'firstTime'}
                  onChange={handleChange}
                />
                <label className="form-check-label" htmlFor="firstTimeVisit">
                  First-Time Visit
                </label>
              </div>
              <div className="form-check">
                <input
                  className="form-check-input"
                  type="radio"
                  name="visitType"
                  id="reVisit"
                  value="reVisit"
                  checked={formData.visitType === 'reVisit'}
                  onChange={handleChange}
                />
                <label className="form-check-label" htmlFor="reVisit">
                  Re-Visit
                </label>
              </div>
            </div>
            <div className="col-md-4">
              <label htmlFor="source" className="form-label">Source</label>
              <select
                className="form-select"
                id="source"
                name="source"
                value={formData.source}
                onChange={handleChange}
              >
                <option value="">--Select--</option>
              </select>
              <small>How did you hear about Nirmal Clinic?</small>
            </div>
            <div className="col-md-4">
              <div className="form-check">
                <input
                  className="form-check-input"
                  type="checkbox"
                  id="internationalPatient"
                  name="internationalPatient"
                  checked={formData.internationalPatient}
                  onChange={handleChange}
                />
                <label className="form-check-label" htmlFor="internationalPatient">
                  International Patient
                </label>
              </div>
            </div>
          </div>

          {/* Doctor & Treatment Details */}
          <div className="row mb-3">
            <div className="col-md-4">
              <label htmlFor="doctor" className="form-label">Doctor</label>
              <select
                className="form-select"
                id="doctor"
                name="doctor"
                value={formData.doctor}
                onChange={handleChange}
              >
                <option value="Kailash Gokral">Kailash Gokral</option>
              </select>
            </div>
            <div className="col-md-4">
              <label htmlFor="visitTypeDetail" className="form-label">Visit Type</label>
              <select
                className="form-select"
                id="visitTypeDetail"
                name="visitTypeDetail"
                value={formData.visitTypeDetail}
                onChange={handleChange}
              >
                <option value="Consultation">Consultation</option>
              </select>
            </div>
            <div className="col-md-4">
              <label htmlFor="treatmentDetails" className="form-label">Treatment Details</label>
              <button type="button" className="btn btn-primary w-100">Get Treatments</button>
            </div>
          </div>

          {/* Appointment Time Section */}
          <div className="row mb-3">
            <div className="col-md-4">
              <label htmlFor="appointmentDate" className="form-label">Time</label>
              <div className="d-flex">
                <input
                  type="date"
                  className="form-control"
                  id="appointmentDate"
                  name="appointmentDate"
                  value={formData.appointmentDate}
                  onChange={handleChange}
                />
                <input
                  type="time"
                  className="form-control"
                  id="appointmentTime"
                  name="appointmentTime"
                  value={formData.appointmentTime}
                  onChange={handleChange}
                />
              </div>
              <button type="button" className="btn btn-secondary mt-2">Change Time</button>
            </div>
            <div className="col-md-4">
              <label htmlFor="appointmentDetails" className="form-label">Details</label>
              <input
                type="text"
                className="form-control"
                id="appointmentDetails"
                name="appointmentDetails"
                value={formData.appointmentDetails}
                onChange={handleChange}
                placeholder="Enter Appointment Details"
              />
            </div>
            <div className="col-md-4">
              <div className="form-check">
                <input
                  className="form-check-input"
                  type="checkbox"
                  id="markAsArrived"
                  name="markAsArrived"
                  checked={formData.markAsArrived}
                  onChange={handleChange}
                />
                <label className="form-check-label" htmlFor="markAsArrived">
                  Mark Apt as Arrived
                </label>
              </div>
              <div className="form-check">
                <input
                  className="form-check-input"
                  type="checkbox"
                  id="isWalkin"
                  name="isWalkin"
                  checked={formData.isWalkin}
                  onChange={handleChange}
                />
                <label className="form-check-label" htmlFor="isWalkin">
                  Is Walkin
                </label>
              </div>
              <div className="form-check">
                <input
                  className="form-check-input"
                  type="checkbox"
                  id="needsAttention"
                  name="needsAttention"
                  checked={formData.needsAttention}
                  onChange={handleChange}
                />
                <label className="form-check-label" htmlFor="needsAttention">
                  Needs Attention
                </label>
              </div>
            </div>
          </div>

          {/* Enable Repeats Section */}
          <div className="row mb-3">
            <div className="col-md-4">
              <div className="form-check">
                <input
                  className="form-check-input"
                  type="checkbox"
                  id="enableRepeats"
                  name="enableRepeats"
                  checked={formData.enableRepeats}
                  onChange={handleChange}
                />
                <label className="form-check-label" htmlFor="enableRepeats">
                  Enable Repeats
                </label>
              </div>
            </div>
          </div>

          {/* Submit Button */}
          <div className="text-center">
            <button type="submit" className="btn btn-success btn-lg">Add Appointment</button>
          </div>
        </form>
      </div>
    </div>
    </div>
    </section>
    </>
  );
};

export default AppointmentAdd;
