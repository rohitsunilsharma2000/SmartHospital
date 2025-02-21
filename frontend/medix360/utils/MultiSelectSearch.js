"use client";
import React, { useState, useRef, useEffect } from "react";

const MultiSelectSearch = ({ options, name, formData, handleChange, clearSelection }) => {
  const [search, setSearch] = useState("");
  const [isOpen, setIsOpen] = useState(false);
  const dropdownRef = useRef(null);

  const handleSearchChange = (e) => setSearch(e.target.value);

  const handleSelectMultipleChange = (e) => {
    handleChange({
      target: {
        name,
        type: "select-multiple",
        value: e.target.checked ? options.map(({ value, id }) => ({ value, id })) : [],
      },
    });
  };

  const handleDeleteOption = (option) => {
    handleChange({
      target: {
        name,
        type: "select-multiple",
        value: formData[name].filter((v) => v.value !== option.value),
      },
    });
  };
  const handleCheckboxChange = (e, name, value, id) => {
    console.log(`Checkbox clicked - Value: ${value}, ID: ${id},Name: ${name}, Checked: ${e.target.checked}`);

    const newValue = e.target.checked
      ? [...formData[name], { value, id }] // Add selected item
      : formData[name].filter((v) => v.value !== value); // Remove unselected item

    console.log("Updated selection:", newValue);

    handleChange({
      target: {
        name,
        type: "select-multiple",
        value: newValue,
      },
    });
  };


  const toggleDropdown = () => setIsOpen(!isOpen);

  // Close dropdown when clicking outside
  useEffect(() => {
    const handleClickOutside = (event) => {
      if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
        setIsOpen(false);
      }
    };
    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  return (
    <div className="dropdown" ref={dropdownRef}>
      <button
        className="btn btn-light dropdown-toggle w-100 text-start"
        type="button"
        onClick={toggleDropdown}
      >
        {formData[name].length > 0 ? (
          <div className="d-flex flex-wrap gap-2 w-75 p-2 border rounded overflow-auto" style={{ minHeight: "50px", maxWidth: "400px" }}>
            {formData[name].map((option, index) => (
              <span key={index} className="badge bg-primary position-relative ms-2 btn-sm">
                {option.value} {/* Display only the value */}
                <span
                  className="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger"
                  style={{ cursor: "pointer" }}
                  onClick={() => handleDeleteOption(option)}
                >
                  <i className="bi bi-trash3"></i>
                </span>
              </span>
            ))}
          </div>
        ) : (
          <span className="text-muted">Select options</span>
        )
        }

      </button >

      {isOpen && (
        <ul className="dropdown-menu p-2 show" style={{ width: "300px" }}>
          <input
            type="text"
            className="form-control mb-2"
            placeholder="Search"
            value={search}
            onChange={handleSearchChange}
          />
          <li>
            <button className="btn btn-sm btn-danger w-100 mb-2" onClick={() => clearSelection(name)}>
              Clear Selection
            </button>
          </li>
          <li>
            <label className="dropdown-item">
              <input
                type="checkbox"
                checked={formData[name].length === options.length}
                onChange={handleSelectMultipleChange}
              />
              Select All
            </label>
          </li>
          {options
            .filter(({ label }) => label.toLowerCase().includes(search.toLowerCase()))
            .map(({ value, label, id }) => (
              <li key={value}>
                <label className="dropdown-item">
                  <input
                    type="checkbox"
                    value={value}
                    checked={formData[name].some((v) => v.value === value)}
                    onChange={(e) => handleCheckboxChange(e, name, value, id)}

                  />
                  {label}
                </label>
              </li>
            ))}
        </ul>
      )}
    </div >
  );
};

export default MultiSelectSearch;