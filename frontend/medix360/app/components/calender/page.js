"use client";
import React, { useState } from "react";
import "./calender.css";
import StatusButtons from "../status-buttons/StatusButtons";

export default function DoctorAvailabilityCalendarPage() {
  // List of doctors (can be fetched dynamically)
  const doctorData = [{
    doctorId: 1,
    doctorName: "Dr. John Doe",
    department: "Cardiology",
    slots: {
      "07:00 AM": [
        { slotId: 101, startTime: "07:00", endTime: "07:10", status: "AVAILABLE" },
        { slotId: 102, startTime: "07:10", endTime: "07:20", status: "AVAILABLE" },
        { slotId: 103, startTime: "07:20", endTime: "07:30", status: "WALKIN" },
        { slotId: 104, startTime: "07:30", endTime: "07:40", status: "CANCELLED" },
        { slotId: 105, startTime: "07:40", endTime: "07:50", status: "BLOCKED" },
        { slotId: 106, startTime: "07:50", endTime: "08:00", status: "RESERVED" }
      ],
      "09:00 PM": [
        { slotId: 107, startTime: "08:00", endTime: "08:10", status: "UNAVAILABLE" },
        { slotId: 108, startTime: "08:10", endTime: "08:20", status: "AVAILABLE" },
        { slotId: 109, startTime: "08:20", endTime: "08:30", status: "BOOKED" },
        { slotId: 110, startTime: "08:30", endTime: "08:40", status: "ARRIVED" },
        { slotId: 111, startTime: "08:40", endTime: "08:50", status: "COMPLETED" },
        { slotId: 112, startTime: "08:50", endTime: "09:00", status: "NO_SHOW" }
      ]
    }
  }, {

    doctorId: 2,
    doctorName: "Dr. John Doe",
    department: "Cardiology",
    slots: {
      "07:00 AM": [
        { slotId: 101, startTime: "07:00", endTime: "07:10", status: "AVAILABLE" },
        { slotId: 102, startTime: "07:10", endTime: "07:20", status: "AVAILABLE" },
        { slotId: 103, startTime: "07:20", endTime: "07:30", status: "WALKIN" },
        { slotId: 104, startTime: "07:30", endTime: "07:40", status: "CANCELLED" },
        { slotId: 105, startTime: "07:40", endTime: "07:50", status: "BLOCKED" },
        { slotId: 106, startTime: "07:50", endTime: "08:00", status: "RESERVED" }
      ],
      "09:00 PM": [
        { slotId: 107, startTime: "08:00", endTime: "08:10", status: "UNAVAILABLE" },
        { slotId: 108, startTime: "08:10", endTime: "08:20", status: "AVAILABLE" },
        { slotId: 109, startTime: "08:20", endTime: "08:30", status: "BOOKED" },
        { slotId: 110, startTime: "08:30", endTime: "08:40", status: "ARRIVED" },
        { slotId: 111, startTime: "08:40", endTime: "08:50", status: "COMPLETED" },
        { slotId: 112, startTime: "08:50", endTime: "09:00", status: "NO_SHOW" }
      ]
    }

  }];

  const hours = [
    "07:00 AM", "08:00 AM", "09:00 AM", "10:00 AM", "11:00 AM",
    "12:00 PM", "01:00 PM", "02:00 PM", "03:00 PM", "04:00 PM",
    "05:00 PM", "06:00 PM", "07:00 PM", "08:00 PM", "09:00 PM"
  ];

  const formatHour = (hour) => {
    const amPm = hour < 12 ? "AM" : "PM";
    const formattedHour = hour % 12 === 0 ? 12 : hour % 12;
    return `${String(formattedHour).padStart(2, "0")}:00 ${amPm}`;
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
                    {/* <select
                      className="form-select"
                      value={selectedDoctor}
                      onChange={(e) => setSelectedDoctor(e.target.value)}
                    >
                      {doctorList.map((doctor) => (
                        <option key={doctor} value={doctor}>
                          {doctor}
                        </option>
                      ))}
                    </select> */}
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
                          {hours.map((hour) => (
                            <th key={hour}>{hour}</th>
                          ))}
                        </tr>
                      </thead>
                      <tbody>

                        {doctorData.map((doctor) => (
                          <tr key={doctor.doctorId}>
                            <td>
                              <p className="shift-time">{doctor.doctorName}</p>
                            </td>
                            <td>
                              <div className="d-flex flex-column align-items-center p-1">
                                <div className="shift-block">
                                  <p className="shift-time">10:00 AM - 01:20 PM</p>
                                  <p className="shift-type">Consultation</p>
                                  <button className="btn btn-xs btn-outline-primary">Edit</button>
                                </div>

                                <div className="shift-block">
                                  <p className="shift-time">02:00 PM - 08:00 PM</p>
                                  <p className="shift-type">Consultation Video</p>
                                  <button className="btn btn-xs btn-outline-primary">Edit</button>
                                </div>
                              </div>
                            </td>

                            {hours.map((hour) => {
                              const slots = doctor.slots[hour];
                              // Pick a representative slot (taking the first slot of the hour)
                              const representativeSlot = slots ? slots[0] : null;

                              return (
                                <td key={`${doctor.doctorId}-${hour}`}>
                                  {doctor.slots[hour] ? (
                                    <StatusButtons slots={doctor.slots[hour]} />
                                  ) : (
                                    <span className="text-muted">No Slots</span>
                                  )}
                                </td>

                              );
                            })}

                          </tr>
                        ))}



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
              <ul className="list-group">
                <li className="list-group-item">
                  <span className="badge bg-danger">Unavailable</span>: The slot is not available for booking.
                </li>
                <li className="list-group-item">
                  <span className="badge bg-success">Available</span>: The slot is open and can be booked.
                </li>
                <li className="list-group-item">
                  <span className="badge bg-warning">Booked</span>: The slot has been reserved by a patient.
                </li>
                <li className="list-group-item">
                  <span className="badge bg-info">Arrived</span>: The patient has arrived for their appointment.
                </li>
                <li className="list-group-item">
                  <span className="badge bg-secondary">Completed</span>: The appointment has been successfully completed.
                </li>
                <li className="list-group-item">
                  <span className="badge bg-light text-dark">No Show</span>: The patient did not attend the appointment.
                </li>
                <li className="list-group-item">
                  <span className="badge bg-dark">Walk-in</span>: The slot was used by a walk-in patient without prior booking.
                </li>
                <li className="list-group-item">
                  <span className="badge bg-danger">Cancelled</span>: The appointment was cancelled by the patient or hospital.
                </li>
                <li className="list-group-item">
                  <span className="badge bg-muted">Blocked</span>: The slot is blocked and cannot be scheduled.
                </li>
                <li className="list-group-item">
                  <span className="badge bg-dark">Reserved</span>: The slot is reserved for a specific case or emergency.
                </li>
              </ul>

            </div>
          </div>

        </div>
      </section>
    </>
  );
}
