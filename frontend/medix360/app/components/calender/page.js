"use client";
import React, { useState } from "react";
import "./calender.css";
import StatusButtons from "../status-buttons/StatusButtons";

export default function DoctorAvailabilityCalendarPage() {
  // List of doctors (can be fetched dynamically)
  const doctorList = [
    "Dr. Kailash Gokra",
    "Dr. Smith Johnson",
    "Dr. Alice Brown",
  ];

  // Initialize state with dynamic doctors
  const initialDoctorState = doctorList.reduce((acc, doctor) => {
    acc[doctor] = {
      unavailable: false,
      available: true,
      booked: false,
      additional: false,
      arrived: false,
      completed: false,
      walkin: false,
      blocked: false,
      noShow: false,
      reserved: false,
    };
    return acc;
  }, {});

  const [doctorAvailability, setDoctorAvailability] =
    useState(initialDoctorState);
  const [selectedDoctor, setSelectedDoctor] = useState(doctorList[0]); // Default to the first doctor

  // Generic handler to update any doctor's availability
  const handleDoctorAvailabilityChange = (doctor, status) => {
    setDoctorAvailability((prevState) => ({
      ...prevState,
      [doctor]: {
        ...prevState[doctor],
        [status]: !prevState[doctor][status], // Toggle the specific status
      },
    }));
  };

  return (
    <>
      <div className="pagetitle">
        <h1>Doctor Availability Calendar</h1>
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
            <div className="card">
              <div className="card-body">
                {/* Department & Doctor Selection */}
                <div className="row mt-2">
                  <div className="col-md-6 d-flex gap-2 mb-3">
                    <label htmlFor="department" className="col-form-label">
                      Dept:
                    </label>
                    <select className="form-select">
                      <option value="">Choose Department</option>
                      <option value="Cardiology">Cardiology</option>
                      <option value="Paediatrics">Paediatrics</option>
                      <option value="Neurology">Neurology</option>
                    </select>

                    <label htmlFor="doctor" className="col-form-label">
                      Dr:
                    </label>
                    <select
                      className="form-select"
                      value={selectedDoctor}
                      onChange={(e) => setSelectedDoctor(e.target.value)}
                    >
                      {doctorList.map((doctor) => (
                        <option key={doctor} value={doctor}>
                          {doctor}
                        </option>
                      ))}
                    </select>
                  </div>

                  {/* Navigation Buttons */}
                  <div className="col-6 text-end mb-3">
                    <div className="btn-group" role="group">
                      <button type="button" className="btn btn-outline-primary">
                        Left
                      </button>
                      <button type="button" className="btn btn-outline-primary">
                        Middle
                      </button>
                      <button type="button" className="btn btn-outline-primary">
                        Middle
                      </button>
                      <button type="button" className="btn btn-outline-primary">
                        Right
                      </button>
                    </div>
                  </div>

                  {/* Calendar Date & Time Slots */}
                  <div className="col-3 mt-1 d-flex gap-2 mb-3">
                    <p className="card-title p-0">Wednesday, 25 May, 2022</p>
                  </div>
                  <div className="col-8 mt-1 d-flex gap-2 mb-3">
                    <h6 className="basic p-0">12am-7am</h6>
                    <h6 className="basic p-0">7am-10pm</h6>
                    <h6 className="basic p-0">10pm-12am</h6>
                    <input type="date" className="form-control-s" />
                    <div className="btn-group" role="group">
                      <button type="button" className="btn btn-primary">
                        Prev
                      </button>
                      <button type="button" className="btn btn-primary">
                        Today
                      </button>
                      <button type="button" className="btn btn-primary">
                        Day
                      </button>
                      <button type="button" className="btn btn-primary">
                        Next
                      </button>
                    </div>
                  </div>

                  {/* Refresh Button */}
                  <div className="col-1 mt-1 text-end mb-3">
                    <button
                      type="button"
                      className="btn btn-outline-primary p-0"
                    >
                      Refresh <i className="bi bi-arrow-repeat ms-2"></i>
                    </button>
                  </div>

                  {/* Availability Table */}
                  <div className="col-12 mb-3">
                    <table className="table table-bordered">
                      <thead>
                        <tr>
                          <th scope="col">Doctor</th>
                          <th scope="col">Availability</th>
                          <th scope="col">7 AM</th>
                          <th scope="col">8 AM</th>
                          <th scope="col">9 AM</th>
                          <th scope="col">10 AM</th>
                          <th scope="col">11 AM</th>
                          <th scope="col">12 PM</th>
                          <th scope="col">1 PM</th>
                          <th scope="col">2 PM</th>
                          <th scope="col">3 PM</th>
                          <th scope="col">4 PM</th>
                          <th scope="col">5 PM</th>
                          <th scope="col">6 PM</th>
                          <th scope="col">7 PM</th>
                          <th scope="col">8 PM</th>
                          <th scope="col">9 PM</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr>
                          <th scope="row">{selectedDoctor}</th>
                          <td>
                            <div className="d-flex align-items-center flex-column mb-3">
                              <p>10:00 AM - 01:20 PM</p>
                              <p>Consultation</p>
                              <p>Edit Shift Block Print</p>
                              <p>02:00 PM - 08:00 PM</p>
                              <p>Consultation Video</p>
                              <p>Edit Shift Block Print</p>
                            </div>
                          </td>
                          {[...Array(13)].map((_, index) => (
                            <td key={index}>
                              <StatusButtons
                                availability={
                                  doctorAvailability[selectedDoctor]
                                }
                                onStatusChange={(status) =>
                                  handleDoctorAvailabilityChange(
                                    selectedDoctor,
                                    status
                                  )
                                }
                              />
                            </td>
                          ))}
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div className="row mb-3">
  <div className="col-12">
    <p><strong>Color Meanings:</strong></p>
    <ul>
      <li><span className="badge bg-danger">Unavailable</span>: Indicates that the slot is not available for booking or selection.</li>
      <li><span className="badge bg-success">Available</span>: Represents an open or available time slot.</li>
      <li><span className="badge bg-warning">Booked</span>: Indicates that the slot has been reserved or booked.</li>
      <li><span className="badge bg-primary">Additional Slots</span>: Represents additional time slots that may be available.</li>
      <li><span className="badge bg-info">Arrived</span>: Indicates that the patient or individual has arrived for the appointment.</li>
      <li><span className="badge bg-secondary">Completed</span>: Represents that the appointment or task has been completed.</li>
      <li><span className="badge bg-dark">Walkin</span>: Represents a walk-in client or individual, who doesn't have an appointment.</li>
      <li><span className="badge bg-muted">Blocked</span>: Indicates that the slot is blocked and cannot be used.</li>
      <li><span className="badge bg-light">No Show</span>: Indicates that the patient or individual didn't show up for the appointment.</li>
      <li><span className="badge bg-dark">Reserved</span>: Represents a reserved slot for a particular individual or event.</li>
    </ul>
  </div>
</div>

        </div>
      </section>
    </>
  );
}
