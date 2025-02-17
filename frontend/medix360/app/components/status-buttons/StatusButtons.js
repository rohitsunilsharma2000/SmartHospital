import React from "react";
import "./StatusButtons.css";

const statusConfig = {
  UNAVAILABLE: { class: "danger", icon: "bi-x-circle", label: "Unavailable" },
  AVAILABLE: { class: "success", icon: "bi-check-circle", label: "Available" },
  BOOKED: { class: "warning", icon: "bi-bookmark-x", label: "Booked" },
  ARRIVED: { class: "info", icon: "bi-person-check", label: "Arrived" },
  COMPLETED: { class: "secondary", icon: "bi-check2-circle", label: "Completed" },
  NO_SHOW: { class: "light", icon: "bi-person-x", label: "No Show" },
  WALKIN: { class: "dark", icon: "bi-person-plus", label: "Walk-in" },
  CANCELLED: { class: "danger", icon: "bi-x-octagon", label: "Cancelled" },
  BLOCKED: { class: "muted", icon: "bi-ban", label: "Blocked" },
  RESERVED: { class: "dark", icon: "bi-file-earmark-check", label: "Reserved" }
};

const StatusButtons = ({ slots = [] }) => {
  if (!slots.length) {
    return <span className="text-muted">No Slots</span>;
  }

  return (
    <div className="status-container">
      {slots.map((slot) => (
        <button
          key={slot.slotId}
          type="button"
          className={`btn btn-sm btn-${statusConfig[slot.status]?.class || "secondary"}`}
          data-bs-toggle="tooltip"
          data-bs-placement="top"
          title={`${statusConfig[slot.status]?.label || "Unknown"} (${slot.startTime} - ${slot.endTime})`}
        >
          <i className={`bi ${statusConfig[slot.status]?.icon || "bi-question-circle"}`}></i>
          {/* {statusConfig[slot.status]?.label || "Unknown"} */}
        </button>



      ))}
    </div>
  );
};

export default StatusButtons;
