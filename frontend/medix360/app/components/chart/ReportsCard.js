"use client"
import React from "react";
import ReactApexChart from "react-apexcharts";

const ReportsCard = () => {
  const options = {
    chart: {
      height: 350,
      type: "area",
      toolbar: {
        show: false,
      },
    },
    markers: {
      size: 4,
    },
    colors: ["#4154f1", "#2eca6a", "#ff771d"],
    fill: {
      type: "gradient",
      gradient: {
        shadeIntensity: 1,
        opacityFrom: 0.3,
        opacityTo: 0.4,
        stops: [0, 90, 100],
      },
    },
    dataLabels: {
      enabled: false,
    },
    stroke: {
      curve: "smooth",
      width: 2,
    },
    xaxis: {
      type: "datetime",
      categories: [
        "2018-09-19T00:00:00.000Z",
        "2018-09-19T01:30:00.000Z",
        "2018-09-19T02:30:00.000Z",
        "2018-09-19T03:30:00.000Z",
        "2018-09-19T04:30:00.000Z",
        "2018-09-19T05:30:00.000Z",
        "2018-09-19T06:30:00.000Z",
      ],
    },
    tooltip: {
      x: {
        format: "dd/MM/yy HH:mm",
      },
    },
  };

  const series = [
    {
      name: "Sales",
      data: [31, 40, 28, 51, 42, 82, 56],
    },
    {
      name: "Revenue",
      data: [11, 32, 45, 32, 34, 52, 41],
    },
    {
      name: "Customers",
      data: [15, 11, 32, 18, 9, 24, 11],
    },
  ];

  return (
    <div className="col-12">
      <div className="card">
        <div className="filter">
          <a className="icon" href="#" data-bs-toggle="dropdown">
            <i className="bi bi-three-dots"></i>
          </a>
          <ul className="dropdown-menu dropdown-menu-end dropdown-menu-arrow">
            <li className="dropdown-header text-start">
              <h6>Filter</h6>
            </li>
            <li><a className="dropdown-item" href="#">Today</a></li>
            <li><a className="dropdown-item" href="#">This Month</a></li>
            <li><a className="dropdown-item" href="#">This Year</a></li>
          </ul>
        </div>

        <div className="card-body">
          <h5 className="card-title">
            Reports <span>/Today</span>
          </h5>

          {/* Line Chart */}
          <ReactApexChart
            options={options}
            series={series}
            type="area"
            height={350}
            width={850}

            
          />
        </div>
      </div>
    </div>
  );
};

export default ReportsCard;
