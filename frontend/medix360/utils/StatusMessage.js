import React, { useState } from 'react';

const StatusMessage = ({ status, errorMessage }) => {
  const [isAlertVisible, setAlertVisible] = useState(true);

  const handleCloseAlert = () => {
    setAlertVisible(false);
  };

  return (
    <div className="col-md-12 d-flex justify-content-center align-items-center">
      {/* Loading Spinner */}
      {status === 'loading' && (
        <div
          className="position-fixed top-0 start-0 w-100 h-100 d-flex justify-content-center align-items-center"
          style={{
            backgroundColor: 'rgba(0, 0, 0, 0.5)', // semi-transparent background
            zIndex: 9999, // Ensure it's above all other content
          }}
        >
          <div className="spinner-border text-light" role="status">
            <span className="visually-hidden">Loading...</span>
          </div>
        </div>
      )}

      {/* Success Message */}
      {status?.toLowerCase().includes('success') && isAlertVisible && (
        <div className="alert alert-success alert-dismissible fade show  z-3 position-absolute mt-5 rounded-3" role="alert">
          <strong>{status}</strong> .
          <button
            type="button"
            className="btn-close"
            data-bs-dismiss="alert"
            aria-label="Close"
            onClick={handleCloseAlert} // Close the alert on button click
          ></button>
        </div>
      )}

      {/* Error Message */}
      {status === 'error' && isAlertVisible && (
        <div className="alert alert-danger alert-dismissible fade show  z-3 position-absolute mt-5 rounded-3" role="alert">

          <strong>Error!</strong> {errorMessage}
          {/* <button
            type="button"
            className="btn-close"
            data-bs-dismiss="alert"
            aria-label="Close"
            onClick={handleCloseAlert} // Close the alert on button click
          ></button> */}
        </div>
      )}
    </div>
  );
};

export default StatusMessage;