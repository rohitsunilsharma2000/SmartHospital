import React from 'react';

const StatusButtons = ({ availability, onStatusChange }) => {
  return (
    <div>
      <div className="row">
        <div className="col-6">
          <button
            type="button"
            className={`btn btn-sm ${availability.unavailable ? 'btn-danger' : 'btn-outline-danger'}`}
            data-bs-toggle="tooltip"
            data-bs-placement="top"
            title="Unavailable"
            onClick={() => onStatusChange('unavailable')}
          >
            <i className="bi bi-x-circle"></i>
          </button>
        </div>
        <div className="col-6">
          <button
            type="button"
            className={`btn btn-sm ${availability.available ? 'btn-success' : 'btn-outline-success'}`}
            data-bs-toggle="tooltip"
            data-bs-placement="top"
            title="Available"
            onClick={() => onStatusChange('available')}
          >
            <i className="bi bi-check-circle"></i>
          </button>
        </div>
      </div>
      <div className="row mt-2">
        <div className="col-6">
          <button
            type="button"
            className={`btn btn-sm ${availability.booked ? 'btn-warning' : 'btn-outline-warning'}`}
            data-bs-toggle="tooltip"
            data-bs-placement="top"
            title="Booked"
            onClick={() => onStatusChange('booked')}
          >
            <i className="bi bi-bookmark-x"></i>
          </button>
        </div>
        <div className="col-6">
          <button
            type="button"
            className={`btn btn-sm ${availability.additional ? 'btn-primary' : 'btn-outline-primary'}`}
            data-bs-toggle="tooltip"
            data-bs-placement="top"
            title="Additional Slots"
            onClick={() => onStatusChange('additional')}
          >
            <i className="bi bi-plus-circle"></i>
          </button>
        </div>
      </div>
      <div className="row mt-2">
        <div className="col-6">
          <button
            type="button"
            className={`btn btn-sm ${availability.arrived ? 'btn-info' : 'btn-outline-info'}`}
            data-bs-toggle="tooltip"
            data-bs-placement="top"
            title="Arrived"
            onClick={() => onStatusChange('arrived')}
          >
            <i className="bi bi-person-check"></i>
          </button>
        </div>
        <div className="col-6">
          <button
            type="button"
            className={`btn btn-sm ${availability.completed ? 'btn-secondary' : 'btn-outline-secondary'}`}
            data-bs-toggle="tooltip"
            data-bs-placement="top"
            title="Completed"
            onClick={() => onStatusChange('completed')}
          >
            <i className="bi bi-check2-circle"></i>
          </button>
        </div>
      </div>
      
      <div className="row mt-2">
        <div className="col-6">
          <button
            type="button"
            className={`btn btn-sm ${availability.walkin ? 'btn-dark' : 'btn-outline-dark'}`}
            data-bs-toggle="tooltip"
            data-bs-placement="top"
            title="Walkin"
            onClick={() => onStatusChange('walkin')}
          >
            <i className="bi bi-person-plus"></i>
          </button>
        </div>
        <div className="col-6">
          <button
            type="button"
            className={`btn btn-sm ${availability.blocked ? 'btn-muted' : 'btn-outline-muted'}`}
            data-bs-toggle="tooltip"
            data-bs-placement="top"
            title="Blocked"
            onClick={() => onStatusChange('blocked')}
          >
            <i className="bi bi-block"></i>
          </button>
        </div>
      </div>
      <div className="row mt-2">
        <div className="col-6">
          <button
            type="button"
            className={`btn btn-sm ${availability.noShow ? 'btn-light' : 'btn-outline-light'}`}
            data-bs-toggle="tooltip"
            data-bs-placement="top"
            title="No Show"
            onClick={() => onStatusChange('noShow')}
          >
            <i className="bi bi-person-x"></i>
          </button>
        </div>
        <div className="col-6">
          <button
            type="button"
            className={`btn btn-sm ${availability.reserved ? 'btn-outline-dark' : 'btn-dark'}`}
            data-bs-toggle="tooltip"
            data-bs-placement="top"
            title="Reserved"
            onClick={() => onStatusChange('reserved')}
          >
            <i className="bi bi-file-earmark-check"></i>
          </button>
        </div>
    </div>
    </div>
  );
};

export default StatusButtons;
